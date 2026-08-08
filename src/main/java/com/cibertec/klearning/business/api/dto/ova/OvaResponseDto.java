package com.cibertec.klearning.business.api.dto.ova;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;

import com.cibertec.klearning.business.data.entity.enums.TipoOva;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OvaResponseDto(
        String idOva,
        String nombre,
        TipoOva tipo,
        LocalDateTime fechaInicioVigencia,
        LocalDateTime fechaFinVigencia,
        Integer duracion,
        EstadoRegistro estado,
        String createUser,
        LocalDateTime createDate,
        String updatedUser,
        LocalDateTime updatedDate,
        LocalDateTime deletedDate,
        String deletedUser
) {}
