package com.cibertec.klearning.security.api.view;

import com.cibertec.klearning.security.api.dto.rol.RolRequestDto;
import com.cibertec.klearning.security.domain.service.interfaces.RolService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
public class RolViewController {

    private final RolService rolService;

    public RolViewController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", rolService.listar());
        return "roles/index";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute RolRequestDto rol,
                        RedirectAttributes atributos) {

        rolService.crear(rol);
        atributos.addFlashAttribute("mensajeExito", "Rol registrado correctamente");

        return "redirect:/roles";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable String id,
                             @Valid @ModelAttribute RolRequestDto rol,
                             RedirectAttributes atributos) {

        rolService.actualizar(id, rol);
        atributos.addFlashAttribute("mensajeExito", "Rol actualizado correctamente");

        return "redirect:/roles";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes atributos) {
        rolService.eliminar(id);
        atributos.addFlashAttribute("mensajeExito", "Rol eliminado correctamente");

        return "redirect:/roles";
    }
}
