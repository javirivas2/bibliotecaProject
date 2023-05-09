package com.capgemini.biblioteca.service;

import java.util.List;

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;

public interface LibroService {
	List<Libro> getAllLibros();

	Libro getLibroById(long id);

	void saveLibro(Lector lector);

////	void devolver(long idLector, long idPrestamo, LocalDate fechaAct);
//
//	void prestar(long idLector, long idLibro, LocalDate fechaAct);

}
