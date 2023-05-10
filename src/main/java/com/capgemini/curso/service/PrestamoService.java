package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Prestamo;

public interface PrestamoService {
	
	List<Prestamo> getAllPrestamo();
	List<Prestamo> getAllActivePrestamo();
	Prestamo getPrestamoById(long id);

}
