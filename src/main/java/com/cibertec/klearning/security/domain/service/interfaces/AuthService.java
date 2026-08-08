package com.cibertec.klearning.security.domain.service.interfaces;

import com.cibertec.klearning.security.api.dto.auth.LoginRequestDto;
import com.cibertec.klearning.security.api.dto.auth.LoginResponseDto;

/**
 * Unica logica de autenticacion de la aplicacion. La usan tanto el
 * AuthController (JSON, para pruebas con Bruno) como el LoginViewController
 * (formulario del navegador).
 */
public interface AuthService {

    LoginResponseDto login(LoginRequestDto requestDto);
}
