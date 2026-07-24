package com.cibertec.klearning.repository;

import com.cibertec.klearning.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {


    @Query("""
           SELECT u FROM Usuario u
           JOIN FETCH u.persona p
           JOIN FETCH u.rol r
           WHERE u.deletedDate IS NULL
           ORDER BY u.idUsuario
           """)
    List<Usuario> listarActivos();


    @Query("""
           SELECT u FROM Usuario u
           JOIN FETCH u.persona
           JOIN FETCH u.rol
           WHERE u.idUsuario = :idUsuario AND u.deletedDate IS NULL
           """)
    Optional<Usuario> obtenerConDetalle(@Param("idUsuario") String idUsuario);


    @Query("""
           SELECT u FROM Usuario u
           JOIN FETCH u.persona
           JOIN FETCH u.rol
           WHERE TRIM(u.usuario) = TRIM(:usuario)
             AND TRIM(u.password) = TRIM(:password)
             AND u.estado = '1'
             AND u.deletedDate IS NULL
           """)
    Optional<Usuario> validarCredenciales(@Param("usuario") String usuario,
                                          @Param("password") String password);


    @Query("SELECT MAX(u.idUsuario) FROM Usuario u")
    String obtenerUltimoCodigo();


    boolean existsByUsuarioIgnoreCase(String usuario);


    @EntityGraph(attributePaths = {"persona", "rol"})
    Page<Usuario> findByRol_IdRolAndDeletedDateIsNull(String idRol, Pageable pageable);


    @Query("""
           SELECT u FROM Usuario u
           JOIN FETCH u.persona p
           JOIN FETCH u.rol
           WHERE u.deletedDate IS NULL
             AND (LOWER(u.usuario)   LIKE LOWER(CONCAT('%', :filtro, '%'))
               OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :filtro, '%')))
           """)
    List<Usuario> buscar(@Param("filtro") String filtro);
}
