package com.cibertec.klearning.security.filter;

import com.cibertec.klearning.security.domain.service.implementations.CustomUserDetailsService;
import com.cibertec.klearning.security.domain.service.implementations.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Unico filtro de autenticacion de la aplicacion.
 * <p>
 * Acepta el token por dos vias, porque son dos clientes distintos del mismo
 * backend: la cabecera {@code Authorization: Bearer ...} (Bruno, Postman) y
 * la cookie HttpOnly que deja el formulario de login (navegador).
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final String nombreCookie;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   CustomUserDetailsService userDetailsService,
                                   @Value("${security.jwt.cookie-name}") String nombreCookie) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.nombreCookie = nombreCookie;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = extraerToken(request);

        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            autenticarUsuario(token, request);
        } catch (JwtException | IllegalArgumentException | UsernameNotFoundException excepcion) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extraerToken(HttpServletRequest request) {
        String cabecera = request.getHeader("Authorization");

        if (cabecera != null && cabecera.startsWith(BEARER_PREFIX)) {
            return cabecera.substring(BEARER_PREFIX.length());
        }

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> nombreCookie.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    private void autenticarUsuario(String token, HttpServletRequest request) {
        String username = jwtService.obtenerUsername(token);

        boolean yaAutenticado =
                SecurityContextHolder.getContext().getAuthentication() != null;

        if (username == null || yaAutenticado) {
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.esTokenValido(token, userDetails)) {
            return;
        }

        UsernamePasswordAuthenticationToken autenticacion =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

        autenticacion.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(autenticacion);
    }
}
