package com.cibertec.klearning.business.data.entity.converter;

import com.cibertec.klearning.business.data.entity.enums.Sexo;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SexoConverter extends CatalogoEnumConverter<Sexo> {

    public SexoConverter() {
        super(Sexo.class);
    }
}
