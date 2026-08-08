package com.cibertec.klearning.business.data.entity.enums;

public enum ModalidadTrabajo implements CatalogoEnum {

    PRESENCIAL("Presencial"),
    SEMIPRESENCIAL("Semipresencial"),
    REMOTO("Remoto");

    private final String descripcion;

    ModalidadTrabajo(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
