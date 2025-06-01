package edu.sena.bibliotecaspring_2.controller;

import edu.sena.bibliotecaspring_2.model.ElementoBiblioteca;
import edu.sena.bibliotecaspring_2.model.Libro;
import edu.sena.bibliotecaspring_2.model.Revista;
import edu.sena.bibliotecaspring_2.model.DVD;
import edu.sena.bibliotecaspring_2.service.ElementoBibliotecaService;
import edu.sena.bibliotecaspring_2.util.BibliotecaException;
import javax.persistence.PersistenceException; // Verifica que esta línea no esté marcada en rojo
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private ElementoBibliotecaService elementoService;

    private static final Logger logger = LoggerFactory.getLogger(BibliotecaController.class);

    @GetMapping("/")
    public String mostrarCatalogo(Model model) {
        logger.info("Intentando mostrar el catálogo...");
        try {
            List<ElementoBiblioteca> elementos = elementoService.obtenerTodos();
            logger.info("Elementos obtenidos: " + (elementos != null ? elementos.size() : "null"));
            model.addAttribute("elementos", elementos);
            model.addAttribute("error", null);
            model.addAttribute("success", null);
            return "index";
        } catch (Exception e) {
            logger.error("Error al mostrar catálogo: " + e.getMessage(), e);
            model.addAttribute("elementos", null);
            model.addAttribute("error", "Error al mostrar el catálogo: " + e.getMessage());
            return "index";
        }
    }

    @GetMapping("/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("elemento", new Libro());
        return "agregar";
    }

    @PostMapping("/guardar")
    public String guardarElemento(
            @RequestParam String tipoElemento,
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam int anoPublicacion,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer numeroPaginas,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) Integer numeroEdicion,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Integer numero,
            @RequestParam(required = false) Integer duracion,
            Model model) {
        logger.info("Intentando guardar elemento: " + titulo);
        try {
            ElementoBiblioteca elemento;
            switch (tipoElemento.toLowerCase()) {
                case "libro":
                    elemento = new Libro(isbn, numeroPaginas != null ? numeroPaginas : 0, genero, editorial, titulo, autor, anoPublicacion);
                    elemento.setTipo("Libro");
                    break;
                case "revista":
                    elemento = new Revista(numeroEdicion != null ? numeroEdicion : 0, categoria, editorial, numero != null ? numero : 0, titulo, autor, anoPublicacion);
                    elemento.setTipo("Revista");
                    break;
                case "dvd":
                    elemento = new DVD(duracion != null ? duracion : 0, genero, titulo, autor, anoPublicacion);
                    elemento.setTipo("DVD");
                    break;
                default:
                    throw new BibliotecaException("Tipo de elemento no válido");
            }
            elementoService.agregarElemento(elemento);
            model.addAttribute("success", "Elemento agregado con éxito");
        } catch (Exception e) {
            logger.error("Error al guardar elemento: " + e.getMessage(), e);
            model.addAttribute("error", "Error al guardar el elemento: " + e.getMessage());
        }
        return "redirect:/biblioteca/";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") int id, Model model) {
        logger.info("Intentando mostrar formulario de edición para elemento con ID: " + id);
        try {
            if (elementoService == null) {
                logger.error("ElementoBibliotecaService no está inicializado");
                throw new IllegalStateException("Servicio de elementos no inicializado");
            }
            logger.info("Obteniendo elemento con ID: " + id);
            ElementoBiblioteca elemento = elementoService.obtenerPorId(id);
            if (elemento == null) {
                logger.error("Elemento no encontrado con ID: " + id);
                throw new BibliotecaException("Elemento no encontrado con ID: " + id);
            }
            logger.info("Elemento encontrado: ID=" + elemento.getId() + ", Título=" + elemento.getTitulo() + ", Tipo=" + elemento.getTipo() + ", Clase=" + elemento.getClass().getSimpleName());
            if (elemento.getTipo() == null) {
                logger.error("El tipo del elemento es null para ID: " + id);
                throw new BibliotecaException("El tipo del elemento no está definido para ID: " + id);
            }
            if ("Libro".equalsIgnoreCase(elemento.getTipo())) {
                if (!(elemento instanceof Libro)) {
                    logger.error("El elemento con ID " + id + " debería ser un Libro pero no lo es: " + elemento.getClass().getSimpleName());
                    throw new BibliotecaException("Error de tipo: el elemento no es un Libro");
                }
                logger.info("Elemento es un Libro: " + elemento.getTitulo());
            } else if ("Revista".equalsIgnoreCase(elemento.getTipo())) {
                if (!(elemento instanceof Revista)) {
                    logger.error("El elemento con ID " + id + " debería ser una Revista pero no lo es: " + elemento.getClass().getSimpleName());
                    throw new BibliotecaException("Error de tipo: el elemento no es una Revista");
                }
                logger.info("Elemento es una Revista: " + elemento.getTitulo());
            } else if ("DVD".equalsIgnoreCase(elemento.getTipo())) {
                if (!(elemento instanceof DVD)) {
                    logger.error("El elemento con ID " + id + " debería ser un DVD pero no lo es: " + elemento.getClass().getSimpleName());
                    throw new BibliotecaException("Error de tipo: el elemento no es un DVD");
                }
                logger.info("Elemento es un DVD: " + elemento.getTitulo());
            } else {
                logger.error("Tipo de elemento no soportado para edición: " + elemento.getTipo());
                throw new BibliotecaException("Tipo de elemento no soportado para edición: " + elemento.getTipo());
            }
            model.addAttribute("elemento", elemento);
            return "editar";
        } catch (PersistenceException e) { // Verifica que esta línea no esté marcada en rojo
            logger.error("Error de persistencia al intentar obtener el elemento con ID " + id + ": " + e.getMessage(), e);
            model.addAttribute("error", "Error de base de datos al cargar el elemento: " + e.getMessage());
            return "redirect:/biblioteca/";
        } catch (Exception e) {
            logger.error("Error inesperado al mostrar formulario de edición: " + e.getMessage(), e);
            model.addAttribute("error", "Error al cargar el formulario de edición: " + e.getMessage());
            return "redirect:/biblioteca/";
        }
    }

    @PostMapping("/actualizar")
    public String actualizarElemento(
            @RequestParam int id,
            @RequestParam String tipoElemento,
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam int anoPublicacion,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer numeroPaginas,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) Integer numeroEdicion,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Integer numero,
            @RequestParam(required = false) Integer duracion,
            Model model) {
        logger.info("Intentando actualizar elemento con ID: " + id);
        try {
            ElementoBiblioteca elemento;
            switch (tipoElemento.toLowerCase()) {
                case "libro":
                    elemento = new Libro(isbn, numeroPaginas != null ? numeroPaginas : 0, genero, editorial, titulo, autor, anoPublicacion);
                    elemento.setTipo("Libro");
                    break;
                case "revista":
                    elemento = new Revista(numeroEdicion != null ? numeroEdicion : 0, categoria, editorial, numero != null ? numero : 0, titulo, autor, anoPublicacion);
                    elemento.setTipo("Revista");
                    break;
                case "dvd":
                    elemento = new DVD(duracion != null ? duracion : 0, genero, titulo, autor, anoPublicacion);
                    elemento.setTipo("DVD");
                    break;
                default:
                    throw new BibliotecaException("Tipo de elemento no válido");
            }
            elemento.setId(id);
            elementoService.actualizarElemento(elemento);
            model.addAttribute("success", "Elemento actualizado con éxito");
        } catch (Exception e) {
            logger.error("Error al actualizar elemento: " + e.getMessage(), e);
            model.addAttribute("error", "Error al actualizar el elemento: " + e.getMessage());
        }
        return "redirect:/biblioteca/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarElemento(@PathVariable("id") int id, Model model) {
        logger.info("Intentando eliminar elemento con ID: " + id);
        try {
            elementoService.eliminarElemento(id);
            model.addAttribute("success", "Elemento eliminado con éxito");
        } catch (Exception e) {
            logger.error("Error al eliminar elemento: " + e.getMessage(), e);
            model.addAttribute("error", "Error al eliminar el elemento: " + e.getMessage());
        }
        return "redirect:/biblioteca/";
    }

    @PostMapping("/buscar")
    public String buscarElementos(@RequestParam String criterio, @RequestParam String tipoBusqueda, Model model) {
        logger.info("Iniciando búsqueda - Criterio: " + criterio + ", Tipo de búsqueda: " + tipoBusqueda);
        try {
            List<ElementoBiblioteca> resultados;
            switch (tipoBusqueda.toLowerCase()) {
                case "titulo":
                    resultados = elementoService.buscarPorTitulo(criterio);
                    break;
                case "autor":
                    resultados = elementoService.buscarPorAutor(criterio);
                    break;
                case "genero":
                    resultados = elementoService.buscarPorGenero(criterio);
                    break;
                default:
                    throw new BibliotecaException("Tipo de búsqueda no válido");
            }
            logger.info("Resultados encontrados: " + (resultados != null ? resultados.size() : "null"));
            model.addAttribute("elementos", resultados);
            model.addAttribute("error", null);
            model.addAttribute("success", null);
            return "index";
        } catch (Exception e) {
            logger.error("Error al buscar elementos | Causa: " + e.getMessage(), e);
            model.addAttribute("elementos", null);
            model.addAttribute("error", "Error al buscar elementos: " + e.getMessage());
            return "index";
        }
    }

    @GetMapping("/ayuda")
    public String mostrarAyuda() {
        return "ayuda";
    }
}