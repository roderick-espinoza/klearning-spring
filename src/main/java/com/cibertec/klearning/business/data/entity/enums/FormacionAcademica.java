package com.cibertec.klearning.business.data.entity.enums;

/**
 * Se persiste con {@code @Enumerated(EnumType.STRING)}: en base de datos queda
 * el nombre de la constante (SECUNDARIA, TECNICA...), sin tildes. Las tildes
 * viven solo en la descripcion, que es lo que ve el usuario.
 */
public enum FormacionAcademica implements CatalogoEnum {

    SECUNDARIA("Secundaria"),
    TECNICA("Técnica"),
    UNIVERSITARIA("Universitaria"),
    POSTGRADO("Postgrado");

    private final String descripcion;

    FormacionAcademica(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
