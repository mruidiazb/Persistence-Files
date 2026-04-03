package com.proyecto.persistencia.ejecucion;

import com.proyecto.persistencia.dominio.biblioteca.Libro;
import com.proyecto.persistencia.dominio.empleado.Empleado;
import com.proyecto.persistencia.dominio.pedidos.Pedido;
import com.proyecto.persistencia.dominio.pedidos.Producto;
import com.proyecto.persistencia.excepciones.PersistenciaException;
import com.proyecto.persistencia.manejadores.*;
import com.proyecto.persistencia.utilidades.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal encargada de ejecutar pruebas de persistencia para todos los formatos.
 * Aplicando POO, interfaces, excepciones y manejo de Constants globales.
 */
public class Principal {

    private static final Logger logger = LoggerFactory.getLogger(Principal.class);
    private static final String SEPARADOR = "\n-----------------------------------------\n";

    public static void main(String[] args) {
        // Asegurar que el directorio dinámico exista utilizando nuestra clase Constante
        File dir = new File(Constants.DIRECTORIO_BASE);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            logger.info("\n========================================="
                    + "\n  MENÚ DE EJEMPLOS DE PERSISTENCIA (OOP)"
                    + "\n========================================="
                    + "\n1. Ejecutar Persistencia en TXT  [Dominio: Biblioteca / Libro]"
                    + "\n2. Ejecutar Persistencia en CSV  [Dominio: Empresa / Empleado]"
                    + "\n3. Ejecutar Persistencia en JSON [Dominio: Ventas / Pedido]"
                    + "\n4. Ejecutar Persistencia en XML  [Dominio: Ventas / Pedido]"
                    + "\n5. Ejecutar Todos"
                    + "\n0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    ejecutarTXT(scanner);
                    break;
                case 2:
                    ejecutarCSV(scanner);
                    break;
                case 3:
                    ejecutarJSON(scanner);
                    break;
                case 4:
                    ejecutarXML(scanner);
                    break;
                case 5:
                    ejecutarTXT(scanner);
                    logger.info(SEPARADOR);
                    ejecutarCSV(scanner);
                    logger.info(SEPARADOR);
                    ejecutarJSON(scanner);
                    logger.info(SEPARADOR);
                    ejecutarXML(scanner);
                    break;
                case 0:
                    logger.info("Saliendo del programa...");
                    break;
                default:
                    logger.warn("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();

        logger.info("\n========================================="
                + "\n            PROCESO FINALIZADO"
                + "\n=========================================");
    }

    private static String solicitarRutaArchivo(Scanner scanner, String formatoExtension, String rutaPorDefecto) {
        System.out.print("Ingrese el nombre para el archivo " + formatoExtension + " (Enter para usar el de por defecto): ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            return rutaPorDefecto;
        }
        if (!nombre.toLowerCase().endsWith(formatoExtension.toLowerCase())) {
            nombre += formatoExtension;
        }
        return Constants.DIRECTORIO_BASE + "/" + nombre;
    }

    private static <T> void ejecutarFlujoPersistencia(IManejadorArchivos<T> manejador, T datosOriginales, String ruta, String formato) {
        try {
            manejador.guardar(datosOriginales, ruta);
            T datosLeidos = manejador.leer(ruta);
            logger.info("Datos recuperados del {}:", formato);
            
            if (datosLeidos instanceof Iterable) {
                for (Object item : (Iterable<?>) datosLeidos) {
                    logger.info(" - {}", item);
                }
            } else if (datosLeidos instanceof Pedido pedido) {
                logger.info(" - {}", pedido);
                for (Producto p : pedido.getProductos()) {
                    logger.info("   * {}", p);
                }
            } else {
                logger.info(" - {}", datosLeidos);
            }
        } catch (PersistenciaException e) {
            logger.error("Falla de capa persistencia ({}): {}", formato, e.getMessage());
        }
    }

    private static void ejecutarTXT(Scanner scanner) {
        logger.info(">> PERSISTENCIA EN TXT <<");
        String ruta = solicitarRutaArchivo(scanner, ".txt", Constants.RUTA_TXT);
        IManejadorArchivos<List<Libro>> manejador = new ManejadorTXT();

        List<Libro> librosOriginales = new ArrayList<>();
        librosOriginales.add(new Libro("L001", "Cien Años de Soledad", "Gabriel Garcia Marquez", 1967));
        librosOriginales.add(new Libro("L002", "El Aleph", "Jorge Luis Borges", 1949));
        librosOriginales.add(new Libro("L003", "Don Quijote", "Miguel de Cervantes", 1605));

        ejecutarFlujoPersistencia(manejador, librosOriginales, ruta, "TXT");
    }

    private static void ejecutarCSV(Scanner scanner) {
        logger.info(">> PERSISTENCIA EN CSV <<");
        String ruta = solicitarRutaArchivo(scanner, ".csv", Constants.RUTA_CSV);
        IManejadorArchivos<List<Empleado>> manejador = new ManejadorCSV();

        List<Empleado> empleadosOriginales = new ArrayList<>();
        empleadosOriginales.add(new Empleado(101, "Ana Martinez", "Ventas", 2500.50));
        empleadosOriginales.add(new Empleado(102, "Carlos Gomez", "IT", 3800.75));
        empleadosOriginales.add(new Empleado(103, "Laura Sarmiento", "Recursos Humanos", 2100.00));

        ejecutarFlujoPersistencia(manejador, empleadosOriginales, ruta, "CSV");
    }

    private static void ejecutarJSON(Scanner scanner) {
        logger.info(">> PERSISTENCIA EN JSON <<");
        String ruta = solicitarRutaArchivo(scanner, ".json", Constants.RUTA_JSON);
        IManejadorArchivos<Pedido> manejador = new ManejadorJSON();

        List<Producto> productos = Arrays.asList(
                new Producto("P01", "Laptop Dell", 1, 1500.00),
                new Producto("P02", "Mouse Inalámbrico", 2, 25.50)
        );
        Pedido pedidoOriginal = new Pedido("ORD-9988", "Empresa ABC S.A.", productos, 1551.00);

        ejecutarFlujoPersistencia(manejador, pedidoOriginal, ruta, "JSON");
    }

    private static void ejecutarXML(Scanner scanner) {
        logger.info(">> PERSISTENCIA EN XML <<");
        String ruta = solicitarRutaArchivo(scanner, ".xml", Constants.RUTA_XML);
        IManejadorArchivos<Pedido> manejador = new ManejadorXML();

        List<Producto> productos = Arrays.asList(
                new Producto("P03", "Teclado Mecánico", 1, 80.00),
                new Producto("P04", "Monitor 24 pulgadas", 2, 150.00)
        );
        Pedido pedidoOriginal = new Pedido("ORD-5544", "Juan Perez", productos, 380.00);

        ejecutarFlujoPersistencia(manejador, pedidoOriginal, ruta, "XML");
    }
}
