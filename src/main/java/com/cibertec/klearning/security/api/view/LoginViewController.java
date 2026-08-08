package com.cibertec.klearning.security.api.view;

import com.cibertec.klearning.security.api.dto.auth.LoginRequestDto;
import com.cibertec.klearning.security.api.dto.auth.LoginResponseDto;
import com.cibertec.klearning.security.domain.service.interfaces.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;

/**
 * Puerta visual de la autenticacion.
 * <p>
 * Llama exactamente al mismo {@link AuthService} que usa el AuthController
 * REST; la unica diferencia es que aqui el token no se devuelve en el cuerpo
 * sino que se deja en una cookie HttpOnly, para que el navegador lo reenvie
 * solo en cada peticion posterior.
 */
@Controller
public class LoginViewController {

    private final AuthService authService;
    private final String nombreCookie;

    public LoginViewController(AuthService authService,
                               @Value("${security.jwt.cookie-name}") String nombreCookie) {
        this.authService = authService;
        this.nombreCookie = nombreCookie;
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String logout,
                               Model model) {
        if (logout != null) {
            model.addAttribute("mensaje", "Has cerrado la sesión correctamente");
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpServletResponse response,
                                Model model) {
        try {
            LoginResponseDto resultado =
                    authService.login(new LoginRequestDto(username, password));

            response.addHeader(HttpHeaders.SET_COOKIE,
                    construirCookie(resultado.token(),
                            Duration.ofMillis(resultado.expiresIn())).toString());

            return "redirect:/home";

        } catch (AuthenticationException excepcion) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            model.addAttribute("username", username);
            return "login";
        }
    }

    // El cierre de sesion NO se maneja aqui: el LogoutFilter de Spring atiende
    // POST /logout antes de llegar a ningun controlador. El borrado de la
    // cookie esta declarado en SecurityConfig.webFilterChain().

    private ResponseCookie construirCookie(String valor, Duration duracion) {
        return ResponseCookie.from(nombreCookie, valor)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(duracion)
                .build();
    }
}
