package com.cibertec.klearning.business.data.entity.enums;

import java.util.Arrays;

public enum Sexo implements CatalogoCodificado {

    MASCULINO("M", "Masculino"),
    FEMENINO("F", "Femenino"),
    OTRO("O", "Otro");

    private final String codigo;
    private final String descripcion;

    Sexo(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public static Sexo desdeCodigo(String codigo) {
        return Arrays.stream(values())
                .filter(sexo -> sexo.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sexo desconocido: " + codigo));
    }
}
