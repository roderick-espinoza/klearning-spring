package com.cibertec.klearning.security.api.view;

import com.cibertec.klearning.business.api.view.PersonaViewController;
import com.cibertec.klearning.business.domain.service.interfaces.PersonaService;
import com.cibertec.klearning.security.domain.service.interfaces.RolService;
import com.cibertec.klearning.security.domain.service.interfaces.UsuarioService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Alimenta las tarjetas de metricas que la plantilla base muestra en las vistas
 * autenticadas. Se hace aqui, y no en cada controlador, para no repetir las
 * mismas tres lineas cuatro veces.
 * <p>
 * Se declara por tipos y no por paquete a proposito: el LoginViewController
 * queda fuera, porque en la pantalla de login todavia no hay usuario y no tiene
 * sentido ir a la base de datos.
 */
@ControllerAdvice(assignableTypes = {
        HomeViewController.class,
        PersonaViewController.class,
        UsuarioViewController.class,
        RolViewController.class
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
    public int totalPersonas() {
        return personaService.listar().size();
    }

    @ModelAttribute("totalUsuarios")
    public int totalUsuarios() {
        return usuarioService.listar().size();
    }

    @ModelAttribute("totalRoles")
    public int totalRoles() {
        return rolService.listar().size();
    }
}
