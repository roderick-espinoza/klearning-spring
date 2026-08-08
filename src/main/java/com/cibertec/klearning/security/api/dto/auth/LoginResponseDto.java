package com.cibertec.klearning.security.api.dto.auth;

import java.util.Set;

public record LoginResponseDto(
        String token,
        String tipo,
        long expiresIn,
        String username,
        Set<String> roles
) {}
