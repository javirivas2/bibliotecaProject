package com.capgemini.biblioteca.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.biblioteca.model.Lector;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.repository.LibroRepository;

@Service
public class LibroServiceImp implements LibroService {
	private static final Logger logger = LoggerFactory.getLogger(LibroServiceImp.class);
	@Autowired
	private LibroRepository libroRepository;

	@Override
	public List<Libro> getAllLibros() {
		logger.info("LibroServiceIml getAllLibros");
		List<Libro> libros = new ArrayList<>();
		libroRepository.findAll().forEach(libros::add);
		return libros;
	}

	@Override
	public Libro getLibroById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveLibro(Lector lector) {
		// TODO Auto-generated method stub

	}

}
