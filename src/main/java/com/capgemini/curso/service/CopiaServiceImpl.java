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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Copia getCopiaById(Long id) {
		Optional<Copia> optionalCopia = this.copiaRepository.findById(id);
		Copia copia = null;
		if (optionalCopia.isPresent()) {
			copia = optionalCopia.get();
		} else {
			System.out.println("La copia no se encuentra nro " + id);
		}
		return copia;
	}

	@Override
	public void saveCopia(Copia copia) {
		copiaRepository.save(copia);

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Copia update(Copia copia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> countCopiasByLibro() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Copia> findCopiasByLibroId(Long copiaId) {
		// TODO Auto-generated method stub
		return null;
	}

}
