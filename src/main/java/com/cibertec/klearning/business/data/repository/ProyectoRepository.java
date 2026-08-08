package com.cibertec.klearning.business.data.repository;

import com.cibertec.klearning.business.data.entity.Proyecto;
import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, String> {

    //Obtener todos los proyectos según su estado (ej. "1" para activos, "0" para inactivos)
    List<Proyecto> findByEstado(EstadoRegistro estado);

    // Buscar si existe un proyecto por nombre exacto
    boolean existsByNombre(String nombre);

    // Obtener una lista de proyectos filtrados por el Product Owner
    List<Proyecto> findByProductOwner(String productOwner);

    // Obtener una lista de proyectos por su tipo de Vertical
    List<Proyecto> findByTipoVertical(String tipoVertical);
}