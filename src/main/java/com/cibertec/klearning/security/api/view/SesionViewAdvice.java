package com.cibertec.klearning.security.api.view;

import com.cibertec.klearning.business.api.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.security.api.dto.usuario.RolResumenDto;
import com.cibertec.klearning.security.domain.service.interfaces.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * Deja en el modelo los nombres visibles de los roles del usuario logueado.
 * <p>
 * Las vistas no pueden sacarlos de {@code principal.authorities}: ahi solo esta
 * la authority tecnica ("ROLE_ADMIN"), que sirve para hasRole() pero no para
 * enseñarsela a nadie. El nombre bonito ("Administrador") solo vive en la tabla
 * de roles, asi que hay que ir a buscarlo.
 * <p>
 * Se declara por paquete y no por tipos: el topbar sale en toda vista que decore
 * layout/base, asi que un modulo nuevo que se olvidara de apuntarse a la lista
 * dejaria rolesUsuario a null y reventaria la pagina entera. El
 * LoginViewController tambien entra, pero ahi la comprobacion devuelve la lista
 * vacia sin tocar la base.
 */
@ControllerAdvice(basePackages = {
        PaquetesVista.SECURITY,
        PaquetesVista.BUSINESS
})
public class SesionViewAdvice {

    private final UsuarioService usuarioService;

    public SesionViewAdvice(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("rolesUsuario")
    public List<String> rolesUsuario(Authentication autenticacion) {

        if (!SesionUsuario.hay(autenticacion)) {
            return List.of();
        }

        try {
            return usuarioService.obtenerPorUsuario(autenticacion.getName())
                    .roles()
                    .stream()
                    .map(RolResumenDto::nombreRol)
                    .sorted()
                    .toList();

        } catch (RecursoNoEncontradoException excepcion) {
            // El token sigue vivo pero el usuario ya no esta: el topbar no es
            // sitio para reventar la pagina, se muestra sin roles y punto.
            return List.of();
        }
    }
}
