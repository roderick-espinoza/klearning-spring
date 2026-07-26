package com.cibertec.klearning.mapper;

import com.cibertec.klearning.dto.ova.OvaRequestDto;
import com.cibertec.klearning.dto.ova.OvaResponseDto;
import com.cibertec.klearning.entity.Ova;
import com.cibertec.klearning.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OvaMapper extends BaseMapper<Ova, OvaRequestDto, OvaResponseDto> {

    @Override
    Ova toEntity(OvaRequestDto request);

    @Override
    OvaResponseDto toResponseDto(Ova entidad);

    @Override
    @Mapping(target = "idOva", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedUser", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "deletedUser", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    void actualizarEntidad(OvaRequestDto dto, @MappingTarget Ova entidad);

}
