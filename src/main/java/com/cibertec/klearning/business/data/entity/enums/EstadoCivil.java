package com.cibertec.klearning.business.data.entity.enums;

import java.util.Arrays;

public enum EstadoCivil implements CatalogoCodificado {

    SOLTERO("S", "Soltero(a)"),
    CASADO("C", "Casado(a)"),
    VIUDO("V", "Viudo(a)"),
    DIVORCIADO("D", "Divorciado(a)");

    private final String codigo;
    private final String descripcion;

    EstadoCivil(String codigo, String descripcion) {
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

    public static EstadoCivil desdeCodigo(String codigo) {
        return Arrays.stream(values())
                .filter(estadoCivil -> estadoCivil.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Estado civil desconocido: " + codigo));
    }
}
