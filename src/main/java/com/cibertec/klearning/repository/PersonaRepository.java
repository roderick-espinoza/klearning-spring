package com.cibertec.klearning.repository;

import com.cibertec.klearning.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, String> {
    Optional<Persona> findByDniCe(String dniCe);
    List<Persona> findByEstado(String estado);
    boolean existsByDniCe(String dniCe);
}
