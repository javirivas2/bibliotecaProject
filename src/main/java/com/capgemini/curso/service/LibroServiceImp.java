package com.capgemini.curso.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Libro getLibroById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveLector(Libro libro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Libro> findAllPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
