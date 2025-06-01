package edu.sena.bibliotecaspring_2.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "dvd")
public class DVD extends ElementoBiblioteca {

    @Column(name = "duracion")
    private int duracion;

    @Column(name = "genero")
    private String genero;

    // Constructores
    public DVD() {}

    public DVD(int duracion, String genero, String titulo, String autor, int anoPublicacion) {
        super(titulo, autor, anoPublicacion);
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters y Setters
    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}