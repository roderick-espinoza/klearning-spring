package com.cibertec.klearning.entity;

import com.cibertec.klearning.entity.base.AuditEntity;
import com.cibertec.klearning.entity.base.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ovas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ovas extends AuditEntity {

    @Id
    @Column(name = "idOva", length = 8)
    private String idOva;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "fechaInicioVigencia", nullable = false)
    private LocalDateTime fechaInicioVigencia;

    @Column(name = "fechaFinVigencia")
    private LocalDateTime fechaFinVigencia;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @Column(name = "estado", nullable = false, length = 1)
    private String estado;

    @Column(name = "createUser", nullable = false, length = 50)
    private String createUser;

    @Column(name = "createDate", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "updatedUser", length = 50)
    private String updatedUser;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "deletedDate")
    private LocalDateTime deletedDate;

    @Column(name = "deletedUser", length = 50)
    private String deletedUser;
}