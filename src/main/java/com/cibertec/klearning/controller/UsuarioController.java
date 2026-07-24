package com.cibertec.klearning.controller;

import com.cibertec.klearning.dto.LoginRequestDTO;
import com.cibertec.klearning.dto.UsuarioRequestDTO;
import com.cibertec.klearning.dto.UsuarioResponseDTO;
import com.cibertec.klearning.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ---------------- POST: registrar un usuario ----------------
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.registrar(dto));
    }

    // ---------------- POST: registrar en LOTE (flush + batch) ----------------
    @PostMapping("/lote")
    public ResponseEntity<Map<String, Object>> registrarLote(
            @RequestBody List<@Valid UsuarioRequestDTO> lista) {

        int total = usuarioService.registrarLote(lista);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("insertados", total,
                             "mensaje", "Registro por lote completado"));
    }

    // ---------------- POST: login ----------------
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(usuarioService.login(dto));
    }

    // ---------------- GET: listar todos (JOIN FETCH) ----------------
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // ---------------- GET: obtener por id ----------------
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> obtener(@PathVariable String idUsuario) {
        return ResponseEntity.ok(usuarioService.obtener(idUsuario));
    }

    // ---------------- GET: paginado por rol (@EntityGraph) ----------------
    // Ej: /api/usuarios/rol/ROL00004?page=0&size=5
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<Page<UsuarioResponseDTO>> listarPorRol(
            @PathVariable String idRol,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(usuarioService.listarPorRol(idRol, page, size));
    }

    // ---------------- DELETE: borrado logico ----------------
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminar(@PathVariable String idUsuario,
                                         @RequestParam String deletedUser) {
        usuarioService.eliminarLogico(idUsuario, deletedUser);
        return ResponseEntity.noContent().build();
    }
}
