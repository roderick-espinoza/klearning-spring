package com.cibertec.klearning.mapper;

import com.cibertec.klearning.dto.persona.PersonaRequestDto;
import com.cibertec.klearning.dto.persona.PersonaResponseDto;
import com.cibertec.klearning.entity.Persona;
import com.cibertec.klearning.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonaMapper extends BaseMapper<Persona, PersonaRequestDto, PersonaResponseDto> {

    @Override
    Persona toEntity(PersonaRequestDto request);

    @Override
    PersonaResponseDto toResponseDto(Persona entidad);

    @Override
    @Mapping(target = "idPersona", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updatedUser", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "deletedUser", ignore = true)
    @Mapping(target = "deletedDate", ignore = true)
    void actualizarEntidad(PersonaRequestDto request, @MappingTarget Persona entidad);
}
