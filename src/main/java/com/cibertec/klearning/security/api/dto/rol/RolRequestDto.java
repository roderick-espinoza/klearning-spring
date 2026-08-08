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
) {

    /**
     * Normaliza el codigo antes de que corra Bean Validation.
     * <p>
     * Spring construye el record por su constructor canonico tanto al enlazar
     * el formulario (@ModelAttribute) como al deserializar el JSON del API
     * (@RequestBody), asi que escribir "admin" en cualquiera de las dos puertas
     * acaba guardado como "ADMIN" y el @Pattern de arriba ya lo recibe correcto.
     */
    public RolRequestDto {
        if (codigoRol != null) {
            codigoRol = codigoRol.trim().toUpperCase();
        }
    }
}
