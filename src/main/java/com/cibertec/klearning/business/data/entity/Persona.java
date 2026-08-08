package com.cibertec.klearning.business.data.entity;

import com.cibertec.klearning.business.data.entity.base.AuditEntity;
import com.cibertec.klearning.business.data.entity.enums.EstadoCivil;
import com.cibertec.klearning.business.data.entity.enums.FormacionAcademica;
import com.cibertec.klearning.business.data.entity.enums.ModalidadTrabajo;
import com.cibertec.klearning.business.data.entity.enums.Nacionalidad;
import com.cibertec.klearning.business.data.entity.enums.Sexo;
import com.cibertec.klearning.business.data.entity.enums.SkillPrincipal;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "personas")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Persona extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idPersona", length = 36, updatable = false)
    private String idPersona;

    @Column(name = "apellidos", nullable = false, length = 200)
    private String apellidos;

    @Column(name = "nombres", nullable = false, length = 200)
    private String nombres;

    @Column(name = "dniCe", nullable = false, length = 12)
    private String dniCe;

    @Column(name = "sexo", nullable = false, length = 1)
    private Sexo sexo;

    @Column(name = "fechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "estadoCivil", nullable = false, length = 1)
    private EstadoCivil estadoCivil;

    @Column(name = "nacionalidad", nullable = false, length = 2)
    private Nacionalidad nacionalidad;

    @Column(name = "celular", nullable = false, length = 9)
    private String celular;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "formacionAcademica", nullable = false, length = 20)
    private FormacionAcademica formacionAcademica;

    @Column(name = "fechaIngreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fechaCese")
    private LocalDate fechaCese;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidadTrabajo", nullable = false, length = 20)
    private ModalidadTrabajo modalidadTrabajo;

    @Enumerated(EnumType.STRING)
    @Column(name = "skillPrincipal", nullable = false, length = 30)
    private SkillPrincipal skillPrincipal;
}
