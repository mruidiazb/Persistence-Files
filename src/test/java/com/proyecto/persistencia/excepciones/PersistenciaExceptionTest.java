package com.proyecto.persistencia.excepciones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersistenciaExceptionTest {

    // ===== PersistenciaException =====

    @Test
    void testPersistenciaExceptionConMensaje() {
        PersistenciaException ex = new PersistenciaException("Error de prueba");
        assertEquals("Error de prueba", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testPersistenciaExceptionConMensajeYCausa() {
        Throwable causa = new RuntimeException("causa original");
        PersistenciaException ex = new PersistenciaException("Error con causa", causa);
        assertEquals("Error con causa", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }

    // ===== ArchivoNoEncontradoException =====

    @Test
    void testArchivoNoEncontradoExceptionConMensaje() {
        ArchivoNoEncontradoException ex = new ArchivoNoEncontradoException("archivo.txt no existe");
        assertEquals("archivo.txt no existe", ex.getMessage());
        assertNull(ex.getCause());
        assertInstanceOf(PersistenciaException.class, ex);
    }

    @Test
    void testArchivoNoEncontradoExceptionConMensajeYCausa() {
        Throwable causa = new RuntimeException("IO error");
        ArchivoNoEncontradoException ex = new ArchivoNoEncontradoException("no encontrado", causa);
        assertEquals("no encontrado", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }

    // ===== ErrorLecturaEscrituraException =====

    @Test
    void testErrorLecturaEscrituraExceptionConMensaje() {
        ErrorLecturaEscrituraException ex = new ErrorLecturaEscrituraException("fallo IO");
        assertEquals("fallo IO", ex.getMessage());
        assertNull(ex.getCause());
        assertInstanceOf(PersistenciaException.class, ex);
    }

    @Test
    void testErrorLecturaEscrituraExceptionConMensajeYCausa() {
        Throwable causa = new RuntimeException("disco lleno");
        ErrorLecturaEscrituraException ex = new ErrorLecturaEscrituraException("fallo IO", causa);
        assertEquals("fallo IO", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }

    // ===== FormatoInvalidoException =====

    @Test
    void testFormatoInvalidoExceptionConMensaje() {
        FormatoInvalidoException ex = new FormatoInvalidoException("formato incorrecto");
        assertEquals("formato incorrecto", ex.getMessage());
        assertNull(ex.getCause());
        assertInstanceOf(PersistenciaException.class, ex);
    }

    @Test
    void testFormatoInvalidoExceptionConMensajeYCausa() {
        Throwable causa = new RuntimeException("parse error");
        FormatoInvalidoException ex = new FormatoInvalidoException("formato incorrecto", causa);
        assertEquals("formato incorrecto", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }
}
