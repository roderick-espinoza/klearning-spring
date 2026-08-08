package com.cibertec.klearning.security.config;

import com.cibertec.klearning.security.domain.service.implementations.CustomUserDetailsService;
import com.cibertec.klearning.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Dos cadenas de filtros, una sola logica de autenticacion.
 * <p>
 * Ambas comparten el mismo {@link JwtAuthenticationFilter}, el mismo
 * {@link AuthenticationProvider} y el mismo {@link CustomUserDetailsService}.
 * Lo unico que cambia es como se le responde a quien no esta autenticado:
 * el API devuelve 401 en JSON (que es lo que se prueba desde Bruno) y la web
 * redirige al formulario de login. Ademas CSRF va activo solo en la web,
 * porque los formularios del navegador lo necesitan y las peticiones con
 * token no.
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final String nombreCookie;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAccessDeniedHandler accessDeniedHandler,
                          @Value("${security.jwt.cookie-name}") String nombreCookie) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.nombreCookie = nombreCookie;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    /** Cadena del API REST: sin estado, sin CSRF y con errores en JSON. */
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        return http
                .securityMatcher("/api/**")

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/roles/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    /** Cadena de las vistas: mismo token, pero redirige al login y usa CSRF. */
    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

        return http
                // El token CSRF se guarda en cookie, no en sesion: la cadena web
                // tambien es stateless, asi que no hay HttpSession donde guardarlo.
                // Thymeleaf inyecta el campo oculto _csrf en cada formulario.
                .csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login")))

                // El LogoutFilter de Spring atiende POST /logout antes que
                // cualquier controlador, asi que el borrado de la cookie con el
                // token tiene que declararse aqui: si se deja en un @Controller
                // nunca llega a ejecutarse y la sesion seguiria abierta.
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies(nombreCookie)
                        .logoutSuccessUrl("/login?logout"))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/logout", "/error").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/assets/**")
                        .permitAll()
                        .requestMatchers("/usuarios/**", "/roles/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .authenticationProvider(authenticationProvider())

                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}
