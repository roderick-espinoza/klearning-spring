package com.cibertec.klearning.controller;

import com.cibertec.klearning.dto.produccion.ProduccionRequestDto;
import com.cibertec.klearning.dto.produccion.ProduccionResponseDto;
import com.cibertec.klearning.entity.Proyecto;
import com.cibertec.klearning.repository.ProduccionRepository;
import com.cibertec.klearning.service.interfaces.ProduccionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produccion")
public class ProduccionController {

    private final ProduccionService produccionService;

    public ProduccionController(ProduccionService produccionService) {
        this.produccionService = produccionService;
    }

    /* Sirve para: Listar todas las producciones activas sin paginar.
     */
    @GetMapping
    public ResponseEntity<List<ProduccionResponseDto>> obtenerTodas() {
        return ResponseEntity.ok(
                produccionService.listar()
        );
    }

    /*Sirve para: Buscar el detalle completo de una tarea específica por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduccionResponseDto> obtenerPorId(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(
                produccionService.obtenerPorId(id)
        );
    }

    /* Sirve para: Registrar una nueva tarea de producción.
     */
    @PostMapping
    public ResponseEntity<ProduccionResponseDto> crear(
            @Valid @RequestBody ProduccionRequestDto request,
            @RequestHeader(value = "Usuario-Actual", defaultValue = "SISTEMA") String usuarioActual
    ) {
        ProduccionResponseDto produccionRegistrada =
                produccionService.crear(request, usuarioActual);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produccionRegistrada);
    }

    /** Sirve para: Actualizar la información de una tarea existente.
      */
    @PutMapping("/{id}")
    public ResponseEntity<ProduccionResponseDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ProduccionRequestDto request,
            @RequestHeader(value = "Usuario-Actual", defaultValue = "SISTEMA") String usuarioActual
    ) {
        return ResponseEntity.ok(
                produccionService.actualizar(id, request, usuarioActual)
        );
    }

    /*Sirve para: Eliminar lógicamente un registro (cambiar estado a inactivo).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id,
            @RequestHeader(value = "Usuario-Actual", defaultValue = "SISTEMA") String usuarioActual
    ) {
        produccionService.eliminar(id, usuarioActual);
        return ResponseEntity.noContent().build();
    }

    /* Sirve para: Mostrar el historial de tareas de un trabajador de forma paginada.
     */
    @GetMapping("/persona/dni/{dni}")
    public ResponseEntity<Page<ProduccionResponseDto>> obtenerPorDni(
            @PathVariable String dni,
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "fechaHoraInicio"
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                produccionService.listarPorPersonaDni(
                        dni,
                        pageable
                )
        );
    }

    /*Sirve para: Auditar tareas ineficientes (Horas reales > Horas estimadas).
     */
    @GetMapping("/auditoria/exceso-tiempo")
    public ResponseEntity<Page<ProduccionResponseDto>> obtenerTareasConExcesoDeTiempo(
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "fechaHoraInicio"
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                produccionService.listarTareasConExcesoDeTiempo(pageable)
        );
    }

    /* Sirve para: Extraer las tareas realizadas en un mes o semana específica.
     */
    @GetMapping("/fechas")
    public ResponseEntity<Page<ProduccionResponseDto>> obtenerEntreFechas(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fechaInicio,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fechaFin,

            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "fechaHoraInicio"
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                produccionService.listarPorRangoFechas(
                        fechaInicio,
                        fechaFin,
                        pageable
                )
        );
    }

    /**
     * Sirve para: Búsqueda maestra dinámica. Permite combinar filtros opcionales.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<ProduccionResponseDto>> buscarPaginado(
            @RequestParam(required = false)
            String idProyecto,

            @RequestParam(required = false)
            Boolean huboCorte,

            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "fechaHoraInicio"
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                produccionService.buscarPaginada(
                        idProyecto,
                        huboCorte,
                        pageable
                )
        );
    }


    /* Sirve para: Obtener una lista única de los proyectos en los que ha trabajado una persona.
     */
    @GetMapping("/persona/dni/{dni}/proyectos")
    public ResponseEntity<List<Proyecto>> obtenerProyectosPorPersonaDni(
            @PathVariable String dni
    ) {
        return ResponseEntity.ok(
                produccionService.listarProyectosDistintosPorPersonaDni(dni)
        );
    }

    /* Sirve para: Devolver métricas agrupadas de tiempo invertido por lección.
     */
    @GetMapping("/persona/dni/{dni}/estadisticas-tiempo")
    public ResponseEntity<List<ProduccionRepository.TiempoLeccionProjection>> calcularTiempoPorLeccion(
            @PathVariable String dni
    ) {
        return ResponseEntity.ok(
                produccionService.calcularTiempoPorLeccionOva(dni)
        );
    }



    /* Sirve para: Forzar el cierre de todas las tareas asociadas a un proyecto.
     */
    @PostMapping("/proyecto/{idProyecto}/cerrar")
    public ResponseEntity<Void> cerrarProduccionPorProyecto(
            @PathVariable String idProyecto
    ) {
        produccionService.cerrarProduccionPorProyecto(idProyecto);
        return ResponseEntity.ok().build();
    }
}