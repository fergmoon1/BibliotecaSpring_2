package edu.sena.bibliotecaspring_2.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ElementoBiblioteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacion;
    private String tipo;

    // Constructor vacío
    public ElementoBiblioteca() {}

    // Constructor con parámetros
    public ElementoBiblioteca(String titulo, String autor, int anoPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacion = anoPublicacion;
        this.tipo = this.getClass().getSimpleName();
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getAnoPublicacion() { return anoPublicacion; }
    public void setAnoPublicacion(int anoPublicacion) { this.anoPublicacion = anoPublicacion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}