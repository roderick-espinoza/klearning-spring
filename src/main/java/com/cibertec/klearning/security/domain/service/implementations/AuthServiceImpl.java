package com.cibertec.klearning.security.domain.service.implementations;

import com.cibertec.klearning.security.api.dto.auth.LoginRequestDto;
import com.cibertec.klearning.security.api.dto.auth.LoginResponseDto;
import com.cibertec.klearning.security.domain.service.interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        String username = requestDto.username().trim().toLowerCase();

        UsernamePasswordAuthenticationToken solicitud =
                new UsernamePasswordAuthenticationToken(username, requestDto.password());

        UserDetails userDetails = (UserDetails) authenticationManager
                .authenticate(solicitud)
                .getPrincipal();

        String token = jwtService.generarToken(userDetails);

        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new LoginResponseDto(
                token,
                "Bearer",
                jwtService.obtenerTiempoExpiracion(),
                userDetails.getUsername(),
                roles
        );
    }
}
