package com.cibertec.klearning.service.interfaces;

import com.cibertec.klearning.dto.produccion.ProduccionRequestDto;
import com.cibertec.klearning.dto.produccion.ProduccionResponseDto;
import com.cibertec.klearning.entity.Proyecto;
import com.cibertec.klearning.repository.ProduccionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProduccionService {


    List<ProduccionResponseDto> listar();
    ProduccionResponseDto obtenerPorId(String id);
    ProduccionResponseDto crear(ProduccionRequestDto request, String usuarioActual);
    ProduccionResponseDto actualizar(String id, ProduccionRequestDto request, String usuarioActual);
    void eliminar(String id, String usuarioActual);


    Page<ProduccionResponseDto> listarPorPersonaDni(String dni, Pageable pageable);
    Page<ProduccionResponseDto> listarTareasConExcesoDeTiempo(Pageable pageable);
    Page<ProduccionResponseDto> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin, Pageable pageable);
    Page<ProduccionResponseDto> buscarPaginada(String idProyecto, Boolean huboCorte, Pageable pageable);


    List<Proyecto> listarProyectosDistintosPorPersonaDni(String dni);
    List<ProduccionRepository.TiempoLeccionProjection> calcularTiempoPorLeccionOva(String dni);

    void cerrarProduccionPorProyecto(String idProyecto);
}