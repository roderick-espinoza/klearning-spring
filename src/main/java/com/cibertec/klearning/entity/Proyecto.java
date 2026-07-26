package com.cibertec.klearning.entity;

import com.cibertec.klearning.entity.base.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "proyectos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idProyecto", length = 36, updatable = false)
    private String idProyecto;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "tipoVertical", nullable = false, length = 100)
    private String tipoVertical;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "kpis", nullable = false, length = 100)
    private String kpis;

    @Column(name = "objetivo", nullable = false, length = 300)
    private String objetivo;

    @Column(name = "necesidades", nullable = false, length = 300)
    private String necesidades;

    @Column(name = "cuenta", nullable = false, length = 100)
    private String cuenta;

    @Column(name = "segmento", nullable = false, length = 100)
    private String segmento;

    @Column(name = "areaSolicitante", nullable = false, length = 200)
    private String areaSolicitante;

    @Column(name = "productOwner", nullable = false, length = 100)
    private String productOwner;

    @Column(name = "sponsor", nullable = false, length = 100)
    private String sponsor;

    @Column(name = "fechaSolicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "antecedentes", nullable = false, length = 400)
    private String antecedentes;

    @Column(name = "descripcion", length = 300)
    private String descripcion;
}