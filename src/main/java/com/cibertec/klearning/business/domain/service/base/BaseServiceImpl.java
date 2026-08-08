package com.cibertec.klearning.business.domain.service.base;

import com.cibertec.klearning.business.api.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.business.data.entity.base.AuditEntity;
import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.business.domain.mapper.base.BaseMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BaseServiceImpl<
        T extends AuditEntity,
        ID,
        REQ,
        RES,
        R extends JpaRepository<T, ID>> {

    protected static final String USUARIO_SISTEMA = "sistema";

    protected final R repository;
    protected final BaseMapper<T, REQ, RES> mapper;

    protected BaseServiceImpl(R repository, BaseMapper<T, REQ, RES> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected abstract String nombreEntidad();

    /**
     * Usuario que queda registrado en los campos de auditoria.
     * Sustituye al antiguo SessionUtil: ahora sale del SecurityContext,
     * que es alimentado por el JwtAuthenticationFilter tanto si el token
     * llega por cabecera (API) como si llega por cookie (navegador).
     */
    protected String usuarioActual() {
        Authentication autenticacion =
                SecurityContextHolder.getContext().getAuthentication();

        if (autenticacion == null
                || !autenticacion.isAuthenticated()
                || autenticacion.getPrincipal() instanceof String) {
            return USUARIO_SISTEMA;
        }

        return autenticacion.getName();
    }

    protected T buscarActivoOrThrow(ID id) {
        return repository.findById(id)
                .filter(e -> e.getEstado() != EstadoRegistro.ELIMINADO)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        nombreEntidad() + " no encontrado con ID: " + id));
    }

    public List<RES> listar() {
        return repository.findAll()
                .stream()
                .filter(e -> e.getEstado() != EstadoRegistro.ELIMINADO)
                .map(mapper::toResponseDto)
                .toList();
    }

    public RES obtenerPorId(ID id) {
        return mapper.toResponseDto(buscarActivoOrThrow(id));
    }

    @Transactional
    public RES crear(REQ request) {
        T entidad = mapper.toEntity(request);
        entidad.setEstado(EstadoRegistro.ACTIVO);
        entidad.setCreateUser(usuarioActual());
        entidad.setCreateDate(LocalDateTime.now());
        return mapper.toResponseDto(repository.save(entidad));
    }

    @Transactional
    public RES actualizar(ID id, REQ request) {
        T entidad = buscarActivoOrThrow(id);
        mapper.actualizarEntidad(request, entidad);
        entidad.setUpdatedUser(usuarioActual());
        entidad.setUpdatedDate(LocalDateTime.now());
        return mapper.toResponseDto(repository.save(entidad));
    }

    @Transactional
    public void eliminar(ID id) {
        T entidad = buscarActivoOrThrow(id);
        entidad.setEstado(EstadoRegistro.ELIMINADO);
        entidad.setDeletedUser(usuarioActual());
        entidad.setDeletedDate(LocalDateTime.now());
        repository.save(entidad);
    }
}
