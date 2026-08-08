package com.cibertec.klearning.business.data.entity.converter;

import com.cibertec.klearning.business.data.entity.enums.Nacionalidad;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NacionalidadConverter extends CatalogoEnumConverter<Nacionalidad> {

    public NacionalidadConverter() {
        super(Nacionalidad.class);
    }
}
