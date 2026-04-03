package com.proyecto.persistencia.manejadores;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.proyecto.persistencia.dominio.empleado.Empleado;
import com.proyecto.persistencia.excepciones.PersistenciaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Manejador de persistencia en CSV utilizando OpenCSV e implementando la interfaz.
 */
public class ManejadorCSV implements IManejadorArchivos<List<Empleado>> {

    private static final Logger logger = LoggerFactory.getLogger(ManejadorCSV.class);

    @Override
    public void guardar(List<Empleado> empleados, String rutaArchivo) throws PersistenciaException {
        try (Writer writer = new FileWriter(rutaArchivo, StandardCharsets.UTF_8)) {
            StatefulBeanToCsv<Empleado> beanToCsv = new StatefulBeanToCsvBuilder<Empleado>(writer)
                    .withQuotechar('\"')
                    .withSeparator(',')
                    .build();
            
            beanToCsv.write(empleados);
            logger.info("Archivo CSV guardado exitosamente en: {}", rutaArchivo);
        } catch (Exception e) {
            throw new com.proyecto.persistencia.excepciones.ErrorLecturaEscrituraException("Error al guardar el archivo CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Empleado> leer(String rutaArchivo) throws PersistenciaException {
        try (Reader reader = new FileReader(rutaArchivo, StandardCharsets.UTF_8)) {
            List<Empleado> empleados = new CsvToBeanBuilder<Empleado>(reader)
                    .withType(Empleado.class)
                    .withSeparator(',')
                    .build()
                    .parse();
                    
            logger.info("Archivo CSV leído exitosamente.");
            return empleados;
        } catch (FileNotFoundException e) {
            throw new com.proyecto.persistencia.excepciones.ArchivoNoEncontradoException("No se encontró el archivo CSV: " + rutaArchivo, e);
        } catch (Exception e) {
            throw new com.proyecto.persistencia.excepciones.FormatoInvalidoException("Error de formato al leer el archivo CSV: " + e.getMessage(), e);
        }
    }
}
