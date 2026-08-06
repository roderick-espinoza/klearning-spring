package com.cibertec.klearning.service.implementations;

import com.cibertec.klearning.dto.produccion.ProduccionRequestDto;
import com.cibertec.klearning.dto.produccion.ProduccionResponseDto;
import com.cibertec.klearning.entity.LeccionOva;
import com.cibertec.klearning.entity.Persona;
import com.cibertec.klearning.entity.Produccion;
import com.cibertec.klearning.entity.Proyecto;
import com.cibertec.klearning.entity.enums.EstadoTarea;
import com.cibertec.klearning.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.mapper.ProduccionMapper;
import com.cibertec.klearning.repository.LeccionOvaRepository;
import com.cibertec.klearning.repository.PersonaRepository;
import com.cibertec.klearning.repository.ProduccionRepository;
import com.cibertec.klearning.repository.ProyectoRepository;
import com.cibertec.klearning.service.base.BaseServiceImpl;
import com.cibertec.klearning.service.interfaces.ProduccionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProduccionServiceImpl
        extends BaseServiceImpl<Produccion, String, ProduccionRequestDto, ProduccionResponseDto, ProduccionRepository>
        implements ProduccionService {

    private final ProduccionRepository produccionRepository;
    private final ProduccionMapper produccionMapper;
    private final ProyectoRepository proyectoRepository;
    private final PersonaRepository personaRepository;
    private final LeccionOvaRepository leccionOvaRepository;

    public ProduccionServiceImpl(ProduccionRepository repository,
                                 ProduccionMapper mapper,
                                 ProyectoRepository proyectoRepository,
                                 PersonaRepository personaRepository,
                                 LeccionOvaRepository leccionOvaRepository) {
        super(repository, mapper);
        this.produccionRepository = repository;
        this.produccionMapper = mapper;
        this.proyectoRepository = proyectoRepository;
        this.personaRepository = personaRepository;
        this.leccionOvaRepository = leccionOvaRepository;
    }

    @Override
    protected String nombreEntidad() {
        return "Produccion";
    }

    // ====================================================================
    // OVERRIDE DE MÉTODOS CRUD (Manejo de Relaciones y Reglas de Negocio)
    // ====================================================================

    @Override
    @Transactional
    public ProduccionResponseDto crear(ProduccionRequestDto request, String usuarioActual) {
        Proyecto proyecto = proyectoRepository.findById(request.idProyecto())
                .orElseThrow(() -> new RecursoNoEncontradoException("Proyecto no encontrado"));
        Persona persona = personaRepository.findById(request.idPersona())
                .orElseThrow(() -> new RecursoNoEncontradoException("Persona no encontrada"));
        LeccionOva leccion = leccionOvaRepository.findById(request.idLeccionOva())
                .orElseThrow(() -> new RecursoNoEncontradoException("Lección OVA no encontrada"));

        Produccion produccion = produccionMapper.toEntity(request);
        produccion.setProyecto(proyecto);
        produccion.setPersona(persona);
        produccion.setLeccionOva(leccion);

        if (request.estadoTarea() == EstadoTarea.COMPLETADO) {
            produccion.setFechaHoraFin(LocalDateTime.now());
        }

        produccion.setEstado("1");
        produccion.setCreateUser(usuarioActual);
        produccion.setCreateDate(LocalDateTime.now());

        return produccionMapper.toResponseDto(produccionRepository.save(produccion));
    }

    @Override
    @Transactional
    public ProduccionResponseDto actualizar(String id, ProduccionRequestDto request, String usuarioActual) {
        Produccion produccion = buscarActivoOrThrow(id);
        produccionMapper.actualizarEntidad(request, produccion);

        if (!produccion.getProyecto().getIdProyecto().equals(request.idProyecto())) {
            produccion.setProyecto(proyectoRepository.findById(request.idProyecto())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Proyecto no encontrado")));
        }
        if (!produccion.getPersona().getIdPersona().equals(request.idPersona())) {
            produccion.setPersona(personaRepository.findById(request.idPersona())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Persona no encontrada")));
        }
        if (!produccion.getLeccionOva().getIdLeccionOva().equals(request.idLeccionOva())) {
            produccion.setLeccionOva(leccionOvaRepository.findById(request.idLeccionOva())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Lección OVA no encontrada")));
        }

        if (request.estadoTarea() == EstadoTarea.COMPLETADO && produccion.getFechaHoraFin() == null) {
            produccion.setFechaHoraFin(LocalDateTime.now());
        } else if (request.estadoTarea() != EstadoTarea.COMPLETADO) {
            produccion.setFechaHoraFin(null);
        }

        produccion.setUpdatedUser(usuarioActual);
        produccion.setUpdatedDate(LocalDateTime.now());

        return produccionMapper.toResponseDto(produccionRepository.save(produccion));
    }


    @Override
    public Page<ProduccionResponseDto> listarPorPersonaDni(String dni, Pageable pageable) {
        return produccionRepository.findByPersona_DniCeAndEstado(dni, "1", pageable)
                .map(produccionMapper::toResponseDto);
    }

    @Override
    public Page<ProduccionResponseDto> listarTareasConExcesoDeTiempo(Pageable pageable) {
        return produccionRepository.findTareasConExcesoDeTiempo(pageable)
                .map(produccionMapper::toResponseDto);
    }

    @Override
    public Page<ProduccionResponseDto> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin, Pageable pageable) {
        return produccionRepository.findByFechaHoraInicioBetweenAndEstado(inicio, fin, "1", pageable)
                .map(produccionMapper::toResponseDto);
    }

    @Override
    public Page<ProduccionResponseDto> buscarPaginada(String idProyecto, Boolean huboCorte, Pageable pageable) {
        return produccionRepository.buscarProduccionPaginada(idProyecto, huboCorte, pageable)
                .map(produccionMapper::toResponseDto);
    }

    // ====================================================================
    // MÉTODOS ANALÍTICOS Y AGRUPACIONES
    // ====================================================================

    @Override
    public List<Proyecto> listarProyectosDistintosPorPersonaDni(String dni) {
        return produccionRepository.findProyectosDistintosPorPersonaDni(dni);
    }

    @Override
    public List<ProduccionRepository.TiempoLeccionProjection> calcularTiempoPorLeccionOva(String dni) {
        return produccionRepository.calcularTiempoPorLeccionOva(dni);
    }

    // ====================================================================
    // PROCEDIMIENTO ALMACENADO
    // ====================================================================

    @Override
    @Transactional
    public void cerrarProduccionPorProyecto(String idProyecto) {
        produccionRepository.cerrarProduccionPorProyectoSP(idProyecto);
    }
}