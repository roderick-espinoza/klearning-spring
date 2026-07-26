package com.cibertec.klearning.controller;

import com.cibertec.klearning.dto.proyecto.ProyectoRequestDto;
import com.cibertec.klearning.dto.proyecto.ProyectoResponseDto;
import com.cibertec.klearning.service.interfaces.ProyectoService;
import com.cibertec.klearning.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final SessionUtil sessionUtil;

    public ProyectoController(ProyectoService proyectoService, SessionUtil sessionUtil) {
        this.proyectoService = proyectoService;
        this.sessionUtil = sessionUtil;
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
            @Valid @RequestBody ProyectoRequestDto request,
            HttpSession session) {

        ProyectoResponseDto proyectoCreado =
                proyectoService.crear(request, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(proyectoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ProyectoRequestDto request,
            HttpSession session) {

        return ResponseEntity.ok(
                proyectoService.actualizar(id, request, sessionUtil.obtenerUsuarioActual(session))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id,
            HttpSession session) {

        proyectoService.eliminar(id, sessionUtil.obtenerUsuarioActual(session));

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