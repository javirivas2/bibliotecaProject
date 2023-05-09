package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Libro;

public interface LibroService {

	List<Libro> getAllLibros();
	List<Libro> getLibrosDisponibles();
}
