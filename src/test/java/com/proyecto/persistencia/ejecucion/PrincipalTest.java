package com.proyecto.persistencia.ejecucion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PrincipalTest {

    private final InputStream standardIn = System.in;
    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
        System.setOut(standardOut);
        System.setErr(standardErr);
    }

    @Test
    void testEjecutarTodos() {
        // Opción 5 (todos) con Enter vacío para nombres por defecto, luego 0 para salir
        String input = "5\n\n\n\n\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("PROCESO FINALIZADO"));
        assertTrue(output.contains("Datos recuperados del TXT:"));
        assertTrue(output.contains("Datos recuperados del CSV:"));
        assertTrue(output.contains("Datos recuperados del JSON:"));
        assertTrue(output.contains("Datos recuperados del XML:"));
    }

    @Test
    void testEjecutarTXTIndividual() {
        // Opción 1 (TXT) con nombre por defecto, luego 0 para salir
        String input = "1\n\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("PERSISTENCIA EN TXT"));
        assertTrue(output.contains("Datos recuperados del TXT:"));
    }

    @Test
    void testEjecutarCSVIndividual() {
        String input = "2\n\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("PERSISTENCIA EN CSV"));
        assertTrue(output.contains("Datos recuperados del CSV:"));
    }

    @Test
    void testEjecutarJSONIndividual() {
        String input = "3\n\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("PERSISTENCIA EN JSON"));
        assertTrue(output.contains("Datos recuperados del JSON:"));
    }

    @Test
    void testEjecutarXMLIndividual() {
        String input = "4\n\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("PERSISTENCIA EN XML"));
        assertTrue(output.contains("Datos recuperados del XML:"));
    }

    @Test
    void testOpcionInvalida() {
        // Opción 9 (inválida), luego 0 para salir
        String input = "9\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("no válida"));
    }

    @Test
    void testEntradaNoNumerica() {
        // Texto no numérico, luego 0 para salir
        String input = "abc\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("no válida"));
    }

    @Test
    void testNombreArchivoPersonalizado() {
        // Opción 1 (TXT) con nombre personalizado "mi_libro", luego 0
        String input = "1\nmi_libro\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Datos recuperados del TXT:"));
    }

    @Test
    void testNombreArchivoConExtension() {
        // Opción 1 (TXT) con nombre que ya tiene extensión
        String input = "1\nmi_libro.txt\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Principal.main(new String[]{});

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Datos recuperados del TXT:"));
    }
}
