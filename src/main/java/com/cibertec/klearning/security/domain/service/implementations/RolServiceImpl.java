package com.cibertec.klearning.security.domain.service.implementations;

import com.cibertec.klearning.business.api.exception.RecursoDuplicadoException;
import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.security.api.dto.rol.RolRequestDto;
import com.cibertec.klearning.security.api.dto.rol.RolResponseDto;
import com.cibertec.klearning.security.data.entity.Rol;
import com.cibertec.klearning.security.domain.mapper.RolMapper;
import com.cibertec.klearning.security.data.repository.RolRepository;
import com.cibertec.klearning.business.domain.service.base.BaseServiceImpl;
import com.cibertec.klearning.security.domain.service.interfaces.RolService;
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
    public List<RolResponseDto> listarActivos() {
        return repository.findByEstado(EstadoRegistro.ACTIVO)
                .stream()
                .map(rolMapper::toResponseDto)
                .toList();
    }

    @Override
    public RolResponseDto obtenerPorId(String id) { return super.obtenerPorId(id); }
    @Override
    public RolResponseDto crear(RolRequestDto request) {
        if (repository.existsByCodigoRolIgnoreCase(request.codigoRol())) {
            throw new RecursoDuplicadoException(
                    "Ya existe un rol con el código: " + request.codigoRol());
        }
        return super.crear(request);
    }

    @Override
    public RolResponseDto actualizar(String id, RolRequestDto request) {
        Rol existente = buscarActivoOrThrow(id);

        boolean cambioCodigo = !existente.getCodigoRol().equalsIgnoreCase(request.codigoRol());

        if (cambioCodigo && repository.existsByCodigoRolIgnoreCase(request.codigoRol())) {
            throw new RecursoDuplicadoException(
                    "Ya existe un rol con el código: " + request.codigoRol());
        }

        return super.actualizar(id, request);
    }

    @Override
    public void eliminar(String id) { super.eliminar(id); }
}
