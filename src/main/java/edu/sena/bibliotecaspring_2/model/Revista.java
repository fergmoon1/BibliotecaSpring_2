package edu.sena.bibliotecaspring_2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class Revista extends ElementoBiblioteca {
    private int numeroEdicion;
    private String categoria;
    private String editorial;
    private int numero;

    @Transient
    private String titulo;

    @Transient
    private String autor;

    @Transient
    private int anoPublicacion;

    @Transient
    private String tipo;

    // Constructor vacío
    public Revista() {}

    // Constructor con parámetros
    public Revista(int numeroEdicion, String categoria, String editorial, int numero, String titulo, String autor, int anoPublicacion) {
        super(titulo, autor, anoPublicacion);
        this.numeroEdicion = numeroEdicion;
        this.categoria = categoria;
        this.editorial = editorial;
        this.numero = numero;
    }

    // Getters y setters
    public int getNumeroEdicion() { return numeroEdicion; }
    public void setNumeroEdicion(int numeroEdicion) { this.numeroEdicion = numeroEdicion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
}