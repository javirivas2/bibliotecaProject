package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Lector;

public interface LectorService {

	List<Lector> getAllLectores();
	Lector getLectorById(long id);
	void saveLector(Lector lector);
}
