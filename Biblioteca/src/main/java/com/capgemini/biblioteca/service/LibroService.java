package com.capgemini.biblioteca.service;

import java.util.List;

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;

public interface LibroService {
	List<Libro> getAllLibros();

	Libro deleteLibroById(long id);

	Libro getLibroById(long id);

	void saveLibro(Libro libro);

}
