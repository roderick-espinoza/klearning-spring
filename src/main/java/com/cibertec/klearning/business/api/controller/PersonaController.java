package com.cibertec.klearning.business.api.controller;

import com.cibertec.klearning.business.api.dto.persona.PersonaRequestDto;
import com.cibertec.klearning.business.api.dto.persona.PersonaResponseDto;
import com.cibertec.klearning.business.domain.service.interfaces.PersonaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<PersonaResponseDto>> obtenerTodos() {
        return ResponseEntity.ok(
                personaService.listar()
        );
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PersonaResponseDto>> obtenerActivos() {
        return ResponseEntity.ok(
                personaService.listarActivos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponseDto> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                personaService.obtenerPorId(id)
        );
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<PersonaResponseDto> buscarPorDni(
            @PathVariable String dni) {

        return ResponseEntity.ok(
                personaService.obtenerPorDni(dni)
        );
    }

    @PostMapping
    public ResponseEntity<PersonaResponseDto> crear(
            @Valid @RequestBody PersonaRequestDto request) {

        PersonaResponseDto personaCreada =
                personaService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody PersonaRequestDto request) {

        return ResponseEntity.ok(
                personaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        personaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}