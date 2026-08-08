package com.cibertec.klearning.security.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfException;

import java.io.IOException;

/**
 * Equivalente al JwtAccessDeniedHandler pero para las vistas: en vez de un 403
 * en JSON devuelve una redireccion, porque delante hay un navegador y no Bruno.
 * <p>
 * Sin este handler el CsrfFilter responde con sendError(403) y el usuario acaba
 * en la Whitelabel Error Page, que ademas deja la URL en /logout y parece que la
 * vista no existe.
 * <p>
 * Redirigir no basta. El CsrfFilter corre antes que el LogoutFilter, asi que un
 * fallo de CSRF en POST /logout aborta el cierre de sesion: las cookies siguen
 * ahi y el usuario acaba en el login, autenticado, leyendo que su sesion expiro.
 * Y como el POST /login del formulario falla por lo mismo, vuelve a esta pantalla
 * una y otra vez sin poder entrar. Por eso aqui se caducan las dos cookies antes
 * de redirigir: el ACCESS_TOKEN porque el usuario queria salir de verdad, y el
 * XSRF-TOKEN para que el CookieCsrfTokenRepository genere uno nuevo en el
 * siguiente GET /login y la cookie y el campo _csrf del formulario vuelvan a
 * cuadrar por construccion.
 */
public class VistaAccessDeniedHandler implements AccessDeniedHandler {

    /** El que usa CookieCsrfTokenRepository por defecto. */
    private static final String COOKIE_CSRF = "XSRF-TOKEN";

    private final String nombreCookie;

    public VistaAccessDeniedHandler(String nombreCookie) {
        this.nombreCookie = nombreCookie;
    }

    @Override
    public void handle(HttpServletRequest peticion,
                       HttpServletResponse respuesta,
                       AccessDeniedException excepcion) throws IOException {

        // Un CsrfException significa token perdido o caducado: la unica salida
        // razonable es volver a autenticarse desde cero. Cualquier otro
        // AccessDenied es un permiso insuficiente, y ahi la sesion si es valida
        // y no hay nada que limpiar.
        if (excepcion instanceof CsrfException) {
            caducar(respuesta, nombreCookie);
            caducar(respuesta, COOKIE_CSRF);
            respuesta.sendRedirect(peticion.getContextPath() + "/login?expirado");
            return;
        }

        respuesta.sendRedirect(peticion.getContextPath() + "/home?denegado");
    }

    /**
     * Mismos atributos con los que se crean las cookies (path y httpOnly); si no
     * coinciden el navegador la trata como otra cookie distinta y no la borra.
     */
    private void caducar(HttpServletResponse respuesta, String nombre) {
        respuesta.addHeader(HttpHeaders.SET_COOKIE,
                ResponseCookie.from(nombre, "")
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(0)
                        .build()
                        .toString());
    }
}
