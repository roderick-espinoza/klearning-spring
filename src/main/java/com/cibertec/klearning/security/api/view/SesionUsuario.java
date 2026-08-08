package com.cibertec.klearning.security.api.view;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * Comprobacion compartida por los advices que se declaran por paquete: como el
 * LoginViewController tambien entra, hay que saber si detras hay un usuario de
 * verdad antes de ir a la base de datos.
 */
final class SesionUsuario {

    /**
     * No vale con {@code isAuthenticated()}. Con el AnonymousAuthenticationFilter
     * activo, en /login llega un AnonymousAuthenticationToken que ya responde
     * true, asi que hay que descartarlo a mano.
     */
    static boolean hay(Authentication autenticacion) {
        return autenticacion != null
                && autenticacion.isAuthenticated()
                && !(autenticacion instanceof AnonymousAuthenticationToken);
    }

    private SesionUsuario() {
    }
}
