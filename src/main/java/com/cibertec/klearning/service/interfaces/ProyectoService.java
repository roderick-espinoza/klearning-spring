package com.cibertec.klearning.service.interfaces;

import com.cibertec.klearning.dto.proyecto.ProyectoRequestDto;
import com.cibertec.klearning.dto.proyecto.ProyectoResponseDto;

import java.util.List;

public interface ProyectoService {
    List<ProyectoResponseDto> listar();
    List<ProyectoResponseDto> listarActivos();
    ProyectoResponseDto obtenerPorId(String id);
    List<ProyectoResponseDto> buscarPorTipoVertical(String tipoVertical);
    ProyectoResponseDto crear(ProyectoRequestDto request, String usuarioActual);
    ProyectoResponseDto actualizar(String id, ProyectoRequestDto request, String usuarioActual);
    void eliminar(String id, String usuarioActual);
}