package com.cibertec.klearning.business.api.view;

import com.cibertec.klearning.business.api.dto.persona.PersonaRequestDto;
import com.cibertec.klearning.business.data.entity.enums.EstadoCivil;
import com.cibertec.klearning.business.data.entity.enums.FormacionAcademica;
import com.cibertec.klearning.business.data.entity.enums.ModalidadTrabajo;
import com.cibertec.klearning.business.data.entity.enums.Nacionalidad;
import com.cibertec.klearning.business.data.entity.enums.Sexo;
import com.cibertec.klearning.business.data.entity.enums.SkillPrincipal;
import com.cibertec.klearning.business.domain.service.interfaces.PersonaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Sirve las vistas de persona. No contiene logica de negocio: llama al mismo
 * PersonaService que usa PersonaController (el REST); la unica diferencia es
 * que aqui se devuelve el nombre de una plantilla en lugar de JSON.
 */
@Controller
@RequestMapping("/personas")
public class PersonaViewController {

    private final PersonaService personaService;

    public PersonaViewController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", personaService.listar());

        model.addAttribute("sexos", Sexo.values());
        model.addAttribute("estadosCiviles", EstadoCivil.values());
        model.addAttribute("nacionalidades", Nacionalidad.values());
        model.addAttribute("formaciones", FormacionAcademica.values());
        model.addAttribute("modalidades", ModalidadTrabajo.values());
        model.addAttribute("skills", SkillPrincipal.values());

        return "personas/index";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute PersonaRequestDto persona,
                        RedirectAttributes atributos) {

        personaService.crear(persona);
        atributos.addFlashAttribute("mensajeExito", "Persona registrada correctamente");

        return "redirect:/personas";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable String id,
                             @Valid @ModelAttribute PersonaRequestDto persona,
                             RedirectAttributes atributos) {

        personaService.actualizar(id, persona);
        atributos.addFlashAttribute("mensajeExito", "Persona actualizada correctamente");

        return "redirect:/personas";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes atributos) {
        personaService.eliminar(id);
        atributos.addFlashAttribute("mensajeExito", "Persona eliminada correctamente");

        return "redirect:/personas";
    }
}
