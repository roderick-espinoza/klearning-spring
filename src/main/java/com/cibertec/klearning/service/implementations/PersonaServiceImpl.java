package com.cibertec.klearning.service.implementations;

import com.cibertec.klearning.dto.persona.PersonaRequestDto;
import com.cibertec.klearning.dto.persona.PersonaResponseDto;
import com.cibertec.klearning.entity.Persona;
import com.cibertec.klearning.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.mapper.PersonaMapper;
import com.cibertec.klearning.repository.PersonaRepository;
import com.cibertec.klearning.service.base.BaseServiceImpl;
import com.cibertec.klearning.service.interfaces.PersonaService;
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
    public PersonaResponseDto crear(PersonaRequestDto request, String usuarioActual) {
        return super.crear(request, usuarioActual);
    }

    @Override
    public PersonaResponseDto actualizar(String id, PersonaRequestDto request, String usuarioActual) {
        return super.actualizar(id, request, usuarioActual);
    }

    @Override
    public void eliminar(String id, String usuarioActual) { super.eliminar(id, usuarioActual); }

    @Override
    public List<PersonaResponseDto> listarActivos() {
        return repository.findByEstado("1")
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
