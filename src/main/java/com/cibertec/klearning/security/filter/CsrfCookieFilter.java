package com.cibertec.klearning.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Obliga a que el token CSRF se genere y se mande al navegador dentro de la
 * cadena de filtros, no durante el render de la vista.
 * <p>
 * Spring Security carga el token de forma diferida: el CsrfFilter deja en la
 * peticion un CsrfToken perezoso y el CookieCsrfTokenRepository no escribe la
 * cookie hasta que alguien pide su valor. Quien lo pide es el th:action de
 * Thymeleaf, ya renderizando. Para entonces la respuesta puede llevar escritos
 * varios kilobytes, y Tomcat ignora en silencio los addCookie() sobre una
 * respuesta ya comprometida: el formulario sale con su campo _csrf pero el
 * navegador nunca recibe la cookie con la que compararlo, asi que el siguiente
 * POST se cae con CsrfException.
 * <p>
 * Con las vistas que decoran layout/base pasaba siempre (el form del logout
 * queda a unos 6 KB del inicio del HTML) y con la pantalla de login no (su form
 * esta a unos 2 KB), que es justo por que se podia entrar pero no salir.
 * <p>
 * Pidiendo aqui el valor, el Set-Cookie se emite antes de que el
 * DispatcherServlet escriba un solo byte.
 */
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // Null solo si la cadena no lleva CsrfFilter; en la web si lo lleva.
        if (token != null) {
            token.getToken();
        }

        filterChain.doFilter(request, response);
    }
}
