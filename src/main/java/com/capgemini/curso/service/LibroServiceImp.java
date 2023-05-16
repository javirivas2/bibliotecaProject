package com.capgemini.curso.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.repository.CopiaRepository;
import com.capgemini.curso.repository.LibroRepository;

@Service
public class LibroServiceImp implements LibroService {
	
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private CopiaRepository copiaRepository;

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
		Optional<Libro> optionalLibro = this.libroRepository.findById(id);
		Libro libro = null;
		if (optionalLibro.isPresent()) {
			libro = optionalLibro.get();
		} else {
			System.out.println("el libro no se encuentra nro " + id);
		}
		return libro;
	}

	@Override
	public void saveLibro(Libro libro) {
		this.libroRepository.save(libro);
	}
	@Override
	public void deleteLibroById(long id) {
		List<Copia> optCopia = copiaRepository.findByEjemplar(getLibroById(id));
		if (optCopia.isEmpty()) {
		libroRepository.deleteById(id);
		} else {
			throw new RuntimeException("El autor con id: " + id + " tiene copias disponibles");
		}

	}

	@Override
	public Page<Libro> findAllPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLibro(Libro libro) {
		libroRepository.delete(libro);
		
	}

}
