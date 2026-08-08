package com.cibertec.klearning.security.data.repository;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.security.data.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, String> {

    Optional<Rol> findByCodigoRolIgnoreCase(String codigoRol);

    boolean existsByCodigoRolIgnoreCase(String codigoRol);

    List<Rol> findByEstado(EstadoRegistro estado);
}
