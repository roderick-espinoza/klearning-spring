package com.cibertec.klearning.business.data.entity.enums;

/**
 * Contrato comun de los catalogos de la aplicacion.
 * <p>
 * Todo enum de catalogo expone una descripcion legible, que es la que se
 * muestra en tablas y formularios. La descripcion nunca se persiste: puede
 * llevar tildes y enie sin afectar a la base de datos.
 */
public interface CatalogoEnum {

    String getDescripcion();
}
