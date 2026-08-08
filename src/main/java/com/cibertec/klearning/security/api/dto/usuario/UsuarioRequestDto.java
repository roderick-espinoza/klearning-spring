package com.cibertec.klearning.security.api.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UsuarioRequestDto(
        @NotBlank String idPersona,
        @NotBlank @Size(max = 100) String usuario,

        // Obligatorio al crear (lo valida el servicio); en blanco al actualizar
        // significa "mantener la contraseña actual".
        @Size(min = 8, max = 300) String password,

        @NotEmpty(message = "Debe asignar al menos un rol")
        Set<String> idsRoles
) {}
