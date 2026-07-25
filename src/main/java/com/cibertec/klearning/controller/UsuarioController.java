package com.cibertec.klearning.controller;

import com.cibertec.klearning.dto.usuario.UsuarioRequestDto;
import com.cibertec.klearning.dto.usuario.UsuarioResponseDto;
import com.cibertec.klearning.service.interfaces.UsuarioService;
import com.cibertec.klearning.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SessionUtil sessionUtil;

    public UsuarioController(UsuarioService usuarioService, SessionUtil sessionUtil) {
        this.usuarioService = usuarioService;
        this.sessionUtil = sessionUtil;
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
            @Valid @RequestBody UsuarioRequestDto request,
            HttpSession session) {

        UsuarioResponseDto usuarioCreado =
                usuarioService.crear(request, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody UsuarioRequestDto request,
            HttpSession session) {

        return ResponseEntity.ok(
                usuarioService.actualizar(id, request, sessionUtil.obtenerUsuarioActual(session))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id,
            HttpSession session) {

        usuarioService.eliminar(id, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UsuarioResponseDto>> crearEnBatch(
            @Valid @RequestBody List<@Valid UsuarioRequestDto> requests,
            @RequestParam(defaultValue = "20") int batchSize,
            HttpSession session) {

        List<UsuarioResponseDto> creados =
                usuarioService.guardarEnBatch(requests, sessionUtil.obtenerUsuarioActual(session), batchSize);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creados);
    }
}