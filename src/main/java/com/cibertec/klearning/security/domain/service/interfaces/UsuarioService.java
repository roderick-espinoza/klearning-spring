package com.cibertec.klearning.security.domain.service.interfaces;

import com.cibertec.klearning.security.api.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.security.api.dto.usuario.UsuarioResponseDto;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDto> listar();
    UsuarioResponseDto obtenerPorId(String id);
    UsuarioResponseDto obtenerPorUsuario(String usuario);
    UsuarioResponseDto crear(UsuarioRequestDto request);
    UsuarioResponseDto actualizar(String id, UsuarioRequestDto request);
    void eliminar(String id);
    List<UsuarioResponseDto> guardarEnBatch(List<UsuarioRequestDto> requests, int batchSize);
}