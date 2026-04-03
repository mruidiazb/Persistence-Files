package com.proyecto.persistencia.manejadores;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.proyecto.persistencia.dominio.pedidos.Pedido;
import com.proyecto.persistencia.excepciones.PersistenciaException;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Manejador XML que implementa la interfaz genérica.
 */
public class ManejadorXML implements IManejadorArchivos<Pedido> {

    private final XmlMapper xmlMapper;

    public ManejadorXML() {
        xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void guardar(Pedido pedido, String rutaArchivo) throws PersistenciaException {
        try {
            xmlMapper.writeValue(new File(rutaArchivo), pedido);
            System.out.println("Archivo XML guardado exitosamente en: " + rutaArchivo);
        } catch (IOException e) {
            throw new com.proyecto.persistencia.excepciones.ErrorLecturaEscrituraException("Error al guardar archivo XML: " + e.getMessage(), e);
        }
    }

    @Override
    public Pedido leer(String rutaArchivo) throws PersistenciaException {
        try {
            Pedido pedido = xmlMapper.readValue(new File(rutaArchivo), Pedido.class);
            System.out.println("Archivo XML leído exitosamente.");
            return pedido;
        } catch (FileNotFoundException e) {
            throw new com.proyecto.persistencia.excepciones.ArchivoNoEncontradoException("No se encontró el archivo XML: " + rutaArchivo, e);
        } catch (JsonProcessingException e) {
            throw new com.proyecto.persistencia.excepciones.FormatoInvalidoException("Error en el formato del archivo XML: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new com.proyecto.persistencia.excepciones.ErrorLecturaEscrituraException("Error de I/O al leer archivo XML: " + e.getMessage(), e);
        }
    }
}
