package com.cibertec.klearning.dto.persona;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonaResponseDto(
        String idPersona,
        String apellidos,
        String nombres,
        String dniCe,
        String sexo,
        LocalDateTime fechaNacimiento,
        String estadoCivil,
        String nacionalidad,
        String celular,
        String email,
        String formacionAcademica,
        LocalDate fechaIngreso,
        LocalDate fechaCese,
        String modalidadTrabajo,
        String skillPrincipal,
        String estado,
        LocalDateTime createDate,
        LocalDateTime updatedDate
) {}
