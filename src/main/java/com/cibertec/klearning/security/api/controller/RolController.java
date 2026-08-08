package com.cibertec.klearning.security.api.controller;

import com.cibertec.klearning.security.api.dto.rol.RolRequestDto;
import com.cibertec.klearning.security.api.dto.rol.RolResponseDto;
import com.cibertec.klearning.security.domain.service.interfaces.RolService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
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
            @Valid @RequestBody RolRequestDto request) {

        RolResponseDto rolCreado =
                rolService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rolCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody RolRequestDto request) {

        return ResponseEntity.ok(
                rolService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        rolService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
