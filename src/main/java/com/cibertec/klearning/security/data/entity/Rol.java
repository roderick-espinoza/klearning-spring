package com.cibertec.klearning.security.data.entity;

import com.cibertec.klearning.business.data.entity.base.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rol extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idRol", length = 36, updatable = false)
    private String idRol;

    /**
     * Codigo tecnico del rol (ADMIN, ANALISTA...). Es el que se usa para
     * construir la authority "ROLE_" + codigoRol, porque el nombre visible
     * lleva espacios y tildes y no sirve para hasRole().
     */
    @Column(name = "codigoRol", nullable = false, unique = true, length = 30)
    private String codigoRol;

    @Column(name = "nombreRol", nullable = false, length = 100)
    private String nombreRol;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;
}
