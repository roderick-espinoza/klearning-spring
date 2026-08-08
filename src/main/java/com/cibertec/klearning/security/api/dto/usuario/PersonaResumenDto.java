package com.cibertec.klearning.security.api.dto.usuario;

public record PersonaResumenDto(
        String idPersona,
        String nombres,
        String apellidos,
        String dniCe
) {}
