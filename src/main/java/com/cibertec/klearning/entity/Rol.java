package com.cibertec.klearning.entity;

import com.cibertec.klearning.entity.base.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "idRol", length = 8)
    private String idRol;

    @Column(name = "nombreRol", nullable = false, length = 100)
    private String nombreRol;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;
}
