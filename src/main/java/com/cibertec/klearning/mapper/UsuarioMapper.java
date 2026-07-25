package com.cibertec.klearning.mapper;

import com.cibertec.klearning.dto.usuario.PersonaResumenDto;
import com.cibertec.klearning.dto.usuario.RolResumenDto;
import com.cibertec.klearning.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.dto.usuario.UsuarioResponseDto;
import com.cibertec.klearning.entity.Persona;
import com.cibertec.klearning.entity.Rol;
import com.cibertec.klearning.entity.Usuario;
import com.cibertec.klearning.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioRequestDto, UsuarioResponseDto> {

    @Override
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Usuario toEntity(UsuarioRequestDto request);

    @Override
    UsuarioResponseDto toResponseDto(Usuario entidad);

    @Override
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "rol", ignore = true)
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
