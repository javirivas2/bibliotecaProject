package com.capgemini.curso.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.capgemini.curso.model.Lector;

public interface LectorService {

	List<Lector> getAllLectores();
	Lector getLectorById(long id);
	void saveLector(Lector lector);

	void devolver(long idLector, long idPrestamo, LocalDate fechaAct);
	void prestar(long idLector, long idLibro, LocalDate fechaAct);
	Page<Lector> findAllPage(Pageable pageable);

}
