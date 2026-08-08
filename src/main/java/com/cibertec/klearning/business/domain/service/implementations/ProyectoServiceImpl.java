package com.cibertec.klearning.business.domain.service.implementations;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;

import com.cibertec.klearning.business.api.dto.proyecto.ProyectoRequestDto;
import com.cibertec.klearning.business.api.dto.proyecto.ProyectoResponseDto;
import com.cibertec.klearning.business.data.entity.Proyecto;
import com.cibertec.klearning.business.domain.mapper.ProyectoMapper;
import com.cibertec.klearning.business.data.repository.ProyectoRepository;
import com.cibertec.klearning.business.domain.service.base.BaseServiceImpl;
import com.cibertec.klearning.business.domain.service.interfaces.ProyectoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServiceImpl
        extends BaseServiceImpl<Proyecto, String, ProyectoRequestDto, ProyectoResponseDto, ProyectoRepository>
        implements ProyectoService {

    private final ProyectoMapper proyectoMapper;

    public ProyectoServiceImpl(ProyectoRepository repository, ProyectoMapper proyectoMapper) {
        super(repository, proyectoMapper);
        this.proyectoMapper = proyectoMapper;
    }

    @Override
    protected String nombreEntidad() {
        return "Proyecto";
    }

    // Estos métodos llaman a "super" para usar la lógica ya programada en BaseServiceImpl
    @Override
    public List<ProyectoResponseDto> listar() { return super.listar(); }

    @Override
    public ProyectoResponseDto obtenerPorId(String id) { return super.obtenerPorId(id); }

    @Override
    public ProyectoResponseDto crear(ProyectoRequestDto request) {
        return super.crear(request);
    }

    @Override
    public ProyectoResponseDto actualizar(String id, ProyectoRequestDto request) {
        return super.actualizar(id, request);
    }

    @Override
    public void eliminar(String id) { super.eliminar(id); }


    @Override
    public List<ProyectoResponseDto> listarActivos() {
        return repository.findByEstado(EstadoRegistro.ACTIVO)
                .stream()
                .map(proyectoMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<ProyectoResponseDto> buscarPorTipoVertical(String tipoVertical) {
        return repository.findByTipoVertical(tipoVertical)
                .stream()
                .map(proyectoMapper::toResponseDto)
                .toList();
    }
}