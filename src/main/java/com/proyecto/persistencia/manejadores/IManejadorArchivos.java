package com.proyecto.persistencia.manejadores;

import com.proyecto.persistencia.excepciones.PersistenciaException;

/**
 * Contrato base para los manejadores de archivos.
 * Emplea genéricos (T) para que cada implementación defina si lee/escribe 
 * listas de entidades (ej. List&lt;Empleado&gt;) o una sola entidad compleja (ej. Pedido).
 *
 * @param <T> El tipo de dato persistido.
 */
public interface IManejadorArchivos<T> {

    /**
     * Guarda el dato persistido a la ruta específica.
     * 
     * @param entidad La entidad o elemento a guardar (puede ser listado).
     * @param rutaArchivo Ruta local (ej. src/main/resources/...).
     * @throws PersistenciaException Si ocurre algún error en I/O.
     */
    void guardar(T entidad, String rutaArchivo) throws PersistenciaException;

    /**
     * Lee un archivo devolviendo un objeto o estructura del tipo T.
     * 
     * @param rutaArchivo La ruta a leer de disco.
     * @return Entidad en memoria del sistema.
     * @throws PersistenciaException Si la lectura no es posible o falla el formateo.
     */
    T leer(String rutaArchivo) throws PersistenciaException;
}
