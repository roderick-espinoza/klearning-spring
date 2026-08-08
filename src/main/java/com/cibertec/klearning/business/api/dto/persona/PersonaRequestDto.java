package com.cibertec.klearning.business.api.dto.persona;

import com.cibertec.klearning.business.data.entity.enums.EstadoCivil;
import com.cibertec.klearning.business.data.entity.enums.FormacionAcademica;
import com.cibertec.klearning.business.data.entity.enums.ModalidadTrabajo;
import com.cibertec.klearning.business.data.entity.enums.Nacionalidad;
import com.cibertec.klearning.business.data.entity.enums.Sexo;
import com.cibertec.klearning.business.data.entity.enums.SkillPrincipal;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PersonaRequestDto(
        @NotBlank @Size(max = 200) String apellidos,
        @NotBlank @Size(max = 200) String nombres,
        @NotBlank @Size(max = 12) String dniCe,
        @NotNull Sexo sexo,
        @NotNull @Past LocalDate fechaNacimiento,
        @NotNull EstadoCivil estadoCivil,
        @NotNull Nacionalidad nacionalidad,
        @NotBlank @Pattern(regexp = "^9\\d{8}$") String celular,
        @NotBlank @Email String email,
        @NotNull FormacionAcademica formacionAcademica,
        @NotNull LocalDate fechaIngreso,
        LocalDate fechaCese,
        @NotNull ModalidadTrabajo modalidadTrabajo,
        @NotNull SkillPrincipal skillPrincipal
) {}
