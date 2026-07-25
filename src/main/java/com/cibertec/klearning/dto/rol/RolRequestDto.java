package com.cibertec.klearning.dto.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RolRequestDto(
        @NotBlank @Size(max = 100) String nombreRol,
        @NotBlank @Size(max = 200) String descripcion
) {}
