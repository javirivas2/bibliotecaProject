package com.capgemini.curso.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.repository.CopiaRepository;
import com.capgemini.curso.repository.LibroRepository;

@Service("copiaServiceImpl")
@Transactional
public class CopiaServiceImpl implements CopiaService {
	private static final Logger logger = LoggerFactory.getLogger(CopiaServiceImpl.class);
	@Autowired
	private CopiaRepository copiaRepository;
	@Autowired
	private LibroRepository libroRepository;

	@Override
	public List<Copia> getAllCopias() {
		return copiaRepository.findAll();
	}

	@Override
	public Copia getCopiaById(Long id) {
		Optional<Copia> optCopia = copiaRepository.findById(id);
		if(optCopia.isEmpty()) {
			throw new RuntimeException("No existe la copia con id: " + id);
		}
		
		return optCopia.get();
	}

	@Override
	public void saveCopia(Copia copia) {
		copiaRepository.save(copia);

	}

	@Override
	public void deleteById(Long id) {
		copiaRepository.deleteById(id);
	}

	@Override
	public Copia update(Copia copia) {
		return copiaRepository.save(copia);
	}

	@Override
	public List<Object[]> countCopiasByLibro() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Copia> findCopiasByLibro(Libro libro) {
		return copiaRepository.findByEjemplar(libro);
	}

}
