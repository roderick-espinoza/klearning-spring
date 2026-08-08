package com.cibertec.klearning.business.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Acotado a los @RestController: los controladores de vista devuelven HTML y
 * no deben ver sus errores convertidos en JSON.
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> manejarNoEncontrado(
            RecursoNoEncontradoException excepcion, HttpServletRequest peticion) {
        return construir(HttpStatus.NOT_FOUND, excepcion.getMessage(), peticion, Map.of());
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ApiErrorResponse> manejarDuplicado(
            RecursoDuplicadoException excepcion, HttpServletRequest peticion) {
        return construir(HttpStatus.CONFLICT, excepcion.getMessage(), peticion, Map.of());
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<ApiErrorResponse> manejarSolicitudInvalida(
            SolicitudInvalidaException excepcion, HttpServletRequest peticion) {
        return construir(HttpStatus.BAD_REQUEST, excepcion.getMessage(), peticion, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> manejarValidacion(
            MethodArgumentNotValidException excepcion, HttpServletRequest peticion) {

        Map<String, String> errores = excepcion.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() == null ? "" : error.getDefaultMessage(),
                        (primero, segundo) -> primero));

        return construir(HttpStatus.BAD_REQUEST,
                "La solicitud contiene campos inválidos", peticion, errores);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> manejarTipoInvalido(
            MethodArgumentTypeMismatchException excepcion, HttpServletRequest peticion) {
        return construir(HttpStatus.BAD_REQUEST,
                "Valor no válido para el parámetro: " + excepcion.getName(), peticion, Map.of());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> manejarAutenticacion(
            AuthenticationException excepcion, HttpServletRequest peticion) {
        return construir(HttpStatus.UNAUTHORIZED,
                "Usuario o contraseña incorrectos", peticion, Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> manejarErrorInesperado(
            Exception excepcion, HttpServletRequest peticion) {
        return construir(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error inesperado", peticion, Map.of());
    }

    private ResponseEntity<ApiErrorResponse> construir(HttpStatus estado,
                                                       String mensaje,
                                                       HttpServletRequest peticion,
                                                       Map<String, String> erroresValidacion) {
        ApiErrorResponse cuerpo = new ApiErrorResponse(
                LocalDateTime.now(),
                estado.value(),
                estado.getReasonPhrase(),
                mensaje,
                peticion.getRequestURI(),
                erroresValidacion
        );

        return ResponseEntity.status(estado).body(cuerpo);
    }
}
