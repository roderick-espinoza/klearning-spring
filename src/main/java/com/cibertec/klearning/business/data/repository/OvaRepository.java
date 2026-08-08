package com.cibertec.klearning.business.data.repository;

import com.cibertec.klearning.business.data.entity.Ova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OvaRepository extends JpaRepository<Ova,String> {

    List<Ova> findBynombre(String nombre);

}
