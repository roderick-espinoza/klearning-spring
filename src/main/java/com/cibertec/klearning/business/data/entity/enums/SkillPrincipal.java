package com.cibertec.klearning.business.data.entity.enums;

public enum SkillPrincipal implements CatalogoEnum {

    DISENADOR_INSTRUCCIONAL("Diseñador Instruccional"),
    DISENADOR_GRAFICO("Diseñador Gráfico"),
    PROGRAMADOR("Programador"),
    ANALISTA_QA("Analista QA"),
    ADMINISTRADOR_BD("Administrador de BD");

    private final String descripcion;

    SkillPrincipal(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
