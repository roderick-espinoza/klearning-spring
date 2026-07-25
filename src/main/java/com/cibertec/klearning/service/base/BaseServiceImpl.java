package com.cibertec.klearning.service.base;

import com.cibertec.klearning.entity.base.AuditEntity;
import com.cibertec.klearning.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.mapper.base.BaseMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BaseServiceImpl<
        T extends AuditEntity,
                ID,
                REQ,
                RES,
                R extends JpaRepository<T, ID>> {

protected final R repository;
protected final BaseMapper<T, REQ, RES> mapper;

protected BaseServiceImpl(R repository, BaseMapper<T, REQ, RES> mapper) {
    this.repository = repository;
    this.mapper = mapper;
}

protected abstract String nombreEntidad();

protected T buscarActivoOrThrow(ID id) {
    return repository.findById(id)
            .filter(e -> !"2".equals(e.getEstado()))
            .orElseThrow(() -> new RecursoNoEncontradoException(
                    nombreEntidad() + " no encontrado con ID: " + id));
}

public List<RES> listar() {
    return repository.findAll()
            .stream()
            .map(mapper::toResponseDto)
            .toList();
}

public RES obtenerPorId(ID id) {
    return mapper.toResponseDto(buscarActivoOrThrow(id));
}

@Transactional
public RES crear(REQ request, String usuarioActual) {
    T entidad = mapper.toEntity(request);
    entidad.setEstado("1");
    entidad.setCreateUser(usuarioActual);
    entidad.setCreateDate(LocalDateTime.now());
    return mapper.toResponseDto(repository.save(entidad));
}

@Transactional
public RES actualizar(ID id, REQ request, String usuarioActual) {
    T entidad = buscarActivoOrThrow(id);
    mapper.actualizarEntidad(request, entidad);
    entidad.setUpdatedUser(usuarioActual);
    entidad.setUpdatedDate(LocalDateTime.now());
    return mapper.toResponseDto(repository.save(entidad));
}

@Transactional
public void eliminar(ID id, String usuarioActual) {
    T entidad = buscarActivoOrThrow(id);
    entidad.setEstado("2");
    entidad.setDeletedUser(usuarioActual);
    entidad.setDeletedDate(LocalDateTime.now());
    repository.save(entidad);
}
}