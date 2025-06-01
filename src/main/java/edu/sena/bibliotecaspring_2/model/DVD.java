package edu.sena.bibliotecaspring_2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class DVD extends ElementoBiblioteca {
    private int duracion;
    private String genero;

    @Transient
    private String titulo;

    @Transient
    private String autor;

    @Transient
    private int anoPublicacion;

    @Transient
    private String tipo;

    // Constructor vacío
    public DVD() {}

    // Constructor con parámetros
    public DVD(int duracion, String genero, String titulo, String autor, int anoPublicacion) {
        super(titulo, autor, anoPublicacion);
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters y setters
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}