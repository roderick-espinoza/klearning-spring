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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idUsuario", length = 36, updatable = false)
    private String idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPersona", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @Column(name = "usuario", nullable = false, length = 100)
    private String usuario;

    @Column(name = "password", nullable = false, length = 300)
    private String password;
}
