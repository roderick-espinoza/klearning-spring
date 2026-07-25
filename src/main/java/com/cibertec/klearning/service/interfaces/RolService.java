package com.cibertec.klearning.service.interfaces;

import com.cibertec.klearning.dto.rol.RolRequestDto;
import com.cibertec.klearning.dto.rol.RolResponseDto;

import java.util.List;

public interface RolService {
    List<RolResponseDto> listar();
    RolResponseDto obtenerPorId(String id);
    RolResponseDto crear(RolRequestDto request, String usuarioActual);
    RolResponseDto actualizar(String id, RolRequestDto request, String usuarioActual);
    void eliminar(String id, String usuarioActual);
}
