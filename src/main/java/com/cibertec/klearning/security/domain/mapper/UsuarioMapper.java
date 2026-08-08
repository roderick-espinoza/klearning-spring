package com.cibertec.klearning.security.domain.mapper;

import com.cibertec.klearning.business.data.entity.Persona;
import com.cibertec.klearning.business.domain.mapper.base.BaseMapper;
import com.cibertec.klearning.security.api.dto.usuario.PersonaResumenDto;
import com.cibertec.klearning.security.api.dto.usuario.RolResumenDto;
import com.cibertec.klearning.security.api.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.security.api.dto.usuario.UsuarioResponseDto;
import com.cibertec.klearning.security.data.entity.Rol;
import com.cibertec.klearning.security.data.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioRequestDto, UsuarioResponseDto> {

    @Override
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedUser", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "deletedUser", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    Usuario toEntity(UsuarioRequestDto request);

    @Override
    UsuarioResponseDto toResponseDto(Usuario entidad);

    @Override
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedUser", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "deletedUser", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    void actualizarEntidad(UsuarioRequestDto request, @MappingTarget Usuario entidad);

    PersonaResumenDto toPersonaResumen(Persona persona);

    RolResumenDto toRolResumen(Rol rol);
}
