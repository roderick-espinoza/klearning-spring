package com.cibertec.klearning.security.api.view;

/**
 * Los paquetes donde viven los controladores de vista, para que los
 * {@code @ControllerAdvice} se declaren por paquete y no por tipos.
 * <p>
 * Apuntar los advices a una lista de clases obliga a acordarse de anadir cada
 * vista nueva; si alguien se olvida, la pagina se renderiza con los atributos
 * del modelo a null en vez de fallar, que es peor. Con el paquete basta con
 * dejar el controlador donde le toca.
 * <p>
 * Son constantes de compilacion a proposito: solo asi se pueden usar dentro de
 * una anotacion.
 */
public final class PaquetesVista {

    public static final String SECURITY = "com.cibertec.klearning.security.api.view";
    public static final String BUSINESS = "com.cibertec.klearning.business.api.view";

    private PaquetesVista() {
    }
}
