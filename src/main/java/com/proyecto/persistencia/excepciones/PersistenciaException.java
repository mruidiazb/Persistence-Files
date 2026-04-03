package com.proyecto.persistencia.excepciones;

/**
 * Excepción personalizada para envolver errores propios de persistencia o lectura.
 */
public class PersistenciaException extends Exception {

    public PersistenciaException(String mensaje) {
        super(mensaje);
    }

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
