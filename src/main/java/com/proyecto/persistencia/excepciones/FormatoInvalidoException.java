package com.proyecto.persistencia.excepciones;

/**
 * Excepción para indicar que el formato del archivo es inválido o no se puede parsear.
 */
public class FormatoInvalidoException extends PersistenciaException {

    public FormatoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public FormatoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
