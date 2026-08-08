package com.cibertec.klearning.business.domain.service.interfaces;

import com.cibertec.klearning.business.api.dto.produccion.ProduccionRequestDto;
import com.cibertec.klearning.business.api.dto.produccion.ProduccionResponseDto;
import com.cibertec.klearning.business.data.entity.Proyecto;
import com.cibertec.klearning.business.data.repository.ProduccionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProduccionService {


    List<ProduccionResponseDto> listar();
    ProduccionResponseDto obtenerPorId(String id);
    ProduccionResponseDto crear(ProduccionRequestDto request);
    ProduccionResponseDto actualizar(String id, ProduccionRequestDto request);
    void eliminar(String id);


    Page<ProduccionResponseDto> listarPorPersonaDni(String dni, Pageable pageable);
    Page<ProduccionResponseDto> listarTareasConExcesoDeTiempo(Pageable pageable);
    Page<ProduccionResponseDto> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin, Pageable pageable);
    Page<ProduccionResponseDto> buscarPaginada(String idProyecto, Boolean huboCorte, Pageable pageable);


    List<Proyecto> listarProyectosDistintosPorPersonaDni(String dni);
    List<ProduccionRepository.TiempoLeccionProjection> calcularTiempoPorLeccionOva(String dni);

    void cerrarProduccionPorProyecto(String idProyecto);
}