package com.cibertec.klearning.service;

import com.cibertec.klearning.dto.LoginRequestDTO;
import com.cibertec.klearning.dto.UsuarioRequestDTO;
import com.cibertec.klearning.dto.UsuarioResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO registrar(UsuarioRequestDTO dto);

    int registrarLote(List<UsuarioRequestDTO> lista);

    UsuarioResponseDTO login(LoginRequestDTO dto);

    List<UsuarioResponseDTO> listar();

    UsuarioResponseDTO obtener(String idUsuario);

    Page<UsuarioResponseDTO> listarPorRol(String idRol, int page, int size);

    void eliminarLogico(String idUsuario, String deletedUser);
}
