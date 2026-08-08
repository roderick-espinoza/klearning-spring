package com.cibertec.klearning.business.domain.service.implementations;

import com.cibertec.klearning.business.api.dto.ova.OvaRequestDto;
import com.cibertec.klearning.business.api.dto.ova.OvaResponseDto;
import com.cibertec.klearning.business.data.entity.Ova;
import com.cibertec.klearning.business.api.exception.RecursoNoEncontradoException;
import com.cibertec.klearning.business.domain.mapper.OvaMapper;
import com.cibertec.klearning.business.data.repository.OvaRepository;
import com.cibertec.klearning.business.domain.service.base.BaseServiceImpl;
import com.cibertec.klearning.business.domain.service.interfaces.OvaService;
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
    public OvaResponseDto crear(OvaRequestDto request) { return super.crear(request);}
    @Override
    public OvaResponseDto actualizar(String id,OvaRequestDto request) { return super.crear(request);}
    @Override
    public void eliminar(String id) { super.eliminar(id);}


}
