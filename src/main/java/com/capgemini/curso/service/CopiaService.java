package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.Libro;

public interface CopiaService {

	List<Copia> getAllCopias();

	Copia getCopiaById(Long id);

	void saveCopia(Copia copia);

	void deleteById(Long id);

	void deleteCopia(Copia copia);

	Copia update(Copia copia);

	List<Object[]> countCopiasByLibro();

	List<Copia> findCopiasByLibro(Libro libro);
	List<Copia> findCopiasByLibroId(Long copiaId);

	void deleteCopiasPorLibro(Long libroId);
}
