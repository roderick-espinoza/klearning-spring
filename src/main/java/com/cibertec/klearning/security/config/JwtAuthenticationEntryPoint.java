package com.cibertec.klearning.security.config;

import com.cibertec.klearning.business.api.exception.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Respuesta 401 en JSON para las peticiones al API. El navegador no pasa por
 * aqui: la cadena web redirige al formulario de login.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException excepcion) throws IOException {

        HttpStatus estado = HttpStatus.UNAUTHORIZED;

        ApiErrorResponse cuerpo = new ApiErrorResponse(
                LocalDateTime.now(),
                estado.value(),
                estado.getReasonPhrase(),
                "No se encuentra autenticado o el token no es válido",
                request.getRequestURI(),
                Map.of()
        );

        response.setStatus(estado.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getOutputStream(), cuerpo);
    }
}
