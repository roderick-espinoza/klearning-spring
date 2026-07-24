package com.cibertec.klearning.entity;

import com.cibertec.klearning.entity.base.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString(exclude = {"persona", "rol"})
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends AuditEntity {

    @Id
    @Column(name = "idUsuario", length = 8)
    private String idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPersona", nullable = false,
            foreignKey = @ForeignKey(name = "FK_Usuario_Persona"))
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idRol", nullable = false,
            foreignKey = @ForeignKey(name = "FK_Usuario_Rol"))
    private Rol rol;

    @Column(name = "usuario", nullable = false, length = 100)
    private String usuario;

    @Column(name = "password", nullable = false, length = 300)
    private String password;
}
