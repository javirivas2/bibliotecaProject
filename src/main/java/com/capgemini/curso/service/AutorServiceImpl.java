package com.capgemini.curso.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.curso.model.Autor;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.repository.AutorRepository;

@Service("autorServiceImpl")
@Transactional
public class AutorServiceImpl implements AutorService {
	private static final Logger logger = LoggerFactory.getLogger(AutorServiceImpl.class);
	@Autowired
	private AutorRepository autorRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Autor> getAllAutores() {
		logger.info("AutorServiceImpl getAllAutores");
		List<Autor> autores = new ArrayList<>();
		autorRepository.findAll().forEach(autores::add);
		return autores;
	}

	@Override
	@Transactional(readOnly = true)
	public Autor getAutorById(Long id) {
		Optional<Autor> optAutor = autorRepository.findById(id);
		if(optAutor.isEmpty())
			throw new RuntimeException("No existe autor con id: " + id);
		
		return optAutor.get();
	}

	@Override
	public void saveAutor(Autor autor) {
		autorRepository.save(autor);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Autor update(Autor autor) {
		return autorRepository.save(autor);
	}

	@Override
	public List<Object[]> countLibrosByAutor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Libro> findLibrosByAutorId(Long autorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
