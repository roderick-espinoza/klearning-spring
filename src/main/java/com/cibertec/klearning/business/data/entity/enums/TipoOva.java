package com.cibertec.klearning.business.data.entity.enums;

public enum TipoOva implements CatalogoEnum {

    TEORICO("Teórico"),
    PRACTICO("Práctico"),
    TEORICO_PRACTICO("Teórico / Práctico");

    private final String descripcion;

    TipoOva(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
