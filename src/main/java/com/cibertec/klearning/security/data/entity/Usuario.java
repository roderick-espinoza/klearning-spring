package com.cibertec.klearning.security.data.entity;

import com.cibertec.klearning.business.data.entity.Persona;
import com.cibertec.klearning.business.data.entity.base.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString(exclude = {"persona", "roles"})
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idUsuario", length = 36, updatable = false)
    private String idUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPersona", nullable = false)
    private Persona persona;

    @Column(name = "usuario", nullable = false, unique = true, length = 100)
    private String usuario;

    @Column(name = "password", nullable = false, length = 300)
    private String password;

    /**
     * EAGER a proposito: con open-in-view en false, el UserDetailsService
     * necesita las authorities ya cargadas cuando se cierra la transaccion.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    private Set<Rol> roles = new LinkedHashSet<>();

    public void agregarRol(Rol rol) {
        roles.add(rol);
    }

    public void reemplazarRoles(Set<Rol> nuevosRoles) {
        roles.clear();
        roles.addAll(nuevosRoles);
    }
}
