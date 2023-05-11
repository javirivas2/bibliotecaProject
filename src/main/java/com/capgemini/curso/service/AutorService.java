package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Autor;
import com.capgemini.curso.model.Libro;


public interface AutorService {	

	List<Autor> getAllAutores();

	Autor getAutorById(Long id);

	void saveAutor(Autor autor);

	void deleteById(Long id);

	Autor update(Autor autor);

	List<Object[]> countLibrosByAutor();

	List<Libro> findLibrosByAutorId(Long autorId);

}
