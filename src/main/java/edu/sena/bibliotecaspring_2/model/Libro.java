package edu.sena.bibliotecaspring_2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class Libro extends ElementoBiblioteca {
    private String isbn;
    private int numeroPaginas;
    private String genero;
    private String editorial;

    @Transient
    private String titulo;

    @Transient
    private String autor;

    @Transient
    private int anoPublicacion;

    @Transient
    private String tipo;

    // Constructor vacío
    public Libro() {}

    // Constructor con parámetros
    public Libro(String isbn, int numeroPaginas, String genero, String editorial, String titulo, String autor, int anoPublicacion) {
        super(titulo, autor, anoPublicacion);
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.genero = genero;
        this.editorial = editorial;
    }

    // Getters y setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(int numeroPaginas) { this.numeroPaginas = numeroPaginas; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
}