package com.capgemini.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.repository.LectorRepository;

@Service
public class LectorServiceImp implements LectorService {

	@Autowired
	private LectorRepository lectorRepository;

	@Override
	public List<Lector> getAllLectores() {
		return lectorRepository.findAll();
	}

	@Override
	public Lector getLectorById(long id) {
		Optional<Lector> optionalLector = lectorRepository.findById(id);
		if (optionalLector.isPresent()) {
			return optionalLector.get();
		} else {
			throw new RuntimeException("No se encuentra lector con id: " + id);
		}

	}

	@Override
	public void saveLector(Lector lector) {
		lectorRepository.save(lector);
	}

}
