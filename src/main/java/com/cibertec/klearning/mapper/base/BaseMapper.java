package com.cibertec.klearning.mapper.base;

import org.mapstruct.MappingTarget;

public interface BaseMapper<T, REQ, RES> {
    T toEntity(REQ request);
    RES toResponseDto(T entidad);
    void actualizarEntidad(REQ request, @MappingTarget T entidad);
}