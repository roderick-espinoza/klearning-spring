package com.cibertec.klearning.repository;

import com.cibertec.klearning.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, String> {
}
