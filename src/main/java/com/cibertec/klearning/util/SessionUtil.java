package com.cibertec.klearning.util;

import com.cibertec.klearning.entity.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    public String obtenerUsuarioActual(HttpSession session) {
        if(session == null){
            return "sistema";
        }

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        return usuario != null
                ? usuario.getUsuario()
                : "sistema";
    }
}
