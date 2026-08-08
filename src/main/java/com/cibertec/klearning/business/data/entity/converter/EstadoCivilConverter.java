package com.cibertec.klearning.business.data.entity.converter;

import com.cibertec.klearning.business.data.entity.enums.EstadoCivil;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoCivilConverter extends CatalogoEnumConverter<EstadoCivil> {

    public EstadoCivilConverter() {
        super(EstadoCivil.class);
    }
}
