package com.cibertec.klearning.security.api.dto.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RolRequestDto(
        @NotBlank
        @Size(max = 30)
        @Pattern(regexp = "^[A-Z][A-Z0-9_]*$",
                message = "El código debe ir en mayúsculas, sin espacios ni tildes")
        String codigoRol,

        @NotBlank @Size(max = 100) String nombreRol,
        @NotBlank @Size(max = 200) String descripcion
) {}
