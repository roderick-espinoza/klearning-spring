package com.cibertec.klearning.dto;

import com.cibertec.klearning.entity.Usuario;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private String idUsuario;
    private String usuario;
    private String estado;

    private String idPersona;
    private String nombreCompleto;
    private String skillPrincipal;

    private String idRol;
    private String nombreRol;

    private String createUser;
    private LocalDateTime createDate;


    public static UsuarioResponseDTO desde(Usuario u) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setUsuario(u.getUsuario());
        dto.setEstado(u.getEstado());          // heredado de AuditEntity
        dto.setCreateUser(u.getCreateUser());  // heredado de AuditEntity
        dto.setCreateDate(u.getCreateDate());

        if (u.getPersona() != null) {
            dto.setIdPersona(u.getPersona().getIdPersona());
            dto.setNombreCompleto(u.getPersona().getNombres() + " " + u.getPersona().getApellidos());
            dto.setSkillPrincipal(u.getPersona().getSkillPrincipal());
        }
        if (u.getRol() != null) {
            dto.setIdRol(u.getRol().getIdRol());
            dto.setNombreRol(u.getRol().getNombreRol());
        }
        return dto;
    }
}
