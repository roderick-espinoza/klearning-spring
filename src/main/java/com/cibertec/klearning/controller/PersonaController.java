package com.cibertec.klearning.controller;

import com.cibertec.klearning.dto.persona.PersonaRequestDto;
import com.cibertec.klearning.dto.persona.PersonaResponseDto;
import com.cibertec.klearning.service.interfaces.PersonaService;
import com.cibertec.klearning.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personas")
public class PersonaController {

    private final PersonaService personaService;
    private final SessionUtil sessionUtil;

    public PersonaController(PersonaService personaService, SessionUtil sessionUtil) {
        this.personaService = personaService;
        this.sessionUtil = sessionUtil;
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
            @Valid @RequestBody PersonaRequestDto request,
            HttpSession session) {

        PersonaResponseDto personaCreada =
                personaService.crear(request, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody PersonaRequestDto request,
            HttpSession session) {

        return ResponseEntity.ok(
                personaService.actualizar(id, request, sessionUtil.obtenerUsuarioActual(session))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id,
            HttpSession session) {

        personaService.eliminar(id, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity.noContent().build();
    }
}