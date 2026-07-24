package com.cibertec.klearning.repository;

import com.cibertec.klearning.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, String> {

    // Query con FETCH JOIN para cargar usuarios cuando se necesiten
    // Esto evita el problema N+1 queries cuando se necesita acceder a los usuarios
    @Query("SELECT r FROM Rol r LEFT JOIN FETCH r.usuarios WHERE r.idRol = :idRol AND r.deletedDate IS NULL")
    Optional<Rol> obtenerConUsuarios(@Param("idRol") String idRol);

    // Query optimizada para listar roles activos sin cargar usuarios (LAZY)
    @Query("SELECT r FROM Rol r WHERE r.deletedDate IS NULL ORDER BY r.nombreRol")
    List<Rol> listarActivos();

    // Query para buscar roles por nombre con filtro
    @Query("SELECT r FROM Rol r WHERE r.deletedDate IS NULL AND LOWER(r.nombreRol) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Rol> buscarPorNombre(@Param("filtro") String filtro);

    // Verificar si existe un rol por nombre (case insensitive)
    boolean existsByNombreRolIgnoreCase(String nombreRol);
}
