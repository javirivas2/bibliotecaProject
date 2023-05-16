package com.capgemini.curso.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.capgemini.curso.model.Libro;

public interface LibroService {

	List<Libro> getAllLibros();

	List<Libro> getLibrosDisponibles();

	Libro getLibroById(Long id);

	void saveLibro(Libro libro);

	Page<Libro> findAllPage(Pageable pageable);

	void deleteLibroById(Long id);
	
	void deleteLibro(Libro libro);

	

}
