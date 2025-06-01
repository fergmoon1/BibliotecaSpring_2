package edu.sena.bibliotecaspring_2.controller;

import edu.sena.bibliotecaspring_2.model.ElementoBiblioteca;
import edu.sena.bibliotecaspring_2.model.Libro;
import edu.sena.bibliotecaspring_2.model.Revista;
import edu.sena.bibliotecaspring_2.model.DVD;
import edu.sena.bibliotecaspring_2.service.ElementoBibliotecaService;
import edu.sena.bibliotecaspring_2.util.BibliotecaException;
import edu.sena.bibliotecaspring_2.util.Logger;
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

    @GetMapping("/")
    public String mostrarCatalogo(Model model) {
        Logger.logInfo("Intentando mostrar el catálogo...");
        try {
            List<ElementoBiblioteca> elementos = elementoService.obtenerTodos();
            Logger.logInfo("Elementos obtenidos: " + (elementos != null ? elementos.size() : "null"));
            model.addAttribute("elementos", elementos);
            model.addAttribute("error", null); // Limpiar error al recargar
            model.addAttribute("success", null); // Limpiar éxito al recargar
            return "index";
        } catch (BibliotecaException e) {
            Logger.logError("Error al mostrar catálogo: " + e.getMessage(), e);
            model.addAttribute("elementos", null); // Mostrar tabla vacía
            model.addAttribute("error", e.getMessage());
            return "index"; // Mostrar error en index.html
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
        Logger.logInfo("Intentando guardar elemento: " + titulo);
        try {
            ElementoBiblioteca elemento;
            switch (tipoElemento.toLowerCase()) {
                case "libro":
                    elemento = new Libro(isbn, numeroPaginas != null ? numeroPaginas : 0, genero, editorial, titulo, autor, anoPublicacion);
                    break;
                case "revista":
                    elemento = new Revista(numeroEdicion != null ? numeroEdicion : 0, categoria, editorial, numero != null ? numero : 0, titulo, autor, anoPublicacion);
                    break;
                case "dvd":
                    elemento = new DVD(duracion != null ? duracion : 0, genero, titulo, autor, anoPublicacion);
                    break;
                default:
                    throw new BibliotecaException("Tipo de elemento no válido");
            }
            elementoService.agregarElemento(elemento);
            model.addAttribute("success", "Elemento agregado con éxito");
        } catch (BibliotecaException e) {
            Logger.logError("Error al guardar elemento: " + e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/biblioteca/"; // Recargar catálogo con mensaje
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") int id, Model model) {
        Logger.logInfo("Intentando mostrar formulario de edición para elemento con ID: " + id);
        try {
            ElementoBiblioteca elemento = elementoService.obtenerPorId(id);
            model.addAttribute("elemento", elemento);
            return "editar";
        } catch (BibliotecaException e) {
            Logger.logError("Error al mostrar formulario de edición: " + e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            return "redirect:/biblioteca/"; // Redirigir a catálogo con error
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
        Logger.logInfo("Intentando actualizar elemento con ID: " + id);
        try {
            ElementoBiblioteca elemento;
            switch (tipoElemento.toLowerCase()) {
                case "libro":
                    elemento = new Libro(isbn, numeroPaginas != null ? numeroPaginas : 0, genero, editorial, titulo, autor, anoPublicacion);
                    break;
                case "revista":
                    elemento = new Revista(numeroEdicion != null ? numeroEdicion : 0, categoria, editorial, numero != null ? numero : 0, titulo, autor, anoPublicacion);
                    break;
                case "dvd":
                    elemento = new DVD(duracion != null ? duracion : 0, genero, titulo, autor, anoPublicacion);
                    break;
                default:
                    throw new BibliotecaException("Tipo de elemento no válido");
            }
            elemento.setId(id);
            elementoService.actualizarElemento(elemento);
            model.addAttribute("success", "Elemento actualizado con éxito");
        } catch (BibliotecaException e) {
            Logger.logError("Error al actualizar elemento: " + e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/biblioteca/"; // Recargar catálogo con mensaje
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarElemento(@PathVariable("id") int id, Model model) {
        Logger.logInfo("Intentando eliminar elemento con ID: " + id);
        try {
            elementoService.eliminarElemento(id);
            model.addAttribute("success", "Elemento eliminado con éxito");
        } catch (BibliotecaException e) {
            Logger.logError("Error al eliminar elemento: " + e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/biblioteca/"; // Recargar catálogo con mensaje
    }

    @PostMapping("/buscar")
    public String buscarElementos(@RequestParam String criterio, @RequestParam String tipoBusqueda, Model model) {
        Logger.logInfo("Iniciando búsqueda - Criterio: " + criterio + ", Tipo de búsqueda: " + tipoBusqueda);
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
            Logger.logInfo("Resultados encontrados: " + (resultados != null ? resultados.size() : "null"));
            model.addAttribute("elementos", resultados);
            model.addAttribute("error", null); // Limpiar error
            model.addAttribute("success", null); // Limpiar éxito
            return "index";
        } catch (BibliotecaException e) {
            Logger.logError("Error al buscar elementos | Causa: " + e.getMessage(), e);
            model.addAttribute("elementos", null); // Mostrar tabla vacía
            model.addAttribute("error", e.getMessage());
            return "index"; // Mostrar error en index.html
        }
    }

    @GetMapping("/ayuda")
    public String mostrarAyuda() {
        return "ayuda";
    }
}