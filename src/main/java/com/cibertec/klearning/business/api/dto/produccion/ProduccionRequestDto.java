package com.cibertec.klearning.business.api.dto.produccion;

import com.cibertec.klearning.business.data.entity.enums.EstadoTarea;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProduccionRequestDto(

        @NotBlank(message = "El ID del proyecto es obligatorio")
        String idProyecto,

        @NotBlank(message = "El ID de la lección OVA es obligatorio")
        String idLeccionOva,

        @NotBlank(message = "El ID de la persona asignada es obligatorio")
        String idPersona,

        @NotNull(message = "La fecha y hora de inicio es obligatoria")
        LocalDateTime fechaHoraInicio,

        // No es obligatorio al crear, se llenará cuando el estado sea COMPLETADO
        LocalDateTime fechaHoraFin,

        @NotNull(message = "Las horas estimadas son obligatorias")
        @DecimalMin(value = "0.0", inclusive = false, message = "Las horas estimadas deben ser mayores a 0")
        BigDecimal horasEstimadas,

        // Puede iniciar en null o 0, se actualizará conforme avance la tarea
        BigDecimal horasReales,

        @NotNull(message = "Debe indicar si hubo corte")
        Boolean huboCorte,

        @Size(max = 300, message = "La descripción del corte no puede superar los 300 caracteres")
        String descripcionCorte,

        BigDecimal horasContratiempo,

        @NotNull(message = "El estado de la tarea es obligatorio")
        EstadoTarea estadoTarea,

        Boolean cumplimiento,

        @Size(max = 300, message = "Las observaciones no pueden superar los 300 caracteres")
        String observaciones
) {}