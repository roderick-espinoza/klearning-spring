package com.cibertec.klearning.dto.produccion;

import com.cibertec.klearning.entity.enums.EstadoTarea;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProduccionResponseDto(
        String idProduccion,
        String idProyecto,
        String idLeccionOva,
        String idPersona,
        LocalDateTime fechaHoraInicio,
        LocalDateTime fechaHoraFin,
        BigDecimal horasEstimadas,
        BigDecimal horasReales,
        Boolean huboCorte,
        String descripcionCorte,
        BigDecimal horasContratiempo,
        EstadoTarea estadoTarea,
        Boolean cumplimiento,
        String observaciones,

        // Campos de auditoría heredados
        String estado,
        String createUser,
        LocalDateTime createDate,
        String updatedUser,
        LocalDateTime updatedDate
) {}