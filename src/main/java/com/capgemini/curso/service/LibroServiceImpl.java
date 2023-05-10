package com.capgemini.proyecto.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.capgemini.proyecto.model.Libro;

@Service
public class LibroServiceImpl implements LibroService {

	@Override
	public List<Libro> getAllLibros() {
		// TODO Auto-generated method stub
		return null;
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
