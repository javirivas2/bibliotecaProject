package com.capgemini.biblioteca.service;

import java.util.List;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.model.Libro;

public interface AutorService {

	List<Autor> getAllAutores();

	Autor getAutorById(long id);
}
