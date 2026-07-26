package com.cibertec.klearning.dto.ova;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OvaResponseDto(
        String idOva,
        String nombre,
        String tipo,
        LocalDateTime fechaInicioVigencia,
        LocalDateTime fechaFinVigencia,
        Integer duracion,
        String estado,
        String createUser,
        LocalDateTime createDate,
        String updatedUser,
        LocalDateTime updatedDate,
        LocalDateTime deletedDate,
        String deletedUser
) {}
