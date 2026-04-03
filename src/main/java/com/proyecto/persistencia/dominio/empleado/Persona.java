package com.proyecto.persistencia.dominio.empleado;

import com.opencsv.bean.CsvBindByName;

/**
 * Clase base de la que hereda Empleado.
 * Demuestra el uso de herencia en el mapeo cruzado con CSV.
 */
public class Persona {

    @CsvBindByName(column = "ID")
    protected int id;

    @CsvBindByName(column = "NOMBRE COMPLETO")
    protected String nombre;

    public Persona() {
    }

    public Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
