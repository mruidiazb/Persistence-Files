package com.proyecto.persistencia.excepciones;

/**
 * Excepción para indicar que un archivo no ha sido encontrado en la ruta especificada.
 */
public class ArchivoNoEncontradoException extends PersistenciaException {

    public ArchivoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public ArchivoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
