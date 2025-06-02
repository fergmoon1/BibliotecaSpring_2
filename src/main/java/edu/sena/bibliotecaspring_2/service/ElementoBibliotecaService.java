package edu.sena.bibliotecaspring_2.service;

import edu.sena.bibliotecaspring_2.model.ElementoBiblioteca;
import edu.sena.bibliotecaspring_2.util.BibliotecaException;
import java.util.List;

public interface ElementoBibliotecaService {
    List<ElementoBiblioteca> obtenerTodos() throws BibliotecaException;
    void agregarElemento(ElementoBiblioteca elemento) throws BibliotecaException;
    ElementoBiblioteca obtenerPorId(int id) throws BibliotecaException;
    void actualizarElemento(ElementoBiblioteca elemento) throws BibliotecaException;
    void eliminarElemento(int id) throws BibliotecaException;
    List<ElementoBiblioteca> buscarPorTitulo(String titulo) throws BibliotecaException;
    List<ElementoBiblioteca> buscarPorAutor(String autor) throws BibliotecaException;
    List<ElementoBiblioteca> buscarPorGenero(String genero) throws BibliotecaException;
}