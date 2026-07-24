package com.cibertec.klearning.controller;

import com.cibertec.klearning.entity.Rol;
import com.cibertec.klearning.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> listarTodos() {
        List<Rol> roles = rolService.listarActivos();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerPorId(@PathVariable String id) {
        Rol rol = rolService.obtenerConUsuarios(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rol);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Rol>> buscar(@RequestParam String filtro) {
        List<Rol> roles = rolService.listarActivos().stream()
                .filter(r -> r.getNombreRol().toLowerCase().contains(filtro.toLowerCase()))
                .toList();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody Rol rol) {
        Rol rolCreado = rolService.guardar(rol);
        return ResponseEntity.ok(rolCreado);
    }
}
