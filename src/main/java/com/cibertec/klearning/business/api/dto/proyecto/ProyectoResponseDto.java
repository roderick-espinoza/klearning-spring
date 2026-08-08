package com.cibertec.klearning.business.api.dto.proyecto;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProyectoResponseDto(
        String idProyecto,
        LocalDate fecha,
        String tipoVertical,
        String nombre,
        String kpis,
        String objetivo,
        String necesidades,
        String cuenta,
        String segmento,
        String areaSolicitante,
        String productOwner,
        String sponsor,
        LocalDate fechaSolicitud,
        String antecedentes,
        String descripcion,
        EstadoRegistro estado,
        LocalDateTime createDate,
        LocalDateTime updatedDate
) {}