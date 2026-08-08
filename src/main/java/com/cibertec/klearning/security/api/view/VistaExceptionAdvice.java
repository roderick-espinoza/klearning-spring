package com.cibertec.klearning.security.api.view;

import com.cibertec.klearning.business.api.exception.RecursoDuplicadoException;
import com.cibertec.klearning.business.api.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.business.api.exception.SolicitudInvalidaException;
import com.cibertec.klearning.business.api.view.PersonaViewController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

/**
 * Equivalente al GlobalExceptionHandler pero para las vistas: en vez de
 * devolver JSON, deja el mensaje como flash attribute y vuelve a la pantalla
 * anterior, que es lo que espera un usuario delante de un formulario.
 */
@ControllerAdvice(assignableTypes = {
        PersonaViewController.class,
        UsuarioViewController.class,
        RolViewController.class
})
public class VistaExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public String manejarValidacion(BindException excepcion,
                                    HttpServletRequest peticion,
                                    RedirectAttributes atributos) {

        String detalle = excepcion.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(". "));

        return volver(peticion, atributos,
                detalle.isBlank() ? "Revisa los datos del formulario" : detalle);
    }

    @ExceptionHandler({
            RecursoDuplicadoException.class,
            SolicitudInvalidaException.class,
            RecursoNoEncontradoException.class
    })
    public String manejarNegocio(RuntimeException excepcion,
                                 HttpServletRequest peticion,
                                 RedirectAttributes atributos) {
        return volver(peticion, atributos, excepcion.getMessage());
    }

    /**
     * Vuelve al listado del modulo deduciendolo del primer segmento de la URL
     * (/roles/abc -> /roles). No se usa la cabecera Referer porque no siempre
     * llega y dejaria al usuario en una pantalla que no es la suya.
     */
    private String volver(HttpServletRequest peticion,
                          RedirectAttributes atributos,
                          String mensaje) {

        atributos.addFlashAttribute("mensajeError", mensaje);

        String[] segmentos = peticion.getRequestURI().split("/");

        return segmentos.length > 1 && !segmentos[1].isBlank()
                ? "redirect:/" + segmentos[1]
                : "redirect:/home";
    }
}
