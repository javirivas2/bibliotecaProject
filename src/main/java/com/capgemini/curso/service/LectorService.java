package com.capgemini.curso.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.capgemini.curso.model.Lector;

public interface LectorService {

	List<Lector> getAllLectores();
	Lector getLectorById(long id);
	void saveLector(Lector lector);

	void devolver(long idLector, long idPrestamo, LocalDate fechaAct);
	void prestar(long idLector, long idLibro, LocalDate fechaAct);
	Page<Lector> findAllPage(int pageNum, int pageSize, String sortField, String sortDirection);

}
