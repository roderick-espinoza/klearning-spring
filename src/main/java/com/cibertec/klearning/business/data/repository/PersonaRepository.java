package com.cibertec.klearning.business.data.repository;

import com.cibertec.klearning.business.data.entity.Persona;
import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, String> {
    Optional<Persona> findByDniCe(String dniCe);
    List<Persona> findByEstado(EstadoRegistro estado);
    boolean existsByDniCe(String dniCe);
}
