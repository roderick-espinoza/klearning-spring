package com.cibertec.klearning.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioResponseDto(
        String idUsuario,
        String usuario,
        String estado,
        PersonaResumenDto persona,
        RolResumenDto rol,
        LocalDateTime createDate,
        LocalDateTime updatedDate
) {}
