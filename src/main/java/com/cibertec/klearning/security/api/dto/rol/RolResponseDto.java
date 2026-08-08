package com.cibertec.klearning.security.api.dto.rol;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;

import java.time.LocalDateTime;

public record RolResponseDto(
        String idRol,
        String codigoRol,
        String nombreRol,
        String descripcion,
        EstadoRegistro estado,
        String createUser,
        LocalDateTime createDate,
        String updatedUser,
        LocalDateTime updatedDate
) {}
