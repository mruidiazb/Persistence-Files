package com.proyecto.persistencia.manejadores;

import com.proyecto.persistencia.dominio.biblioteca.Libro;
import com.proyecto.persistencia.excepciones.PersistenciaException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de persistencia en TXT que implementa la interfaz genérica.
 */
public class ManejadorTXT implements IManejadorArchivos<List<Libro>> {

    @Override
    public void guardar(List<Libro> libros, String rutaArchivo) throws PersistenciaException {
        try (
            FileWriter fw = new FileWriter(rutaArchivo, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(fw)
        ) {
            for (Libro libro : libros) {
                String linea = libro.getId() + ";" + 
                               libro.getTitulo() + ";" + 
                               libro.getAutor() + ";" + 
                               libro.getAnioPublicacion();
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Archivo TXT guardado exitosamente en: " + rutaArchivo);
        } catch (IOException e) {
            throw new com.proyecto.persistencia.excepciones.ErrorLecturaEscrituraException("Error al guardar archivo TXT: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Libro> leer(String rutaArchivo) throws PersistenciaException {
        List<Libro> libros = new ArrayList<>();
        try (
            FileReader fr = new FileReader(rutaArchivo, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(fr)
        ) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 4) {
                    Libro libro = new Libro(
                            datos[0], 
                            datos[1], 
                            datos[2], 
                            Integer.parseInt(datos[3])
                    );
                    libros.add(libro);
                }
            }
            System.out.println("Archivo TXT leído exitosamente.");
            return libros;
        } catch (FileNotFoundException e) {
            throw new com.proyecto.persistencia.excepciones.ArchivoNoEncontradoException("No se encontró el archivo TXT: " + rutaArchivo, e);
        } catch (NumberFormatException e) {
            throw new com.proyecto.persistencia.excepciones.FormatoInvalidoException("Formato inválido en archivo TXT: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new com.proyecto.persistencia.excepciones.ErrorLecturaEscrituraException("Error de I/O al leer archivo TXT: " + e.getMessage(), e);
        }
    }
}
