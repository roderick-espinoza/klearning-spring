package com.cibertec.klearning.business.data.entity.enums;

import java.util.Arrays;

/**
 * Nacionalidad del personal. El codigo es el ISO 3166-1 alpha-2 del pais,
 * que es un estandar internacional, por eso se guarda compacto en CHAR(2).
 */
public enum Nacionalidad implements CatalogoCodificado {

    PERUANA("PE", "Peruana"),
    VENEZOLANA("VE", "Venezolana"),
    COLOMBIANA("CO", "Colombiana"),
    ECUATORIANA("EC", "Ecuatoriana"),
    BOLIVIANA("BO", "Boliviana"),
    CHILENA("CL", "Chilena"),
    ARGENTINA("AR", "Argentina"),
    OTRA("OT", "Otra");

    private final String codigo;
    private final String descripcion;

    Nacionalidad(String codigo, String descripcion) {
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

    public static Nacionalidad desdeCodigo(String codigo) {
        return Arrays.stream(values())
                .filter(nacionalidad -> nacionalidad.codigo.equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nacionalidad desconocida: " + codigo));
    }
}
