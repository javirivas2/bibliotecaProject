package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Multa;

public interface MultaService {

	List<Multa> getAllMultas();

	Multa getMultaById(Long id);

	void saveMulta(Multa multa);

	void deleteMultaById(Long id);

}
