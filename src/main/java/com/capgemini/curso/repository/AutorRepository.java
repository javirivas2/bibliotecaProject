package com.capgemini.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.proyecto.model.Autor;
import com.capgemini.proyecto.model.Libro;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

	@Query("SELECT a, COUNT(l) FROM Autor a LEFT JOIN a.obras l GROUP BY a")
	List<Object[]> countLibrosByAutor();

	@Query("SELECT a.obras FROM Autor a WHERE a.id = :autorId")
	List<Libro> findLibrosByAutorId(@Param("autorId") Long autorId);
}
