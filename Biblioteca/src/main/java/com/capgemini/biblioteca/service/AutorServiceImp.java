package com.capgemini.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.biblioteca.model.Autor;
import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.repository.AutorRepository;
@Service
public class AutorServiceImp implements AutorService {
	private static final Logger logger = LoggerFactory.getLogger(LibroServiceImp.class);
	@Autowired
	private AutorRepository autorRepository;
	@Override
	public List<Autor> getAllAutores() {
		logger.info("AutorServiceIml getAllAutores");
		List<Autor> autor = new ArrayList<>();
		autorRepository.findAll().forEach(autor::add);
		return autor;
	}
	@Override
	
	public Autor getAutorById(long id) {
		Optional<Autor> optAutor = autorRepository.findById(id);
		Autor autor = null;
		if(optAutor.isPresent()) {
			autor = optAutor.get();
		} else {
			System.out.println("el autor no se encuentra nro " + id);
		}
		return autor;
	}
	
}
