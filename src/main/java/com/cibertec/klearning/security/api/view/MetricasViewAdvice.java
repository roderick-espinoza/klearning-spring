package com.cibertec.klearning.security.api.view;

import com.cibertec.klearning.business.domain.service.interfaces.PersonaService;
import com.cibertec.klearning.security.domain.service.interfaces.RolService;
import com.cibertec.klearning.security.domain.service.interfaces.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Alimenta las tarjetas de metricas que la plantilla base muestra en las vistas
 * autenticadas. Se hace aqui, y no en cada controlador, para no repetir las
 * mismas tres lineas cuatro veces.
 * <p>
 * Se declara por paquete, igual que el {@link SesionViewAdvice}: estaba por
 * tipos para dejar fuera al LoginViewController, pero eso obligaba a apuntar
 * cada vista nueva a la lista y, si alguien se olvidaba, fragments/metrics.html
 * salia con las tres tarjetas a null. Que el login no toque la base de datos lo
 * resuelve el guard de {@link SesionUsuario}, no el scoping.
 */
@ControllerAdvice(basePackages = {
        PaquetesVista.SECURITY,
        PaquetesVista.BUSINESS
})
public class MetricasViewAdvice {

    private final PersonaService personaService;
    private final UsuarioService usuarioService;
    private final RolService rolService;

    public MetricasViewAdvice(PersonaService personaService,
                              UsuarioService usuarioService,
                              RolService rolService) {
        this.personaService = personaService;
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @ModelAttribute("totalPersonas")
    public int totalPersonas(Authentication autenticacion) {
        return SesionUsuario.hay(autenticacion) ? personaService.listar().size() : 0;
    }

    @ModelAttribute("totalUsuarios")
    public int totalUsuarios(Authentication autenticacion) {
        return SesionUsuario.hay(autenticacion) ? usuarioService.listar().size() : 0;
    }

    @ModelAttribute("totalRoles")
    public int totalRoles(Authentication autenticacion) {
        return SesionUsuario.hay(autenticacion) ? rolService.listar().size() : 0;
    }
}
