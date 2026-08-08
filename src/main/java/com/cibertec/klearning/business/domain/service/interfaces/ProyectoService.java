package com.cibertec.klearning.business.domain.service.interfaces;

import com.cibertec.klearning.business.api.dto.proyecto.ProyectoRequestDto;
import com.cibertec.klearning.business.api.dto.proyecto.ProyectoResponseDto;

import java.util.List;

public interface ProyectoService {
    List<ProyectoResponseDto> listar();
    List<ProyectoResponseDto> listarActivos();
    ProyectoResponseDto obtenerPorId(String id);
    List<ProyectoResponseDto> buscarPorTipoVertical(String tipoVertical);
    ProyectoResponseDto crear(ProyectoRequestDto request);
    ProyectoResponseDto actualizar(String id, ProyectoRequestDto request);
    void eliminar(String id);
}