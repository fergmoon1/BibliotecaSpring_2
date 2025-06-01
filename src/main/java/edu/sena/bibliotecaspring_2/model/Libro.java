package edu.sena.bibliotecaspring_2.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "libro")
public class Libro extends ElementoBiblioteca {

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "numero_paginas")
    private int numeroPaginas;

    @Column(name = "genero")
    private String genero;

    @Column(name = "editorial")
    private String editorial;

    // Constructores
    public Libro() {}

    public Libro(String isbn, int numeroPaginas, String genero, String editorial, String titulo, String autor, int anoPublicacion) {
        super(titulo, autor, anoPublicacion);
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
    }

    // Getters y Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
}