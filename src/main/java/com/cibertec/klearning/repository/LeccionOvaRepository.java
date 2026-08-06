package com.cibertec.klearning.repository;

import com.cibertec.klearning.entity.LeccionOva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeccionOvaRepository extends JpaRepository<LeccionOva, String> {



}