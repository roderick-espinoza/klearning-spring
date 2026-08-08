package com.cibertec.klearning.security.api.view;

import com.cibertec.klearning.business.domain.service.interfaces.PersonaService;
import com.cibertec.klearning.security.api.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.security.api.view.form.UsuarioForm;
import com.cibertec.klearning.security.domain.service.interfaces.RolService;
import com.cibertec.klearning.security.domain.service.interfaces.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    private final UsuarioService usuarioService;
    private final PersonaService personaService;
    private final RolService rolService;

    public UsuarioViewController(UsuarioService usuarioService,
                                 PersonaService personaService,
                                 RolService rolService) {
        this.usuarioService = usuarioService;
        this.personaService = personaService;
        this.rolService = rolService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", usuarioService.listar());
        model.addAttribute("personas", personaService.listarActivos());
        model.addAttribute("roles", rolService.listarActivos());

        return "usuarios/index";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute UsuarioForm formulario,
                        RedirectAttributes atributos) {

        usuarioService.crear(aRequestDto(formulario));
        atributos.addFlashAttribute("mensajeExito", "Usuario registrado correctamente");

        return "redirect:/usuarios";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable String id,
                             @Valid @ModelAttribute UsuarioForm formulario,
                             RedirectAttributes atributos) {

        usuarioService.actualizar(id, aRequestDto(formulario));
        atributos.addFlashAttribute("mensajeExito", "Usuario actualizado correctamente");

        return "redirect:/usuarios";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes atributos) {
        usuarioService.eliminar(id);
        atributos.addFlashAttribute("mensajeExito", "Usuario eliminado correctamente");

        return "redirect:/usuarios";
    }

    private UsuarioRequestDto aRequestDto(UsuarioForm formulario) {
        return new UsuarioRequestDto(
                formulario.getIdPersona(),
                formulario.getUsuario(),
                formulario.getPassword(),
                formulario.getIdsRoles()
        );
    }
}
