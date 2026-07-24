package com.cibertec.klearning.entity;

import com.cibertec.klearning.entity.base.AuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString(exclude = "usuarios")
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

    // RELACIÓN @OneToMany: Un rol tiene muchos usuarios
    // FetchType.LAZY: Los usuarios se cargan solo cuando se acceden explícitamente
    // mappedBy = "rol": Indica que el lado propietario de la relación está en Usuario.rol
    // cascade = CascadeType.ALL: Permite propagar operaciones (persist, merge, remove) desde Rol hacia Usuario
    // orphanRemoval = true: Elimina automáticamente los usuarios huérfanos cuando se eliminan de la colección
    // @JsonIgnore: Evita LazyInitializationException al serializar JSON
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();
}
