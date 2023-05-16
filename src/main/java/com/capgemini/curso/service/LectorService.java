package com.capgemini.curso.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.capgemini.curso.model.Lector;

public interface LectorService {

	List<Lector> getAllLectores();
	List<Lector> getLectoresQuePuedenPrestamo(LocalDate fechaPrestamo);
	Lector getLectorById(long id);
	void saveLector(Lector lector);

	Page<Lector> findAllPage(int pageNum, int pageSize, String sortField, String sortDirection);
	void deleteById(Long id);
	List<Object[]> countPrestamosByLector();

}
