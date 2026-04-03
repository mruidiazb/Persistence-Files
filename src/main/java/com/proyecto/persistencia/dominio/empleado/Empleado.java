package com.proyecto.persistencia.dominio.empleado;

import com.opencsv.bean.CsvBindByName;

/**
 * Clase que representa a un Empleado.
 * Hereda propiedades y anotaciones OpenCSV de la clase Persona.
 */
public class Empleado extends Persona {

    @CsvBindByName(column = "DEPARTAMENTO")
    private String departamento;

    @CsvBindByName(column = "SALARIO")
    private double salario;

    public Empleado() {
        super();
    }

    public Empleado(int id, String nombre, String departamento, double salario) {
        super(id, nombre);
        this.departamento = departamento;
        this.salario = salario;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                ", salario=" + salario +
                '}';
    }
}
