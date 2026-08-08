package com.cibertec.klearning.business.data.entity.converter;

import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoRegistroConverter extends CatalogoEnumConverter<EstadoRegistro> {

    public EstadoRegistroConverter() {
        super(EstadoRegistro.class);
    }
}
