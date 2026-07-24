package com.cibertec.klearning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "idPersona es obligatorio")
    @Size(max = 8)
    private String idPersona;

    @NotBlank(message = "idRol es obligatorio")
    @Size(max = 8)
    private String idRol;

    @NotBlank(message = "usuario es obligatorio")
    @Size(max = 100)
    private String usuario;

    @NotBlank(message = "password es obligatorio")
    @Size(min = 5, max = 300)
    private String password;


    @NotBlank(message = "createUser es obligatorio")
    @Size(max = 50)
    private String createUser;
}
