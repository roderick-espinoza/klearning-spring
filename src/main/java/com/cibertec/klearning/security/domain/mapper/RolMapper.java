package com.cibertec.klearning.security.domain.mapper;

import com.cibertec.klearning.security.api.dto.rol.RolRequestDto;
import com.cibertec.klearning.security.api.dto.rol.RolResponseDto;
import com.cibertec.klearning.security.data.entity.Rol;
import com.cibertec.klearning.business.domain.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RolMapper extends BaseMapper<Rol, RolRequestDto, RolResponseDto> {

    @Override
    Rol toEntity(RolRequestDto request);

    @Override
    RolResponseDto toResponseDto(Rol entidad);

    @Override
    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedUser", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "deletedUser", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    void actualizarEntidad(RolRequestDto request, @MappingTarget Rol entidad);
}
