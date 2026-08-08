package com.cibertec.klearning.security.api.controller;

import com.cibertec.klearning.security.api.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.security.api.dto.usuario.UsuarioResponseDto;
import com.cibertec.klearning.security.domain.service.interfaces.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> obtenerTodos() {
        return ResponseEntity.ok(
                usuarioService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorId(id)
        );
    }

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioResponseDto> buscarPorUsuario(
            @RequestParam String usuario) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorUsuario(usuario)
        );
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crear(
            @Valid @RequestBody UsuarioRequestDto request) {

        UsuarioResponseDto usuarioCreado =
                usuarioService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody UsuarioRequestDto request) {

        return ResponseEntity.ok(
                usuarioService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UsuarioResponseDto>> crearEnBatch(
            @Valid @RequestBody List<@Valid UsuarioRequestDto> requests,
            @RequestParam(defaultValue = "20") int batchSize) {

        List<UsuarioResponseDto> creados =
                usuarioService.guardarEnBatch(requests, batchSize);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creados);
    }
}