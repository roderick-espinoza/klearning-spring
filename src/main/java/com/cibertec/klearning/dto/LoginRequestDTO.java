package com.cibertec.klearning.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank
    private String usuario;

    @NotBlank
    private String password;
}
