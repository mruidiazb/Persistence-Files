package com.proyecto.persistencia.dominio.biblioteca;

import java.io.Serializable;

/**
 * Clase que representa a un Libro en la biblioteca.
 * Se utilizará principalmente para demostrar la lectura y escritura 
 * en archivos de texto plano (TXT).
 */
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String titulo;
    private String autor;
    private int anioPublicacion;

    /**
     * Constructor vacío requerido por algunos frameworks o instanciación genérica.
     */
    public Libro() {
    }

    /**
     * Constructor con parámetros.
     * 
     * @param id Identificador único del libro.
     * @param titulo Título del libro.
     * @param autor Autor del libro.
     * @param anioPublicacion Año de publicación.
     */
    public Libro(String id, String titulo, String autor, int anioPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                '}';
    }
}
