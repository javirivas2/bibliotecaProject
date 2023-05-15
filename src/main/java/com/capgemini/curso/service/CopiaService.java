package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Copia;

public interface CopiaService {

	List<Copia> getAllCopias();

	Copia getCopiaById(Long id);

	void saveCopia(Copia copia);

	void deleteById(Long id);

	Copia update(Copia copia);

	List<Object[]> countCopiasByLibro();

	List<Copia> findCopiasByLibroId(Long copiaId);
}
