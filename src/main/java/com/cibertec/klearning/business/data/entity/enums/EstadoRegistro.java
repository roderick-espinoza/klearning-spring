package com.cibertec.klearning.business.data.entity.enums;

import java.util.Arrays;

/**
 * Estado de auditoria comun a todas las tablas (columna {@code estado CHAR(1)}).
 * Es la base del borrado logico: {@link #ELIMINADO} nunca se borra fisicamente.
 */
public enum EstadoRegistro implements CatalogoCodificado {

    ACTIVO("1", "Activo"),
    INACTIVO("0", "Inactivo"),
    ELIMINADO("2", "Eliminado");

    private final String codigo;
    private final String descripcion;

    EstadoRegistro(String codigo, String descripcion) {
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

    public static EstadoRegistro desdeCodigo(String codigo) {
        return Arrays.stream(values())
                .filter(estado -> estado.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Estado de registro desconocido: " + codigo));
    }
}
