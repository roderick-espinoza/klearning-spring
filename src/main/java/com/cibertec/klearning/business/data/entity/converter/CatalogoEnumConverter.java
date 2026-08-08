package com.cibertec.klearning.business.data.entity.converter;

import com.cibertec.klearning.business.data.entity.enums.CatalogoCodificado;
import jakarta.persistence.AttributeConverter;

import java.util.Arrays;

/**
 * Traduce entre el enum y el codigo corto que se guarda en la columna.
 * <p>
 * Cada catalogo codificado solo tiene que declarar una subclase de una linea
 * anotada con {@code @Converter(autoApply = true)}; asi las entidades quedan
 * limpias, sin un {@code @Convert} repetido en cada campo.
 */
public abstract class CatalogoEnumConverter<E extends Enum<E> & CatalogoCodificado>
        implements AttributeConverter<E, String> {

    private final Class<E> tipoEnum;

    protected CatalogoEnumConverter(Class<E> tipoEnum) {
        this.tipoEnum = tipoEnum;
    }

    @Override
    public String convertToDatabaseColumn(E valor) {
        return valor == null ? null : valor.getCodigo();
    }

    @Override
    public E convertToEntityAttribute(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        return Arrays.stream(tipoEnum.getEnumConstants())
                .filter(constante -> constante.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Codigo '" + codigo + "' no valido para "
                                + tipoEnum.getSimpleName()));
    }
}
