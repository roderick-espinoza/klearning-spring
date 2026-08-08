package com.cibertec.klearning.security.domain.service.interfaces;

import com.cibertec.klearning.security.api.dto.rol.RolRequestDto;
import com.cibertec.klearning.security.api.dto.rol.RolResponseDto;

import java.util.List;

public interface RolService {
    List<RolResponseDto> listar();
    List<RolResponseDto> listarActivos();
    RolResponseDto obtenerPorId(String id);
    RolResponseDto crear(RolRequestDto request);
    RolResponseDto actualizar(String id, RolRequestDto request);
    void eliminar(String id);
}
