package com.cibertec.klearning.service.implementations;

import com.cibertec.klearning.dto.ova.OvaRequestDto;
import com.cibertec.klearning.dto.ova.OvaResponseDto;
import com.cibertec.klearning.entity.Ova;
import com.cibertec.klearning.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.mapper.OvaMapper;
import com.cibertec.klearning.repository.OvaRepository;
import com.cibertec.klearning.service.base.BaseServiceImpl;
import com.cibertec.klearning.service.interfaces.OvaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OvaServiceImpl
    extends BaseServiceImpl<Ova, String, OvaRequestDto, OvaResponseDto, OvaRepository>
    implements OvaService {

    public OvaServiceImpl(OvaRepository repository, OvaMapper ovaMapper) {
        super(repository, ovaMapper);
    }

    @Override
    protected String nombreEntidad() { return "Ova";}

    @Override
    public List<OvaResponseDto> listar() { return super.listar();}
    @Override
    public OvaResponseDto obtenerPorId(String id) { return super.obtenerPorId(id);}
    @Override
    public OvaResponseDto crear(OvaRequestDto request, String usuarioActual) { return super.crear(request, usuarioActual);}
    @Override
    public OvaResponseDto actualizar(String id,OvaRequestDto request, String usuarioActual) { return super.crear(request, usuarioActual);}
    @Override
    public void eliminar(String id, String usuarioActual) { super.eliminar(id, usuarioActual);}


}
