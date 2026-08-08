package com.cibertec.klearning.business.data.entity.base;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditEntity {

    @Column(name = "estado", length = 1)
    private EstadoRegistro estado;

    @Column(name = "createUser", nullable = false, length = 50)
    private String createUser;

    @Column(name = "createDate")
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
