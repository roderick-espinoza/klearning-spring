package com.cibertec.klearning.repository;

import com.cibertec.klearning.entity.Ovas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OvasRepository extends JpaRepository<Ovas,String> {

    List<Ovas> findBynombre(String nombre);

}