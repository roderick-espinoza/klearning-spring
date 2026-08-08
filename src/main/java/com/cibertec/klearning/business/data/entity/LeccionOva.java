package com.cibertec.klearning.business.data.entity;

import com.cibertec.klearning.business.data.entity.base.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leccion_ova")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeccionOva extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idLeccionOva", length = 36, updatable = false)
    private String idLeccionOva;

    /*
     * Nota para el equipo:
     * Los demás campos (nombre, descripción, etc.) se pueden agregar
     * más adelante cuando se desarrolle el módulo de Lecciones.
     * Por ahora, el ID es suficiente para resolver la dependencia de Produccion.
     */
}