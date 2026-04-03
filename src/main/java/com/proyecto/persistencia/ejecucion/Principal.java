package com.proyecto.persistencia.ejecucion;

import com.proyecto.persistencia.dominio.biblioteca.Libro;
import com.proyecto.persistencia.dominio.empleado.Empleado;
import com.proyecto.persistencia.dominio.pedidos.Pedido;
import com.proyecto.persistencia.dominio.pedidos.Producto;
import com.proyecto.persistencia.excepciones.PersistenciaException;
import com.proyecto.persistencia.manejadores.*;
import com.proyecto.persistencia.utilidades.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase principal encargada de ejecutar pruebas de persistencia para todos los formatos.
 * Aplicando POO, interfaces, excepciones y manejo de Constants globales.
 */
public class Principal {

    public static void main(String[] args) {
        // Asegurar que el directorio dinámico exista utilizando nuestra clase Constante
        File dir = new File(Constants.DIRECTORIO_BASE);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        System.out.println("=========================================");
        System.out.println("  INICIANDO EJEMPLOS DE PERSISTENCIA (OOP)");
        System.out.println("=========================================\n");

        ejecutarTXT();
        System.out.println("\n-----------------------------------------\n");
        ejecutarCSV();
        System.out.println("\n-----------------------------------------\n");
        ejecutarJSON();
        System.out.println("\n-----------------------------------------\n");
        ejecutarXML();
        
        System.out.println("\n=========================================");
        System.out.println("            PROCESO FINALIZADO");
        System.out.println("=========================================");
    }

    private static void ejecutarTXT() {
        System.out.println(">> PERSISTENCIA EN TXT <<");
        IManejadorArchivos<List<Libro>> manejador = new ManejadorTXT();

        List<Libro> librosOriginales = new ArrayList<>();
        librosOriginales.add(new Libro("L001", "Cien Años de Soledad", "Gabriel Garcia Marquez", 1967));
        librosOriginales.add(new Libro("L002", "El Aleph", "Jorge Luis Borges", 1949));
        librosOriginales.add(new Libro("L003", "Don Quijote", "Miguel de Cervantes", 1605));

        try {
            manejador.guardar(librosOriginales, Constants.RUTA_TXT);
            List<Libro> librosLeidos = manejador.leer(Constants.RUTA_TXT);
            System.out.println("Datos recuperados del TXT:");
            for (Libro l : librosLeidos) {
                System.out.println(" - " + l);
            }
        } catch (PersistenciaException e) {
            System.err.println("Falla de capa persistencia (TXT): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void ejecutarCSV() {
        System.out.println(">> PERSISTENCIA EN CSV <<");
        IManejadorArchivos<List<Empleado>> manejador = new ManejadorCSV();

        List<Empleado> empleadosOriginales = new ArrayList<>();
        empleadosOriginales.add(new Empleado(101, "Ana Martinez", "Ventas", 2500.50));
        empleadosOriginales.add(new Empleado(102, "Carlos Gomez", "IT", 3800.75));
        empleadosOriginales.add(new Empleado(103, "Laura Sarmiento", "Recursos Humanos", 2100.00));

        try {
            manejador.guardar(empleadosOriginales, Constants.RUTA_CSV);
            List<Empleado> empleadosLeidos = manejador.leer(Constants.RUTA_CSV);
            System.out.println("Datos recuperados del CSV:");
            for (Empleado e : empleadosLeidos) {
                System.out.println(" - " + e);
            }
        } catch (PersistenciaException e) {
            System.err.println("Falla de capa persistencia (CSV): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void ejecutarJSON() {
        System.out.println(">> PERSISTENCIA EN JSON <<");
        IManejadorArchivos<Pedido> manejador = new ManejadorJSON();

        List<Producto> productos = Arrays.asList(
                new Producto("P01", "Laptop Dell", 1, 1500.00),
                new Producto("P02", "Mouse Inalámbrico", 2, 25.50)
        );
        Pedido pedidoOriginal = new Pedido("ORD-9988", "Empresa ABC S.A.", productos, 1551.00);

        try {
            manejador.guardar(pedidoOriginal, Constants.RUTA_JSON);
            Pedido pedidoLeido = manejador.leer(Constants.RUTA_JSON);
            System.out.println("Datos recuperados del JSON:");
            System.out.println(" - " + pedidoLeido);
            for(Producto p : pedidoLeido.getProductos()) {
                System.out.println("   * " + p);
            }
        } catch (PersistenciaException e) {
            System.err.println("Falla de capa persistencia (JSON): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void ejecutarXML() {
        System.out.println(">> PERSISTENCIA EN XML <<");
        IManejadorArchivos<Pedido> manejador = new ManejadorXML();

        List<Producto> productos = Arrays.asList(
                new Producto("P03", "Teclado Mecánico", 1, 80.00),
                new Producto("P04", "Monitor 24 pulgadas", 2, 150.00)
        );
        Pedido pedidoOriginal = new Pedido("ORD-5544", "Juan Perez", productos, 380.00);

        try {
            manejador.guardar(pedidoOriginal, Constants.RUTA_XML);
            Pedido pedidoLeido = manejador.leer(Constants.RUTA_XML);
            System.out.println("Datos recuperados del XML:");
            System.out.println(" - " + pedidoLeido);
            for(Producto p : pedidoLeido.getProductos()) {
                System.out.println("   * " + p);
            }
        } catch (PersistenciaException e) {
            System.err.println("Falla de capa persistencia (XML): " + e.getMessage());
            e.printStackTrace();
        }
    }
}
