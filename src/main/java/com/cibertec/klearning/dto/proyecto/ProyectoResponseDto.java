package com.cibertec.klearning.dto.proyecto;

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
        String estado,
        LocalDateTime createDate,
        LocalDateTime updatedDate
) {}