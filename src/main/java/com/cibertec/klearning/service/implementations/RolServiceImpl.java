package com.cibertec.klearning.service.implementations;

import com.cibertec.klearning.dto.rol.RolRequestDto;
import com.cibertec.klearning.dto.rol.RolResponseDto;
import com.cibertec.klearning.entity.Rol;
import com.cibertec.klearning.mapper.RolMapper;
import com.cibertec.klearning.repository.RolRepository;
import com.cibertec.klearning.service.base.BaseServiceImpl;
import com.cibertec.klearning.service.interfaces.RolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl
        extends BaseServiceImpl<Rol, String, RolRequestDto, RolResponseDto, RolRepository>
        implements RolService {

    private final RolMapper rolMapper;

    public RolServiceImpl(RolRepository repository, RolMapper rolMapper) {
        super(repository, rolMapper);
        this.rolMapper = rolMapper;
    }

    @Override
    protected String nombreEntidad() { return "Rol"; }

    @Override
    public List<RolResponseDto> listar() { return super.listar(); }
    @Override
    public RolResponseDto obtenerPorId(String id) { return super.obtenerPorId(id); }
    @Override
    public RolResponseDto crear(RolRequestDto request, String usuarioActual) { return super.crear(request, usuarioActual); }
    @Override
    public RolResponseDto actualizar(String id, RolRequestDto request, String usuarioActual) { return super.actualizar(id, request, usuarioActual); }
    @Override
    public void eliminar(String id, String usuarioActual) { super.eliminar(id, usuarioActual); }
}
