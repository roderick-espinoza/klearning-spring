package com.cibertec.klearning.business.domain.mapper;

import com.cibertec.klearning.business.api.dto.ova.OvaRequestDto;
import com.cibertec.klearning.business.api.dto.ova.OvaResponseDto;
import com.cibertec.klearning.business.data.entity.Ova;
import com.cibertec.klearning.business.domain.mapper.base.BaseMapper;
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
