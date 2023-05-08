package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Multa;

public interface MultaService {

	List<Multa> getAllMultaes();
	Multa getMultaById(long id);
	void saveMulta(Multa multa);
	void deleteMultaById(long id);
}
