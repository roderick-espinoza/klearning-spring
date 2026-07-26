package com.cibertec.klearning.controller;

import com.cibertec.klearning.dto.ova.OvaRequestDto;
import com.cibertec.klearning.dto.ova.OvaResponseDto;
import com.cibertec.klearning.service.interfaces.OvaService;
import com.cibertec.klearning.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ovas")
public class OvaController {

    private final OvaService ovaService;
    private final SessionUtil sessionUtil;

    public OvaController(OvaService ovaService, SessionUtil sessionUtil) {
        this.ovaService = ovaService;
        this.sessionUtil = sessionUtil;
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
            @Valid @RequestBody OvaRequestDto request,
            HttpSession session) {

        OvaResponseDto ovaCreada =
                ovaService.crear(request, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ovaCreada);

    }

    @PutMapping("/{id}")
    public ResponseEntity<OvaResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody OvaRequestDto request,
            HttpSession session) {

        return ResponseEntity.ok(
                ovaService.actualizar(id, request, sessionUtil.obtenerUsuarioActual(session))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id,
            HttpSession session) {
        ovaService.eliminar(id, sessionUtil.obtenerUsuarioActual(session));

        return ResponseEntity.noContent().build();
    }
}
