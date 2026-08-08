package com.cibertec.klearning.security.api.view.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Unico formulario propio del proyecto. Existe porque el modal de usuario
 * necesita algo que UsuarioRequestDto no puede expresar: al editar, dejar la
 * contraseña vacia significa "no cambiarla", mientras que al crear es
 * obligatoria. El resto de pantallas se enlazan directamente contra su
 * RequestDto.
 * <p>
 * No es un record porque Thymeleaf necesita un bean mutable para repintar el
 * formulario cuando la validacion falla.
 */
@Getter
@Setter
public class UsuarioForm {

    private String idUsuario;

    @NotBlank(message = "Debe seleccionar una persona")
    private String idPersona;

    @NotBlank(message = "El usuario es obligatorio")
    @Size(max = 100)
    private String usuario;

    @Size(min = 8, max = 300, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    /**
     * El campo del modal llega como cadena vacia cuando el usuario no quiere
     * cambiar la contraseña, y una cadena vacia no pasa el @Size(min = 8).
     * Se normaliza a null, que es lo que el servicio interpreta como
     * "mantener la contraseña actual" y lo que @Size ignora.
     */
    public void setPassword(String password) {
        this.password = (password == null || password.isBlank()) ? null : password;
    }

    @NotEmpty(message = "Debe asignar al menos un rol")
    private Set<String> idsRoles = new LinkedHashSet<>();

    public boolean esNuevo() {
        return idUsuario == null || idUsuario.isBlank();
    }
}
