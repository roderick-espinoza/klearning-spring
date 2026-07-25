package com.cibertec.klearning.service.interfaces;

import com.cibertec.klearning.dto.persona.PersonaRequestDto;
import com.cibertec.klearning.dto.persona.PersonaResponseDto;

import java.util.List;

public interface PersonaService {
    List<PersonaResponseDto> listar();
    List<PersonaResponseDto> listarActivos();
    PersonaResponseDto obtenerPorId(String id);
    PersonaResponseDto obtenerPorDni(String dni);
    PersonaResponseDto crear(PersonaRequestDto request, String usuarioActual);
    PersonaResponseDto actualizar(String id, PersonaRequestDto request, String usuarioActual);
    void eliminar(String id, String usuarioActual);
}
