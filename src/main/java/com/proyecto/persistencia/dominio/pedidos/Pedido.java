package com.proyecto.persistencia.dominio.pedidos;

import java.util.List;

/**
 * Clase que representa un Pedido.
 * Se utilizará para demostrar la lectura y escritura en archivos JSON y XML
 * debido a que es un objeto con estructura jerárquica (contiene listas de otros objetos).
 */
public class Pedido {

    private String numeroPedido;
    private String cliente;
    private List<Producto> productos;
    private double total;

    /**
     * Constructor vacío requerido principalmente para la deserialización (Jackson).
     */
    public Pedido() {
    }

    /**
     * Constructor con todos los campos.
     * 
     * @param numeroPedido Número/ID del pedido.
     * @param cliente Nombre del cliente.
     * @param productos Lista de productos incluidos en el pedido.
     * @param total Costo total del pedido.
     */
    public Pedido(String numeroPedido, String cliente, List<Producto> productos, double total) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.productos = productos;
        this.total = total;
    }

    // Getters y Setters

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido='" + numeroPedido + '\'' +
                ", cliente='" + cliente + '\'' +
                ", productos=" + productos +
                ", total=" + total +
                '}';
    }
}
