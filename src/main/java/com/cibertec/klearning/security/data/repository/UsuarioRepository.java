package com.cibertec.klearning.security.data.repository;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.security.data.entity.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByUsuario(String usuario);

    @EntityGraph(attributePaths = {"persona", "roles"})
    Optional<Usuario> findByUsuarioIgnoreCase(String usuario);

    boolean existsByUsuarioIgnoreCase(String usuario);

    List<Usuario> findByEstado(EstadoRegistro estado);

    @Override
    @EntityGraph(attributePaths = {"persona", "roles"})
    Optional<Usuario> findById(String id);

    @Override
    @EntityGraph(attributePaths = {"persona", "roles"})
    List<Usuario> findAll();
}
