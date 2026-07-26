package com.cibertec.klearning.service.interfaces;

import com.cibertec.klearning.dto.ova.OvaRequestDto;
import com.cibertec.klearning.dto.ova.OvaResponseDto;

import java.util.List;

public interface OvaService {
    List<OvaResponseDto> listar();
    OvaResponseDto obtenerPorId(String id);
    OvaResponseDto crear(OvaRequestDto request, String usuarioActual);
    OvaResponseDto actualizar(String id, OvaRequestDto  request, String usuarioActual);
    void eliminar(String id, String usuarioActual);
}
