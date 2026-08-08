package com.cibertec.klearning.security.domain.service.implementations;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.security.data.entity.Usuario;
import com.cibertec.klearning.security.data.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String usernameNormalizado = username.trim().toLowerCase();

        Usuario usuario = usuarioRepository
                .findByUsuarioIgnoreCase(usernameNormalizado)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario o contraseña incorrectos"));

        // La authority se construye con el codigo tecnico del rol, no con el
        // nombre visible, porque hasRole() no admite espacios ni tildes.
        String[] autoridades = usuario.getRoles()
                .stream()
                .map(rol -> "ROLE_" + rol.getCodigoRol())
                .toArray(String[]::new);

        return User.builder()
                .username(usuario.getUsuario())
                .password(usuario.getPassword())
                .authorities(autoridades)
                .disabled(usuario.getEstado() != EstadoRegistro.ACTIVO)
                .build();
    }
}
