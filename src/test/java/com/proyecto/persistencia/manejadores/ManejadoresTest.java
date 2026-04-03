package com.proyecto.persistencia.manejadores;

import com.proyecto.persistencia.dominio.biblioteca.Libro;
import com.proyecto.persistencia.dominio.empleado.Empleado;
import com.proyecto.persistencia.dominio.pedidos.Pedido;
import com.proyecto.persistencia.dominio.pedidos.Producto;
import com.proyecto.persistencia.excepciones.PersistenciaException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManejadoresTest {

    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("persistencia_test");
    }

    @AfterEach
    void tearDown() {
        if (tempDir != null) {
            File[] files = tempDir.toFile().listFiles();
            if (files != null) {
                for (File f : files) f.delete();
            }
            tempDir.toFile().delete();
        }
    }

    // ==== TXT ====

    @Test
    void testManejadorTXTGuardaYLee() throws PersistenciaException {
        IManejadorArchivos<List<Libro>> manejador = new ManejadorTXT();
        String ruta = tempDir.resolve("libros.txt").toString();

        List<Libro> libros = new ArrayList<>();
        libros.add(new Libro("T001", "Libro Test", "Autor", 2020));
        libros.add(new Libro("T002", "Otro Libro", "Otro Autor", 2021));

        manejador.guardar(libros, ruta);
        assertTrue(new File(ruta).exists());

        List<Libro> leidos = manejador.leer(ruta);
        assertEquals(2, leidos.size());
        assertEquals("T001", leidos.get(0).getId());
        assertEquals("Libro Test", leidos.get(0).getTitulo());
        assertEquals("Autor", leidos.get(0).getAutor());
        assertEquals(2020, leidos.get(0).getAnioPublicacion());
    }

    @Test
    void testManejadorTXTLeerArchivoInexistente() {
        IManejadorArchivos<List<Libro>> manejador = new ManejadorTXT();
        String ruta = tempDir.resolve("no_existe.txt").toString();

        assertThrows(PersistenciaException.class, () -> manejador.leer(ruta));
    }

    @Test
    void testManejadorTXTLeerLineaMalFormateada() throws Exception {
        String ruta = tempDir.resolve("malformato.txt").toString();
        try (FileWriter fw = new FileWriter(ruta)) {
            fw.write("soloUnCampo\n");
            fw.write("T001;Titulo;Autor;2020\n");
        }

        IManejadorArchivos<List<Libro>> manejador = new ManejadorTXT();
        List<Libro> leidos = manejador.leer(ruta);
        assertEquals(1, leidos.size()); // Solo la línea bien formateada
    }

    // ==== CSV ====

    @Test
    void testManejadorCSVGuardaYLee() throws PersistenciaException {
        IManejadorArchivos<List<Empleado>> manejador = new ManejadorCSV();
        String ruta = tempDir.resolve("empleados.csv").toString();

        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado(1, "Ana Test", "IT", 1000.0));
        empleados.add(new Empleado(2, "Carlos Test", "Ventas", 2000.0));

        manejador.guardar(empleados, ruta);
        assertTrue(new File(ruta).exists());

        List<Empleado> leidos = manejador.leer(ruta);
        assertEquals(2, leidos.size());
        assertEquals("Ana Test", leidos.get(0).getNombre());
        assertEquals("IT", leidos.get(0).getDepartamento());
        assertEquals(1000.0, leidos.get(0).getSalario());
    }

    @Test
    void testManejadorCSVLeerArchivoInexistente() {
        IManejadorArchivos<List<Empleado>> manejador = new ManejadorCSV();
        String ruta = tempDir.resolve("no_existe.csv").toString();

        assertThrows(PersistenciaException.class, () -> manejador.leer(ruta));
    }

    // ==== JSON ====

    @Test
    void testManejadorJSONGuardaYLee() throws PersistenciaException {
        IManejadorArchivos<Pedido> manejador = new ManejadorJSON();
        String ruta = tempDir.resolve("pedido.json").toString();

        List<Producto> productos = Arrays.asList(
                new Producto("ID1", "Item A", 1, 10.0),
                new Producto("ID2", "Item B", 3, 20.0)
        );
        Pedido pedido = new Pedido("P-001", "Cliente Test", productos, 70.0);

        manejador.guardar(pedido, ruta);
        assertTrue(new File(ruta).exists());

        Pedido leido = manejador.leer(ruta);
        assertNotNull(leido);
        assertEquals("P-001", leido.getNumeroPedido());
        assertEquals("Cliente Test", leido.getCliente());
        assertEquals(2, leido.getProductos().size());
        assertEquals(70.0, leido.getTotal());
    }

    @Test
    void testManejadorJSONLeerArchivoInexistente() {
        IManejadorArchivos<Pedido> manejador = new ManejadorJSON();
        String ruta = tempDir.resolve("no_existe.json").toString();

        assertThrows(PersistenciaException.class, () -> manejador.leer(ruta));
    }

    // ==== XML ====

    @Test
    void testManejadorXMLGuardaYLee() throws PersistenciaException {
        IManejadorArchivos<Pedido> manejador = new ManejadorXML();
        String ruta = tempDir.resolve("pedido.xml").toString();

        List<Producto> productos = Arrays.asList(
                new Producto("ID1", "XML Item", 1, 15.0),
                new Producto("ID2", "XML Item 2", 2, 25.0)
        );
        Pedido pedido = new Pedido("X-001", "XML Test", productos, 65.0);

        manejador.guardar(pedido, ruta);
        assertTrue(new File(ruta).exists());

        Pedido leido = manejador.leer(ruta);
        assertNotNull(leido);
        assertEquals("X-001", leido.getNumeroPedido());
        assertEquals("XML Test", leido.getCliente());
        assertEquals(2, leido.getProductos().size());
    }

    @Test
    void testManejadorXMLLeerArchivoInexistente() {
        IManejadorArchivos<Pedido> manejador = new ManejadorXML();
        String ruta = tempDir.resolve("no_existe.xml").toString();

        assertThrows(PersistenciaException.class, () -> manejador.leer(ruta));
    }

    // ==== Tests de Dominio (Libro setters y toString) ====

    @Test
    void testLibroConstructorVacioYSetters() {
        Libro libro = new Libro();
        libro.setId("L99");
        libro.setTitulo("Test Title");
        libro.setAutor("Test Author");
        libro.setAnioPublicacion(1999);

        assertEquals("L99", libro.getId());
        assertEquals("Test Title", libro.getTitulo());
        assertEquals("Test Author", libro.getAutor());
        assertEquals(1999, libro.getAnioPublicacion());
    }

    @Test
    void testLibroToString() {
        Libro libro = new Libro("L01", "Mi Libro", "Mi Autor", 2000);
        String str = libro.toString();
        assertTrue(str.contains("L01"));
        assertTrue(str.contains("Mi Libro"));
        assertTrue(str.contains("Mi Autor"));
        assertTrue(str.contains("2000"));
    }

    // ==== Tests de Dominio (Producto setters y toString) ====

    @Test
    void testProductoConstructorVacioYSetters() {
        Producto p = new Producto();
        p.setCodigo("C01");
        p.setNombre("Prod Test");
        p.setCantidad(5);
        p.setPrecioUnitario(99.99);

        assertEquals("C01", p.getCodigo());
        assertEquals("Prod Test", p.getNombre());
        assertEquals(5, p.getCantidad());
        assertEquals(99.99, p.getPrecioUnitario());
    }

    @Test
    void testProductoToString() {
        Producto p = new Producto("C01", "Prod", 2, 50.0);
        String str = p.toString();
        assertTrue(str.contains("C01"));
        assertTrue(str.contains("Prod"));
    }

    // ==== Tests de Dominio (Pedido setters y toString) ====

    @Test
    void testPedidoConstructorVacioYSetters() {
        Pedido pedido = new Pedido();
        pedido.setNumeroPedido("NP-01");
        pedido.setCliente("Cliente Set");
        pedido.setProductos(Arrays.asList(new Producto("X", "Y", 1, 1.0)));
        pedido.setTotal(1.0);

        assertEquals("NP-01", pedido.getNumeroPedido());
        assertEquals("Cliente Set", pedido.getCliente());
        assertEquals(1, pedido.getProductos().size());
        assertEquals(1.0, pedido.getTotal());
    }

    @Test
    void testPedidoToString() {
        Pedido pedido = new Pedido("ORD-1", "Cli", Arrays.asList(), 0.0);
        String str = pedido.toString();
        assertTrue(str.contains("ORD-1"));
        assertTrue(str.contains("Cli"));
    }

    // ==== Tests de Dominio (Empleado toString y herencia) ====

    @Test
    void testEmpleadoToString() {
        Empleado emp = new Empleado(1, "Juan", "Marketing", 3000.0);
        String str = emp.toString();
        assertTrue(str.contains("Juan"));
        assertTrue(str.contains("Marketing"));
    }

    @Test
    void testEmpleadoConstructorVacioYSetters() {
        Empleado emp = new Empleado();
        emp.setId(10);
        emp.setNombre("Maria");
        emp.setDepartamento("RRHH");
        emp.setSalario(2500.0);

        assertEquals(10, emp.getId());
        assertEquals("Maria", emp.getNombre());
        assertEquals("RRHH", emp.getDepartamento());
        assertEquals(2500.0, emp.getSalario());
    }
}
