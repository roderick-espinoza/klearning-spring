package com.cibertec.klearning.business.api.dto.produccion;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.business.data.entity.enums.EstadoTarea;

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
        EstadoRegistro estado,
        String createUser,
        LocalDateTime createDate,
        String updatedUser,
        LocalDateTime updatedDate
) {}