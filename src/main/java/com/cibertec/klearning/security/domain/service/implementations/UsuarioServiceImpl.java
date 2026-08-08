package com.cibertec.klearning.security.domain.service.implementations;

import com.cibertec.klearning.business.api.exception.RecursoDuplicadoException;
import com.cibertec.klearning.business.api.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.business.api.exception.SolicitudInvalidaException;
import com.cibertec.klearning.business.data.entity.Persona;
import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.business.data.repository.PersonaRepository;
import com.cibertec.klearning.business.domain.service.base.BaseServiceImpl;
import com.cibertec.klearning.security.api.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.security.api.dto.usuario.UsuarioResponseDto;
import com.cibertec.klearning.security.data.entity.Rol;
import com.cibertec.klearning.security.data.entity.Usuario;
import com.cibertec.klearning.security.data.repository.RolRepository;
import com.cibertec.klearning.security.data.repository.UsuarioRepository;
import com.cibertec.klearning.security.domain.mapper.UsuarioMapper;
import com.cibertec.klearning.security.domain.service.interfaces.UsuarioService;
import jakarta.persistence.EntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl
        extends BaseServiceImpl<Usuario, String, UsuarioRequestDto, UsuarioResponseDto, UsuarioRepository>
        implements UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public UsuarioServiceImpl(UsuarioRepository repository,
                              UsuarioMapper usuarioMapper,
                              PersonaRepository personaRepository,
                              RolRepository rolRepository,
                              PasswordEncoder passwordEncoder,
                              EntityManager entityManager) {
        super(repository, usuarioMapper);
        this.usuarioMapper = usuarioMapper;
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Override
    protected String nombreEntidad() { return "Usuario"; }

    @Override
    public List<UsuarioResponseDto> listar() { return super.listar(); }

    @Override
    public UsuarioResponseDto obtenerPorId(String id) { return super.obtenerPorId(id); }

    @Override
    public void eliminar(String id) { super.eliminar(id); }

    @Override
    public UsuarioResponseDto obtenerPorUsuario(String usuario) {
        Usuario encontrado = repository.findByUsuario(usuario)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado: " + usuario));
        return usuarioMapper.toResponseDto(encontrado);
    }

    @Override
    @Transactional
    public UsuarioResponseDto crear(UsuarioRequestDto request) {
        if (request.password() == null || request.password().isBlank()) {
            throw new SolicitudInvalidaException(
                    "La contraseña es obligatoria al crear un usuario");
        }

        String nombreUsuario = normalizar(request.usuario());

        if (repository.existsByUsuarioIgnoreCase(nombreUsuario)) {
            throw new RecursoDuplicadoException(
                    "Ya existe un usuario con el nombre: " + nombreUsuario);
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setUsuario(nombreUsuario);
        usuario.setPersona(buscarPersona(request.idPersona()));
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.reemplazarRoles(buscarRoles(request.idsRoles()));
        usuario.setEstado(EstadoRegistro.ACTIVO);
        usuario.setCreateUser(usuarioActual());
        usuario.setCreateDate(LocalDateTime.now());

        return usuarioMapper.toResponseDto(repository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioResponseDto actualizar(String id, UsuarioRequestDto request) {
        Usuario usuario = buscarActivoOrThrow(id);
        usuarioMapper.actualizarEntidad(request, usuario);

        usuario.setUsuario(normalizar(request.usuario()));

        if (!usuario.getPersona().getIdPersona().equals(request.idPersona())) {
            usuario.setPersona(buscarPersona(request.idPersona()));
        }

        usuario.reemplazarRoles(buscarRoles(request.idsRoles()));

        // Password en blanco significa "no cambiar la contraseña actual".
        if (request.password() != null && !request.password().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.password()));
        }

        usuario.setUpdatedUser(usuarioActual());
        usuario.setUpdatedDate(LocalDateTime.now());

        return usuarioMapper.toResponseDto(repository.save(usuario));
    }

    /**
     * Inserción masiva de usuarios con flush + clear periódico.
     * Evita el N+1: en vez de buscar Persona/Rol dentro del loop,
     * se traen TODAS las referencias necesarias en dos consultas previas.
     */
    @Override
    @Transactional
    public List<UsuarioResponseDto> guardarEnBatch(List<UsuarioRequestDto> requests,
                                                   int batchSize) {

        Set<String> idsPersona = requests.stream()
                .map(UsuarioRequestDto::idPersona)
                .collect(Collectors.toSet());

        Set<String> idsRol = requests.stream()
                .flatMap(dto -> dto.idsRoles().stream())
                .collect(Collectors.toSet());

        Map<String, Persona> personas = personaRepository.findAllById(idsPersona).stream()
                .collect(Collectors.toMap(Persona::getIdPersona, p -> p));
        Map<String, Rol> roles = rolRepository.findAllById(idsRol).stream()
                .collect(Collectors.toMap(Rol::getIdRol, r -> r));

        List<UsuarioResponseDto> resultado = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            UsuarioRequestDto dto = requests.get(i);

            Persona persona = personas.get(dto.idPersona());
            if (persona == null) {
                throw new RecursoNoEncontradoException("Persona no encontrada: " + dto.idPersona());
            }

            Set<Rol> rolesUsuario = new LinkedHashSet<>();
            for (String idRol : dto.idsRoles()) {
                Rol rol = roles.get(idRol);
                if (rol == null) {
                    throw new RecursoNoEncontradoException("Rol no encontrado: " + idRol);
                }
                rolesUsuario.add(rol);
            }

            Usuario usuario = usuarioMapper.toEntity(dto);
            usuario.setUsuario(normalizar(dto.usuario()));
            usuario.setPersona(persona);
            usuario.setPassword(passwordEncoder.encode(dto.password()));
            usuario.reemplazarRoles(rolesUsuario);
            usuario.setEstado(EstadoRegistro.ACTIVO);
            usuario.setCreateUser(usuarioActual());
            usuario.setCreateDate(LocalDateTime.now());

            Usuario guardado = repository.save(usuario);
            resultado.add(usuarioMapper.toResponseDto(guardado));

            if ((i + 1) % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        entityManager.flush();
        entityManager.clear();

        return resultado;
    }

    private String normalizar(String nombreUsuario) {
        return nombreUsuario.trim().toLowerCase();
    }

    private Persona buscarPersona(String idPersona) {
        return personaRepository.findById(idPersona)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Persona no encontrada: " + idPersona));
    }

    private Set<Rol> buscarRoles(Set<String> idsRoles) {
        if (idsRoles == null || idsRoles.isEmpty()) {
            throw new SolicitudInvalidaException("Debe asignar al menos un rol al usuario");
        }

        List<Rol> encontrados = rolRepository.findAllById(idsRoles);

        if (encontrados.size() != idsRoles.size()) {
            throw new RecursoNoEncontradoException(
                    "Alguno de los roles indicados no existe");
        }

        return new LinkedHashSet<>(encontrados);
    }
}
