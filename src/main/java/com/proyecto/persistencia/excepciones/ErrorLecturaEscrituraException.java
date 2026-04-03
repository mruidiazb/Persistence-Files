package com.proyecto.persistencia.excepciones;

/**
 * Excepción para indicar que ha ocurrido un error general de lectura o escritura de archivos.
 */
public class ErrorLecturaEscrituraException extends PersistenciaException {

    public ErrorLecturaEscrituraException(String mensaje) {
        super(mensaje);
    }

    public ErrorLecturaEscrituraException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
