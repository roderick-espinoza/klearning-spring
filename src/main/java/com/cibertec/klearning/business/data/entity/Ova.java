package com.cibertec.klearning.business.data.entity;

import com.cibertec.klearning.business.data.entity.base.AuditEntity;
import com.cibertec.klearning.business.data.entity.enums.TipoOva;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ovas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ova extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idOva", length = 36, updatable = false)
    private String idOva;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoOva tipo;

    @Column(name = "fechaInicioVigencia", nullable = false)
    private LocalDateTime fechaInicioVigencia;

    @Column(name = "fechaFinVigencia")
    private LocalDateTime fechaFinVigencia;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;
}