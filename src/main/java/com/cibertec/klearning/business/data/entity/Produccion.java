package com.cibertec.klearning.business.data.entity;

import com.cibertec.klearning.business.data.entity.base.AuditEntity;
import com.cibertec.klearning.business.data.entity.enums.EstadoTarea;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produccion")
@NamedStoredProcedureQuery(
        name = "Produccion.cerrarPorProyecto",
        procedureName = "sp_cerrar_produccion_proyecto",
        parameters = {
                @StoredProcedureParameter(
                        mode = ParameterMode.IN,
                        name = "p_id_proyecto",
                        type = String.class
                )
        }
)
@Getter
@Setter
@ToString(exclude = {"proyecto", "leccionOva", "persona"})
@NoArgsConstructor
@AllArgsConstructor
public class Produccion extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idProduccion", length = 36, updatable = false)
    private String idProduccion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idProyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idLeccionOva", nullable = false)
    private LeccionOva leccionOva;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPersona", nullable = false)
    private Persona persona;

    @Column(name = "fechaHoraInicio", nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fechaHoraFin")
    private LocalDateTime fechaHoraFin;

    @Column(name = "horasEstimadas", nullable = false, precision = 6, scale = 2)
    private BigDecimal horasEstimadas;

    @Column(name = "horasReales", precision = 6, scale = 2)
    private BigDecimal horasReales;

    @Column(name = "huboCorte", nullable = false)
    private Boolean huboCorte = false;

    @Column(name = "descripcionCorte", length = 300)
    private String descripcionCorte;

    @Column(name = "horasContratiempo", precision = 6, scale = 2)
    private BigDecimal horasContratiempo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estadoTarea", nullable = false, length = 20)
    private EstadoTarea estadoTarea;

    @Column(name = "cumplimiento")
    private Boolean cumplimiento;

    @Column(name = "observaciones", length = 300)
    private String observaciones;
}