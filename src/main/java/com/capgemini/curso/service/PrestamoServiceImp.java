package com.capgemini.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.curso.model.Prestamo;
import com.capgemini.curso.repository.PrestamoRepository;

@Service
public class PrestamoServiceImp implements PrestamoService {

	@Autowired
	private PrestamoRepository prestamoRepository;

	@Override
	public List<Prestamo> getAllPrestamo() {
		return prestamoRepository.findAll();
	}

	@Override
	public List<Prestamo> getAllActivePrestamo() {
		return prestamoRepository.findByActivo(true);
	}
	
	@Override
	public Prestamo getPrestamoById(long id) {		
		Optional<Prestamo> optionalPrestamo = prestamoRepository.findById(id);
		if (optionalPrestamo.isPresent()) {
			return optionalPrestamo.get();
		} else {
			throw new RuntimeException("No se encuentra el prestamo con id: " + id);
		}
	}

}
