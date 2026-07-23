package com.cibertec.klearning.entity;

import com.cibertec.klearning.entity.base.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "idPersona", length = 8)
    private String idPersona;

    @Column(name = "apellidos", nullable = false, length = 200)
    private String apellidos;

    @Column(name = "nombres", nullable = false, length = 200)
    private String nombres;

    @Column(name = "dniCe", nullable = false, length = 12)
    private String dniCe;

    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;

    @Column(name = "fechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "estadoCivil", nullable = false, length = 1)
    private String estadoCivil;

    @Column(name = "nacionalidad", nullable = false, length = 50)
    private String nacionalidad;

    @Column(name = "celular", nullable = false, length = 9)
    private String celular;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "formacionAcademica", nullable = false, length = 200)
    private String formacionAcademica;

    @Column(name = "fechaIngreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "fechaCese")
    private LocalDate fechaCese;

    @Column(name = "modalidadTrabajo", nullable = false, length = 100)
    private String modalidadTrabajo;

    @Column(name = "skillPrincipal", nullable = false, length = 100)
    private String skillPrincipal;
}
