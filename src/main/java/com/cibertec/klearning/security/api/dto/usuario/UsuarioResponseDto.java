package com.cibertec.klearning.security.api.dto.usuario;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;

import java.time.LocalDateTime;
import java.util.Set;

public record UsuarioResponseDto(
        String idUsuario,
        String usuario,
        EstadoRegistro estado,
        PersonaResumenDto persona,
        Set<RolResumenDto> roles,
        String createUser,
        LocalDateTime createDate,
        String updatedUser,
        LocalDateTime updatedDate
) {}
