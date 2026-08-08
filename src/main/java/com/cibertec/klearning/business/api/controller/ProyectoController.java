package com.cibertec.klearning.business.api.controller;

import com.cibertec.klearning.business.api.dto.proyecto.ProyectoRequestDto;
import com.cibertec.klearning.business.api.dto.proyecto.ProyectoResponseDto;
import com.cibertec.klearning.business.domain.service.interfaces.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public ResponseEntity<List<ProyectoResponseDto>> obtenerTodos() {
        return ResponseEntity.ok(
                proyectoService.listar()
        );
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ProyectoResponseDto>> obtenerActivos() {
        return ResponseEntity.ok(
                proyectoService.listarActivos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponseDto> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                proyectoService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProyectoResponseDto> crear(
            @Valid @RequestBody ProyectoRequestDto request) {

        ProyectoResponseDto proyectoCreado =
                proyectoService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(proyectoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ProyectoRequestDto request) {

        return ResponseEntity.ok(
                proyectoService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        proyectoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vertical/{tipoVertical}")
    public ResponseEntity<List<ProyectoResponseDto>> buscarPorVertical(
            @PathVariable String tipoVertical) {

        return ResponseEntity.ok(
                proyectoService.buscarPorTipoVertical(tipoVertical)
        );
    }
}