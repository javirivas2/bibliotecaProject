package com.capgemini.proyecto.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.capgemini.proyecto.model.Lector;
import com.capgemini.proyecto.model.Libro;

public interface LibroService {

	List<Libro> getAllLibros();
	List<Libro> getLibrosDisponibles();

	Libro getLibroById(Long id);

	void saveLector(Libro libro);
//
//	void devolver(Long idLector, Long idPrestamo, LocalDate fechaDevolucion);
//
//	void prestar(Long idLector, Long idLibro, LocalDate fechaPrestamo);

	Page<Libro> findAllPage(Pageable pageable);

}
