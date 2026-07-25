package com.cibertec.klearning.repository;

import com.cibertec.klearning.entity.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByUsuario(String usuario);

    List<Usuario> findByEstado(String estado);

    @Override
    @EntityGraph(attributePaths = {"persona", "rol"})
    Optional<Usuario> findById(String id);

    @Override
    @EntityGraph(attributePaths = {"persona", "rol"})
    List<Usuario> findAll();
}
