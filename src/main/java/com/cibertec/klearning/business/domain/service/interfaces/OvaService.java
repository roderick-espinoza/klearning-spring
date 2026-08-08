package com.cibertec.klearning.business.domain.service.interfaces;

import com.cibertec.klearning.business.api.dto.ova.OvaRequestDto;
import com.cibertec.klearning.business.api.dto.ova.OvaResponseDto;

import java.util.List;

public interface OvaService {
    List<OvaResponseDto> listar();
    OvaResponseDto obtenerPorId(String id);
    OvaResponseDto crear(OvaRequestDto request);
    OvaResponseDto actualizar(String id, OvaRequestDto  request);
    void eliminar(String id);
}
