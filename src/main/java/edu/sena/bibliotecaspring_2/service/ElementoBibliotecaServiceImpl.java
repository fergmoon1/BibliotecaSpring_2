package edu.sena.bibliotecaspring_2.service;

import edu.sena.bibliotecaspring_2.dao.ElementoBibliotecaRepository;
import edu.sena.bibliotecaspring_2.model.ElementoBiblioteca;
import edu.sena.bibliotecaspring_2.model.Libro;
import edu.sena.bibliotecaspring_2.model.Revista;
import edu.sena.bibliotecaspring_2.model.DVD;
import edu.sena.bibliotecaspring_2.util.BibliotecaException;
import edu.sena.bibliotecaspring_2.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ElementoBibliotecaServiceImpl implements ElementoBibliotecaService {

    @Autowired
    private ElementoBibliotecaRepository elementoRepository;

    @Override
    public List<ElementoBiblioteca> obtenerTodos() throws BibliotecaException {
        Logger.logInfo("Intentando obtener todos los elementos de la base de datos...");
        List<ElementoBiblioteca> elementos = elementoRepository.findAll();
        if (elementos.isEmpty()) {
            Logger.logInfo("No se encontraron elementos en la base de datos.");
            throw new BibliotecaException("El catálogo está vacío");
        }
        Logger.logInfo("Elementos obtenidos: " + elementos.size());
        return elementos;
    }

    @Override
    public void agregarElemento(ElementoBiblioteca elemento) throws BibliotecaException {
        Logger.logInfo("Intentando guardar elemento: " + elemento.getTitulo());
        validarElemento(elemento);
        elemento.setTipo(elemento.getClass().getSimpleName());
        elementoRepository.save(elemento);
        Logger.logInfo("Elemento guardado con éxito: " + elemento.getId());
    }

    @Override
    public ElementoBiblioteca obtenerPorId(int id) throws BibliotecaException {
        Logger.logInfo("Buscando elemento con ID: " + id);
        Optional<ElementoBiblioteca> elementoOpt = elementoRepository.findById(id);
        if (!elementoOpt.isPresent()) {
            Logger.logInfo("No se encontró elemento con ID: " + id);
            throw new BibliotecaException("No se encontró elemento con ID: " + id);
        }
        Logger.logInfo("Elemento encontrado: " + elementoOpt.get().getTitulo());
        return elementoOpt.get();
    }

    @Override
    public void actualizarElemento(ElementoBiblioteca elementoActualizado) throws BibliotecaException {
        Logger.logInfo("Intentando actualizar elemento con ID: " + elementoActualizado.getId());
        Optional<ElementoBiblioteca> elementoExistenteOpt = elementoRepository.findById(elementoActualizado.getId());
        if (!elementoExistenteOpt.isPresent()) {
            Logger.logInfo("No se encontró elemento con ID: " + elementoActualizado.getId());
            throw new BibliotecaException("No se encontró elemento con ID: " + elementoActualizado.getId());
        }
        ElementoBiblioteca elementoExistente = elementoExistenteOpt.get();
        elementoExistente.setTitulo(elementoActualizado.getTitulo());
        elementoExistente.setAutor(elementoActualizado.getAutor());
        elementoExistente.setAnoPublicacion(elementoActualizado.getAnoPublicacion());

        if (elementoExistente instanceof Libro && elementoActualizado instanceof Libro) {
            Libro libroExistente = (Libro) elementoExistente;
            Libro libroActualizado = (Libro) elementoActualizado;
            libroExistente.setIsbn(libroActualizado.getIsbn());
            libroExistente.setNumeroPaginas(libroActualizado.getNumeroPaginas());
            libroExistente.setGenero(libroActualizado.getGenero());
            libroExistente.setEditorial(libroActualizado.getEditorial());
        } else if (elementoExistente instanceof Revista && elementoActualizado instanceof Revista) {
            Revista revistaExistente = (Revista) elementoExistente;
            Revista revistaActualizada = (Revista) elementoActualizado;
            revistaExistente.setNumeroEdicion(revistaActualizada.getNumeroEdicion());
            revistaExistente.setCategoria(revistaActualizada.getCategoria());
            revistaExistente.setEditorial(revistaActualizada.getEditorial());
            revistaExistente.setNumero(revistaActualizada.getNumero());
        } else if (elementoExistente instanceof DVD && elementoActualizado instanceof DVD) {
            DVD dvdExistente = (DVD) elementoExistente;
            DVD dvdActualizado = (DVD) elementoActualizado;
            dvdExistente.setDuracion(dvdActualizado.getDuracion());
            dvdExistente.setGenero(dvdActualizado.getGenero());
        } else {
            throw new BibliotecaException("Tipo de elemento no coincide para actualización");
        }

        elementoRepository.save(elementoExistente);
        Logger.logInfo("Elemento actualizado con éxito: " + elementoExistente.getId());
    }

    @Override
    public void eliminarElemento(int id) throws BibliotecaException {
        Logger.logInfo("Intentando eliminar elemento con ID: " + id);
        if (!elementoRepository.existsById(id)) {
            Logger.logInfo("No se encontró elemento con ID: " + id);
            throw new BibliotecaException("No se encontró elemento con ID: " + id);
        }
        elementoRepository.deleteById(id);
        Logger.logInfo("Elemento eliminado con éxito: " + id);
    }

    @Override
    public List<ElementoBiblioteca> buscarPorTitulo(String titulo) throws BibliotecaException {
        Logger.logInfo("Buscando elementos por título: " + titulo);
        List<ElementoBiblioteca> resultados = elementoRepository.findByTituloContainingIgnoreCase(titulo);
        if (resultados.isEmpty()) {
            Logger.logInfo("No se encontraron elementos con el título: " + titulo);
            throw new BibliotecaException("No se encontraron elementos con el título: " + titulo);
        }
        Logger.logInfo("Elementos encontrados: " + resultados.size());
        return resultados;
    }

    @Override
    public List<ElementoBiblioteca> buscarPorAutor(String autor) throws BibliotecaException {
        Logger.logInfo("Buscando elementos por autor: " + autor);
        List<ElementoBiblioteca> resultados = elementoRepository.findByAutorContainingIgnoreCase(autor);
        if (resultados.isEmpty()) {
            Logger.logInfo("No se encontraron elementos con el autor: " + autor);
            throw new BibliotecaException("No se encontraron elementos con el autor: " + autor);
        }
        Logger.logInfo("Elementos encontrados: " + resultados.size());
        return resultados;
    }

    @Override
    public List<ElementoBiblioteca> buscarPorGenero(String genero) throws BibliotecaException {
        Logger.logInfo("Buscando elementos por género: " + genero);
        List<ElementoBiblioteca> resultadosLibro = elementoRepository.findByGeneroInLibro(genero);
        List<ElementoBiblioteca> resultadosDVD = elementoRepository.findByGeneroInDVD(genero);
        List<ElementoBiblioteca> resultados = new ArrayList<>();
        resultados.addAll(resultadosLibro);
        resultados.addAll(resultadosDVD);
        if (resultados.isEmpty()) {
            Logger.logInfo("No se encontraron elementos con el género: " + genero);
            throw new BibliotecaException("No se encontraron elementos con el género: " + genero);
        }
        Logger.logInfo("Elementos encontrados: " + resultados.size());
        return resultados;
    }

    private void validarElemento(ElementoBiblioteca elemento) throws BibliotecaException {
        if (elemento.getTitulo() == null || elemento.getTitulo().trim().isEmpty()) {
            throw new BibliotecaException("El título es obligatorio");
        }
        if (elemento.getAutor() == null || elemento.getAutor().trim().isEmpty()) {
            throw new BibliotecaException("El autor es obligatorio");
        }
        if (elemento.getAnoPublicacion() <= 0) {
            throw new BibliotecaException("El año de publicación debe ser mayor a 0");
        }
    }
}