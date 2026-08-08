package com.cibertec.klearning.business.api.dto.persona;

import com.cibertec.klearning.business.data.entity.enums.EstadoCivil;
import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.business.data.entity.enums.FormacionAcademica;
import com.cibertec.klearning.business.data.entity.enums.ModalidadTrabajo;
import com.cibertec.klearning.business.data.entity.enums.Nacionalidad;
import com.cibertec.klearning.business.data.entity.enums.Sexo;
import com.cibertec.klearning.business.data.entity.enums.SkillPrincipal;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonaResponseDto(
        String idPersona,
        String apellidos,
        String nombres,
        String dniCe,
        Sexo sexo,
        LocalDate fechaNacimiento,
        EstadoCivil estadoCivil,
        Nacionalidad nacionalidad,
        String celular,
        String email,
        FormacionAcademica formacionAcademica,
        LocalDate fechaIngreso,
        LocalDate fechaCese,
        ModalidadTrabajo modalidadTrabajo,
        SkillPrincipal skillPrincipal,
        EstadoRegistro estado,
        String createUser,
        LocalDateTime createDate,
        String updatedUser,
        LocalDateTime updatedDate
) {}
