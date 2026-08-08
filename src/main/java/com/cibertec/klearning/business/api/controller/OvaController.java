package com.cibertec.klearning.business.api.controller;

import com.cibertec.klearning.business.api.dto.ova.OvaRequestDto;
import com.cibertec.klearning.business.api.dto.ova.OvaResponseDto;
import com.cibertec.klearning.business.domain.service.interfaces.OvaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ovas")
public class OvaController {

    private final OvaService ovaService;

    public OvaController(OvaService ovaService) {
        this.ovaService = ovaService;
    }

    @GetMapping
    public ResponseEntity<List<OvaResponseDto>> obtenerTodos() {
        return ResponseEntity.ok(
                ovaService.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OvaResponseDto> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                ovaService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<OvaResponseDto> crear(
            @Valid @RequestBody OvaRequestDto request) {

        OvaResponseDto ovaCreada =
                ovaService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ovaCreada);

    }

    @PutMapping("/{id}")
    public ResponseEntity<OvaResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody OvaRequestDto request) {

        return ResponseEntity.ok(
                ovaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {
        ovaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
