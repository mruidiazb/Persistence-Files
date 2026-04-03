package com.proyecto.persistencia.manejadores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.proyecto.persistencia.dominio.pedidos.Pedido;
import com.proyecto.persistencia.excepciones.PersistenciaException;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Manejador JSON que implementa la interfaz genérica.
 */
public class ManejadorJSON implements IManejadorArchivos<Pedido> {

    private final ObjectMapper objectMapper;

    public ManejadorJSON() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void guardar(Pedido pedido, String rutaArchivo) throws PersistenciaException {
        try {
            objectMapper.writeValue(new File(rutaArchivo), pedido);
            System.out.println("Archivo JSON guardado exitosamente en: " + rutaArchivo);
        } catch (IOException e) {
            throw new com.proyecto.persistencia.excepciones.ErrorLecturaEscrituraException("Error al guardar archivo JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public Pedido leer(String rutaArchivo) throws PersistenciaException {
        try {
            Pedido pedido = objectMapper.readValue(new File(rutaArchivo), Pedido.class);
            System.out.println("Archivo JSON leído exitosamente.");
            return pedido;
        } catch (FileNotFoundException e) {
            throw new com.proyecto.persistencia.excepciones.ArchivoNoEncontradoException("No se encontró el archivo JSON: " + rutaArchivo, e);
        } catch (JsonProcessingException e) {
            throw new com.proyecto.persistencia.excepciones.FormatoInvalidoException("Error en el formato del archivo JSON: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new com.proyecto.persistencia.excepciones.ErrorLecturaEscrituraException("Error de I/O al leer archivo JSON: " + e.getMessage(), e);
        }
    }
}
