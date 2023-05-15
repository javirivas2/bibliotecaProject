package com.capgemini.curso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.curso.model.Libro;
import com.capgemini.curso.repository.LibroRepository;

public class ReservaLibroServiceImp implements RerservaLibroService {
	@Autowired
	private LibroRepository libroRepository;
	@Override
	public List<Libro> getAllLibros() {
		// TODO Auto-generated method stub
		return null;
	}

}
