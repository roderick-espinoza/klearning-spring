package com.cibertec.klearning.mapper;

import com.cibertec.klearning.dto.proyecto.ProyectoRequestDto;
import com.cibertec.klearning.dto.proyecto.ProyectoResponseDto;
import com.cibertec.klearning.entity.Proyecto;
import com.cibertec.klearning.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProyectoMapper extends BaseMapper<Proyecto, ProyectoRequestDto, ProyectoResponseDto> {

    @Override
    Proyecto toEntity(ProyectoRequestDto request);

    @Override
    ProyectoResponseDto toResponseDto(Proyecto entidad);

    @Override
    @Mapping(target = "idProyecto", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedUser", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "deletedUser", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    void actualizarEntidad(ProyectoRequestDto request, @MappingTarget Proyecto entidad);
}