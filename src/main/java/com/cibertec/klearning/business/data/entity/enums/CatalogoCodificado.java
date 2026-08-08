package com.cibertec.klearning.business.data.entity.enums;

/**
 * Catalogo que se guarda en base de datos con un codigo corto en vez de con
 * el nombre de la constante. Se usa solo cuando la columna ya era compacta
 * (CHAR(1)) o cuando el codigo es un estandar reconocido, como el ISO 3166-1
 * de dos letras para el pais.
 * <p>
 * La traduccion codigo &lt;-&gt; enum la hace un AttributeConverter.
 */
public interface CatalogoCodificado extends CatalogoEnum {

    String getCodigo();
}
