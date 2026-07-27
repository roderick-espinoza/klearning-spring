package com.cibertec.klearning.controller;

import com.cibertec.klearning.dto.rol.RolRequestDto;
import com.cibertec.klearning.dto.rol.RolResponseDto;
import com.cibertec.klearning.service.interfaces.RolService;
import com.cibertec.klearning.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    private final RolService rolService;
    private final SessionUtil sessionUtil;

    public RolController(RolService rolService, SessionUtil sessionUtil) {
        this.rolService = rolService;
        this.sessionUtil = sessionUtil;
    }

    @GetMapping
    public ResponseEntity<List<RolResponseDto>> obtenerTodos() {
        return ResponseEntity.ok(
                rolService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDto> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                rolService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<RolResponseDto> crear(
            @Valid @RequestBody RolRequestDto request,
            HttpSession session) {

        RolResponseDto rolCreado =
                rolService.crear(request, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rolCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody RolRequestDto request,
            HttpSession session) {

        return ResponseEntity.ok(
                rolService.actualizar(id, request, sessionUtil.obtenerUsuarioActual(session))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id,
            HttpSession session) {

        rolService.eliminar(id, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity.noContent().build();
    }
}
