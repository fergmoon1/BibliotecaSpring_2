package edu.sena.bibliotecaspring_2.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "revista")
public class Revista extends ElementoBiblioteca {

    @Column(name = "numero_edicion")
    private int numeroEdicion;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "editorial")
    private String editorial;

    @Column(name = "numero")
    private int numero;

    // Constructores
    public Revista() {}

    public Revista(int numeroEdicion, String categoria, String editorial, int numero, String titulo, String autor, int anoPublicacion) {
        super(titulo, autor, anoPublicacion);
        this.numeroEdicion = numeroEdicion;
        this.categoria = categoria;
        this.editorial = editorial;
        this.numero = numero;
    }

    // Getters y Setters
    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}