package com.cibertec.klearning.service.impl;

import com.cibertec.klearning.dto.LoginRequestDTO;
import com.cibertec.klearning.dto.UsuarioRequestDTO;
import com.cibertec.klearning.dto.UsuarioResponseDTO;
import com.cibertec.klearning.entity.Persona;
import com.cibertec.klearning.entity.Rol;
import com.cibertec.klearning.entity.Usuario;
import com.cibertec.klearning.repository.UsuarioRepository;
import com.cibertec.klearning.service.UsuarioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ============================================================
 *  CAPA DE NEGOCIO  -  @Service (estereotipo Spring)
 * ============================================================
 * Aqui viven: generacion del codigo USERxxxx, validaciones,
 * FLUSHING / BATCHING y el borrado logico.
 */
@Slf4j
@Service
@RequiredArgsConstructor          // Lombok: inyeccion por constructor
public class UsuarioServiceImpl implements UsuarioService {

    private static final String PREFIJO = "USER";
    private static final int LONGITUD_CORRELATIVO = 4;   // USER + 0001
    private static final int TAMANIO_LOTE = 30;          // = hibernate.jdbc.batch_size

    private final UsuarioRepository usuarioRepository;

    /** Necesario para flush(), clear() y getReference(). */
    @PersistenceContext
    private EntityManager entityManager;

    // ==================================================================
    // 1) REGISTRO SIMPLE  (POST /api/usuarios)
    // ==================================================================
    @Override
    @Transactional
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {

        if (usuarioRepository.existsByUsuarioIgnoreCase(dto.getUsuario().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El nombre de usuario '" + dto.getUsuario() + "' ya esta registrado");
        }

        Usuario u = new Usuario();
        u.setIdUsuario(generarSiguienteCodigo());
        u.setUsuario(dto.getUsuario().trim());
        u.setPassword(dto.getPassword());
        u.setEstado("1");                       // heredado de AuditEntity
        u.setCreateUser(dto.getCreateUser());   // heredado de AuditEntity
        u.setCreateDate(LocalDateTime.now());

        // FETCHING: getReference() devuelve un PROXY sin consultar la BD.
        // Solo necesitamos la FK, no los datos de persona/rol, asi que
        // esto evita 2 SELECT innecesarios antes del INSERT.
        u.setPersona(entityManager.getReference(Persona.class, dto.getIdPersona()));
        u.setRol(entityManager.getReference(Rol.class, dto.getIdRol()));

        usuarioRepository.save(u);

        // FLUSHING manual: obliga a Hibernate a ejecutar el INSERT AHORA,
        // sin esperar al commit. Asi un error de FK o de duplicado aparece
        // dentro de este metodo y no despues.
        usuarioRepository.flush();

        log.info("Usuario registrado: {}", u.getIdUsuario());

        return usuarioRepository.obtenerConDetalle(u.getIdUsuario())
                .map(UsuarioResponseDTO::desde)
                .orElseThrow();
    }

    // ==================================================================
    // 2) REGISTRO POR LOTE  (POST /api/usuarios/lote)
    //    >>> AQUI SE DEMUESTRA FLUSHING + BATCHING <<<
    // ==================================================================
    /**
     * Sin batching, insertar N usuarios = N viajes a MySQL, y el contexto
     * de persistencia crece hasta consumir toda la memoria.
     *
     * Con batching:
     *   - hibernate.jdbc.batch_size=30 agrupa 30 INSERT por viaje.
     *   - flush()  -> vacia el contexto hacia la BD (ejecuta el lote).
     *   - clear()  -> desasocia lo ya guardado para que el contexto no crezca.
     * El par flush()+clear() cada N registros es EL patron clasico.
     */
    @Override
    @Transactional
    public int registrarLote(List<UsuarioRequestDTO> lista) {

        // El correlativo se calcula UNA vez y se incrementa en memoria,
        // porque despues del clear() ya no podriamos consultar el MAX().
        int correlativo = obtenerUltimoCorrelativo();
        int insertados = 0;

        for (int i = 0; i < lista.size(); i++) {
            UsuarioRequestDTO dto = lista.get(i);

            Usuario u = new Usuario();
            u.setIdUsuario(formatearCodigo(++correlativo));
            u.setUsuario(dto.getUsuario().trim());
            u.setPassword(dto.getPassword());
            u.setEstado("1");
            u.setCreateUser(dto.getCreateUser());
            u.setCreateDate(LocalDateTime.now());
            u.setPersona(entityManager.getReference(Persona.class, dto.getIdPersona()));
            u.setRol(entityManager.getReference(Rol.class, dto.getIdRol()));

            entityManager.persist(u);
            insertados++;

            if (i > 0 && i % TAMANIO_LOTE == 0) {
                entityManager.flush();   // manda el lote a MySQL
                entityManager.clear();   // libera memoria del contexto
                log.info("Lote enviado. Registros procesados: {}", i);
            }
        }

        entityManager.flush();   // ultimo lote (el resto)
        entityManager.clear();

        log.info("Registro por lote finalizado. Total insertados: {}", insertados);
        return insertados;
    }

    // ==================================================================
    // 3) LOGIN  (POST /api/usuarios/login) -> equivale a sp_validar_usuario
    // ==================================================================
    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO login(LoginRequestDTO dto) {
        return usuarioRepository
                .validarCredenciales(dto.getUsuario(), dto.getPassword())
                .map(UsuarioResponseDTO::desde)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Usuario o password incorrectos"));
    }

    // ==================================================================
    // 4) CONSULTAS
    // ==================================================================
    @Override
    @Transactional(readOnly = true)   // readOnly evita el dirty checking: mas rapido
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.listarActivos()
                .stream()
                .map(UsuarioResponseDTO::desde)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO obtener(String idUsuario) {
        return usuarioRepository.obtenerConDetalle(idUsuario)
                .map(UsuarioResponseDTO::desde)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe el usuario " + idUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> listarPorRol(String idRol, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("idUsuario").ascending());
        return usuarioRepository
                .findByRol_IdRolAndDeletedDateIsNull(idRol, pageable)
                .map(UsuarioResponseDTO::desde);
    }

    // ==================================================================
    // 5) BORRADO LOGICO -> equivale a sp_eliminar_usuario
    // ==================================================================
    @Override
    @Transactional
    public void eliminarLogico(String idUsuario, String deletedUser) {
        Usuario u = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe el usuario " + idUsuario));

        u.setEstado("2");
        u.setDeletedDate(LocalDateTime.now());
        u.setDeletedUser(deletedUser);
        // No hace falta save(): la entidad esta "managed" y el dirty checking
        // genera el UPDATE solo al hacer flush/commit.
    }

    // ==================================================================
    // HELPERS: generacion del codigo USER0001
    // ==================================================================
    private String generarSiguienteCodigo() {
        return formatearCodigo(obtenerUltimoCorrelativo() + 1);
    }

    private int obtenerUltimoCorrelativo() {
        String ultimo = usuarioRepository.obtenerUltimoCodigo();
        if (ultimo == null || ultimo.isBlank()) {
            return 0;
        }
        return Integer.parseInt(ultimo.substring(PREFIJO.length()));
    }

    private String formatearCodigo(int numero) {
        return PREFIJO + String.format("%0" + LONGITUD_CORRELATIVO + "d", numero);
    }
}
