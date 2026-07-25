package com.cibertec.klearning.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDto(
        @NotBlank String idPersona,
        @NotBlank String idRol,
        @NotBlank @Size(max = 100) String usuario,
        @NotBlank @Size(min = 8, max = 300) String password
) {}
