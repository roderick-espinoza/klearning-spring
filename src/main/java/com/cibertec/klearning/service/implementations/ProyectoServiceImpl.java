package com.cibertec.klearning.service.implementations;

import com.cibertec.klearning.dto.proyecto.ProyectoRequestDto;
import com.cibertec.klearning.dto.proyecto.ProyectoResponseDto;
import com.cibertec.klearning.entity.Proyecto;
import com.cibertec.klearning.mapper.ProyectoMapper;
import com.cibertec.klearning.repository.ProyectoRepository;
import com.cibertec.klearning.service.base.BaseServiceImpl;
import com.cibertec.klearning.service.interfaces.ProyectoService;
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
    public ProyectoResponseDto crear(ProyectoRequestDto request, String usuarioActual) {
        return super.crear(request, usuarioActual);
    }

    @Override
    public ProyectoResponseDto actualizar(String id, ProyectoRequestDto request, String usuarioActual) {
        return super.actualizar(id, request, usuarioActual);
    }

    @Override
    public void eliminar(String id, String usuarioActual) { super.eliminar(id, usuarioActual); }


    @Override
    public List<ProyectoResponseDto> listarActivos() {
        return repository.findByEstado("1")
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