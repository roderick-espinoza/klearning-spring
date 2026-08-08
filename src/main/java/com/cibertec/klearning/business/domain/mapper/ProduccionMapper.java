package com.cibertec.klearning.business.domain.mapper;

import com.cibertec.klearning.business.api.dto.produccion.ProduccionRequestDto;
import com.cibertec.klearning.business.api.dto.produccion.ProduccionResponseDto;
import com.cibertec.klearning.business.data.entity.Produccion;
import com.cibertec.klearning.business.domain.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProduccionMapper extends BaseMapper<Produccion, ProduccionRequestDto, ProduccionResponseDto> {

    // 1. DTO -> ENTIDAD (Para crear un nuevo registro)
    @Override
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "leccionOva", ignore = true)
    @Mapping(target = "persona", ignore = true)
    Produccion toEntity(ProduccionRequestDto request);

    // 2. ENTIDAD -> DTO (Para devolver la respuesta al cliente)
    @Override
    @Mapping(target = "idProyecto", source = "proyecto.idProyecto")
    @Mapping(target = "idLeccionOva", source = "leccionOva.idLeccionOva")
    @Mapping(target = "idPersona", source = "persona.idPersona")
    ProduccionResponseDto toResponseDto(Produccion entidad);

    // 3. DTO -> ENTIDAD EXISTENTE (Para actualizar un registro)
    @Override
    @Mapping(target = "idProduccion", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "leccionOva", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedUser", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "deletedUser", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    void actualizarEntidad(ProduccionRequestDto request, @MappingTarget Produccion entidad);
}