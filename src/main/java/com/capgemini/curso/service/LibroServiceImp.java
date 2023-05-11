package com.capgemini.curso.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.curso.model.Libro;
import com.capgemini.curso.repository.LibroRepository;

@Service
public class LibroServiceImp implements LibroService {
	
	@Autowired
	private LibroRepository libroRepository;

	@Override
	public List<Libro> getAllLibros() {
		return libroRepository.findAll();
	}

	@Override
	public List<Libro> getLibrosDisponibles() {
		List<Libro> libros = getAllLibros();
		List<Libro> librosAct = new ArrayList<>();
		
		for (Libro libro : libros) {
			if(!libro.getEjemplaresDisponibles().isEmpty()) {
				librosAct.add(libro);
			}
		}		
		
		return librosAct;
	}

}
