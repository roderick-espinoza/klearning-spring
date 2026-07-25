package com.cibertec.klearning.dto.persona;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PersonaRequestDto(
        @NotBlank @Size(max = 200) String apellidos,
        @NotBlank @Size(max = 200) String nombres,
        @NotBlank @Size(max = 12) String dniCe,
        @NotBlank @Size(max = 1) String sexo,
        @NotNull @Past LocalDate fechaNacimiento,
        @NotBlank String estadoCivil,
        @NotBlank String nacionalidad,
        @NotBlank @Pattern(regexp = "^9\\d{8}$") String celular,
        @NotBlank @Email String email,
        @NotBlank String formacionAcademica,
        @NotNull LocalDate fechaIngreso,
        LocalDate fechaCese,
        @NotBlank String modalidadTrabajo,
        @NotBlank String skillPrincipal
) {}
