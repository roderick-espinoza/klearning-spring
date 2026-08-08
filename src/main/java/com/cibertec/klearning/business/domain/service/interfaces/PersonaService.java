package com.cibertec.klearning.business.domain.service.interfaces;

import com.cibertec.klearning.business.api.dto.persona.PersonaRequestDto;
import com.cibertec.klearning.business.api.dto.persona.PersonaResponseDto;

import java.util.List;

public interface PersonaService {
    List<PersonaResponseDto> listar();
    List<PersonaResponseDto> listarActivos();
    PersonaResponseDto obtenerPorId(String id);
    PersonaResponseDto obtenerPorDni(String dni);
    PersonaResponseDto crear(PersonaRequestDto request);
    PersonaResponseDto actualizar(String id, PersonaRequestDto request);
    void eliminar(String id);
}
