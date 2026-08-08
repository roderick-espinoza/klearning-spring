package com.cibertec.klearning.security.api.controller;

import com.cibertec.klearning.security.api.dto.auth.LoginRequestDto;
import com.cibertec.klearning.security.api.dto.auth.LoginResponseDto;
import com.cibertec.klearning.security.domain.service.interfaces.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Puerta REST de la autenticacion, la que se prueba desde Bruno.
 * Comparte el AuthService con el formulario de login del navegador.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto) {

        return ResponseEntity.ok(authService.login(requestDto));
    }
}
