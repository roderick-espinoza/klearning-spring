package com.cibertec.klearning.dto.ova;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OvaRequestDto(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 200, message = "El nombre no puede superar los 200 caracteres")
        String nombre,

        @NotBlank(message = "El tipo es obligatorio")
        @Size(max = 100, message = "El tipo no puede superar los 100 caracteres")
        String tipo,

        @NotNull(message = "La fecha de inicio de vigencia es obligatoria")
        LocalDateTime fechaInicioVigencia,

        @Future(message = "La fecha de fin de vigencia debe ser una fecha futura")
        LocalDateTime fechaFinVigencia,

        @NotNull(message = "La duración es obligatoria")
        @Min(value = 1, message = "La duración debe ser mayor que 0")
        Integer duracion
        ) { }
