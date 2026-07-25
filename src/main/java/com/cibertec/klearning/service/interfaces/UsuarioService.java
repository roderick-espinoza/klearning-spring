package com.cibertec.klearning.service.interfaces;

import com.cibertec.klearning.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.dto.usuario.UsuarioResponseDto;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDto> listar();
    UsuarioResponseDto obtenerPorId(String id);
    UsuarioResponseDto obtenerPorUsuario(String usuario);
    UsuarioResponseDto crear(UsuarioRequestDto request, String usuarioActual);
    UsuarioResponseDto actualizar(String id, UsuarioRequestDto request, String usuarioActual);
    void eliminar(String id, String usuarioActual);
    List<UsuarioResponseDto> guardarEnBatch(List<UsuarioRequestDto> requests, String usuarioActual, int batchSize);
}