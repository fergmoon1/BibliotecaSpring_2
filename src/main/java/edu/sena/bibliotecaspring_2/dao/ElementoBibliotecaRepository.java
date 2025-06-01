package edu.sena.bibliotecaspring_2.dao;

import edu.sena.bibliotecaspring_2.model.ElementoBiblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementoBibliotecaRepository extends JpaRepository<ElementoBiblioteca, Integer> {
    @Query("SELECT e FROM ElementoBiblioteca e WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<ElementoBiblioteca> findByTituloContainingIgnoreCase(@Param("titulo") String titulo);

    @Query("SELECT e FROM ElementoBiblioteca e WHERE LOWER(e.autor) LIKE LOWER(CONCAT('%', :autor, '%'))")
    List<ElementoBiblioteca> findByAutorContainingIgnoreCase(@Param("autor") String autor);

    @Query("SELECT e FROM ElementoBiblioteca e JOIN Libro l ON e.id = l.id WHERE LOWER(l.genero) LIKE LOWER(CONCAT('%', :genero, '%'))")
    List<ElementoBiblioteca> findByGeneroInLibro(@Param("genero") String genero);

    @Query("SELECT e FROM ElementoBiblioteca e JOIN DVD d ON e.id = d.id WHERE LOWER(d.genero) LIKE LOWER(CONCAT('%', :genero, '%'))")
    List<ElementoBiblioteca> findByGeneroInDVD(@Param("genero") String genero);
}