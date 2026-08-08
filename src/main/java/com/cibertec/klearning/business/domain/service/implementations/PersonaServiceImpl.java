package com.cibertec.klearning.business.domain.service.implementations;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;

import com.cibertec.klearning.business.api.dto.persona.PersonaRequestDto;
import com.cibertec.klearning.business.api.dto.persona.PersonaResponseDto;
import com.cibertec.klearning.business.data.entity.Persona;
import com.cibertec.klearning.business.api.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.business.domain.mapper.PersonaMapper;
import com.cibertec.klearning.business.data.repository.PersonaRepository;
import com.cibertec.klearning.business.domain.service.base.BaseServiceImpl;
import com.cibertec.klearning.business.domain.service.interfaces.PersonaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServiceImpl
        extends BaseServiceImpl<Persona, String, PersonaRequestDto, PersonaResponseDto, PersonaRepository>
        implements PersonaService {

    private final PersonaMapper personaMapper;

    public PersonaServiceImpl(PersonaRepository repository, PersonaMapper personaMapper) {
        super(repository, personaMapper);
        this.personaMapper = personaMapper;
    }

    @Override
    protected String nombreEntidad() { return "Persona"; }

    @Override
    public List<PersonaResponseDto> listar() { return super.listar(); }

    @Override
    public PersonaResponseDto obtenerPorId(String id) { return super.obtenerPorId(id); }

    @Override
    public PersonaResponseDto crear(PersonaRequestDto request) {
        return super.crear(request);
    }

    @Override
    public PersonaResponseDto actualizar(String id, PersonaRequestDto request) {
        return super.actualizar(id, request);
    }

    @Override
    public void eliminar(String id) { super.eliminar(id); }

    @Override
    public List<PersonaResponseDto> listarActivos() {
        return repository.findByEstado(EstadoRegistro.ACTIVO)
                .stream()
                .map(personaMapper::toResponseDto)
                .toList();
    }

    @Override
    public PersonaResponseDto obtenerPorDni(String dni) {
        Persona persona = repository.findByDniCe(dni)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Persona no encontrada con DNI/CE: " + dni));
        return personaMapper.toResponseDto(persona);
    }
}
