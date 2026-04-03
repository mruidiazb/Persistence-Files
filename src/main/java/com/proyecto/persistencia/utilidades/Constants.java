package com.proyecto.persistencia.utilidades;

/**
 * Clase de utilidades que almacena las constantes globales del proyecto.
 * Define las rutas de persistencia centralizadas para no utilizar valores 
 * "quemados" (hardcoded) durante la ejecución.
 */
public final class Constants {

    // Constructor privado para evitar que la clase sea instanciada (Patrón Utility Class)
    private Constants() {
    }

    /** Directorio raíz donde viviran todos los archivos persistidos */
    public static final String DIRECTORIO_BASE = "src/main/resources/files";

    /** Rutas pre-ensambladas para cada formato */
    public static final String RUTA_TXT = DIRECTORIO_BASE + "/libros.txt";
    public static final String RUTA_CSV = DIRECTORIO_BASE + "/empleados.csv";
    public static final String RUTA_JSON = DIRECTORIO_BASE + "/pedido.json";
    public static final String RUTA_XML = DIRECTORIO_BASE + "/pedido.xml";

}
