package com.cibertec.klearning.service.implementations;

import com.cibertec.klearning.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.dto.usuario.UsuarioResponseDto;
import com.cibertec.klearning.entity.Persona;
import com.cibertec.klearning.entity.Rol;
import com.cibertec.klearning.entity.Usuario;
import com.cibertec.klearning.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.mapper.UsuarioMapper;
import com.cibertec.klearning.repository.PersonaRepository;
import com.cibertec.klearning.repository.RolRepository;
import com.cibertec.klearning.repository.UsuarioRepository;
import com.cibertec.klearning.service.base.BaseServiceImpl;
import com.cibertec.klearning.service.interfaces.UsuarioService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final EntityManager entityManager;

    public UsuarioServiceImpl(UsuarioRepository repository,
                              UsuarioMapper usuarioMapper,
                              PersonaRepository personaRepository,
                              RolRepository rolRepository,
                              EntityManager entityManager) {
        super(repository, usuarioMapper);
        this.usuarioMapper = usuarioMapper;
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
        this.entityManager = entityManager;
    }

    @Override
    protected String nombreEntidad() { return "Usuario"; }

    @Override
    public List<UsuarioResponseDto> listar() { return super.listar(); }

    @Override
    public UsuarioResponseDto obtenerPorId(String id) { return super.obtenerPorId(id); }

    @Override
    public void eliminar(String id, String usuarioActual) { super.eliminar(id, usuarioActual); }

    @Override
    public UsuarioResponseDto obtenerPorUsuario(String usuario) {
        Usuario encontrado = repository.findByUsuario(usuario)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado: " + usuario));
        return usuarioMapper.toResponseDto(encontrado);
    }

    @Override
    @Transactional
    public UsuarioResponseDto crear(UsuarioRequestDto request, String usuarioActual) {
        Persona persona = personaRepository.findById(request.idPersona())
                .orElseThrow(() -> new RecursoNoEncontradoException("Persona no encontrada: " + request.idPersona()));
        Rol rol = rolRepository.findById(request.idRol())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado: " + request.idRol()));

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setPersona(persona);
        usuario.setRol(rol);
        usuario.setEstado("1");
        usuario.setCreateUser(usuarioActual);
        usuario.setCreateDate(LocalDateTime.now());

        return usuarioMapper.toResponseDto(repository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioResponseDto actualizar(String id, UsuarioRequestDto request, String usuarioActual) {
        Usuario usuario = buscarActivoOrThrow(id);
        usuarioMapper.actualizarEntidad(request, usuario);

        if (!usuario.getPersona().getIdPersona().equals(request.idPersona())) {
            usuario.setPersona(personaRepository.findById(request.idPersona())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Persona no encontrada")));
        }
        if (!usuario.getRol().getIdRol().equals(request.idRol())) {
            usuario.setRol(rolRepository.findById(request.idRol())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado")));
        }

        usuario.setUpdatedUser(usuarioActual);
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
                                                   String usuarioActual,
                                                   int batchSize) {

        Set<String> idsPersona = requests.stream()
                .map(UsuarioRequestDto::idPersona)
                .collect(Collectors.toSet());
        Set<String> idsRol = requests.stream()
                .map(UsuarioRequestDto::idRol)
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
            Rol rol = roles.get(dto.idRol());
            if (rol == null) {
                throw new RecursoNoEncontradoException("Rol no encontrado: " + dto.idRol());
            }

            Usuario usuario = usuarioMapper.toEntity(dto);
            usuario.setPersona(persona);
            usuario.setRol(rol);
            usuario.setEstado("1");
            usuario.setCreateUser(usuarioActual);
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
}