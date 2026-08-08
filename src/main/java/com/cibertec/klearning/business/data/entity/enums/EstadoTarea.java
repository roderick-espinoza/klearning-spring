package com.cibertec.klearning.business.data.entity.enums;

public enum EstadoTarea implements CatalogoEnum {

    PENDIENTE("Pendiente"),
    EN_PROGRESO("En Progreso"),
    DESARROLLADO("Desarrollado"),
    COMPLETADO("Completado");

    private final String descripcion;

    EstadoTarea(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
