package com.cibertec.klearning.dto.proyecto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ProyectoRequestDto(
        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotBlank(message = "El tipo de vertical es obligatorio")
        @Size(max = 100, message = "El tipo de vertical no puede superar los 100 caracteres")
        String tipoVertical,

        @NotBlank(message = "El nombre del proyecto es obligatorio")
        @Size(max = 200, message = "El nombre no puede superar los 200 caracteres")
        String nombre,

        @NotBlank(message = "Los KPIs son obligatorios")
        @Size(max = 100, message = "Los KPIs no pueden superar los 100 caracteres")
        String kpis,

        @NotBlank(message = "El objetivo es obligatorio")
        @Size(max = 300, message = "El objetivo no puede superar los 300 caracteres")
        String objetivo,

        @NotBlank(message = "Las necesidades son obligatorias")
        @Size(max = 300, message = "Las necesidades no pueden superar los 300 caracteres")
        String necesidades,

        @NotBlank(message = "La cuenta es obligatoria")
        @Size(max = 100, message = "La cuenta no puede superar los 100 caracteres")
        String cuenta,

        @NotBlank(message = "El segmento es obligatorio")
        @Size(max = 100, message = "El segmento no puede superar los 100 caracteres")
        String segmento,

        @NotBlank(message = "El área solicitante es obligatoria")
        @Size(max = 200, message = "El área solicitante no puede superar los 200 caracteres")
        String areaSolicitante,

        @NotBlank(message = "El Product Owner es obligatorio")
        @Size(max = 100, message = "El Product Owner no puede superar los 100 caracteres")
        String productOwner,

        @NotBlank(message = "El sponsor es obligatorio")
        @Size(max = 100, message = "El sponsor no puede superar los 100 caracteres")
        String sponsor,

        @NotNull(message = "La fecha de solicitud es obligatoria")
        LocalDate fechaSolicitud,

        @NotBlank(message = "Los antecedentes son obligatorios")
        @Size(max = 400, message = "Los antecedentes no pueden superar los 400 caracteres")
        String antecedentes,

        @Size(max = 300, message = "La descripción no puede superar los 300 caracteres")
        String descripcion
) {}