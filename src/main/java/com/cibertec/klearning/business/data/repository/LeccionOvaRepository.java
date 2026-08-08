package com.cibertec.klearning.business.data.repository;

import com.cibertec.klearning.business.data.entity.LeccionOva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeccionOvaRepository extends JpaRepository<LeccionOva, String> {



}