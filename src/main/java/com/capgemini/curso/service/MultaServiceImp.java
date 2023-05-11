package com.capgemini.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.curso.model.Multa;
import com.capgemini.curso.repository.MultaRepository;

@Service
public class MultaServiceImp implements MultaService {

	@Autowired
	private MultaRepository multaRepository;

	@Override
	public List<Multa> getAllMultas() {
		return multaRepository.findAll();
	}

	@Override
	public Multa getMultaById(Long id) {
		Optional<Multa> optionalMulta = multaRepository.findById(id);
		if (optionalMulta.isPresent()) {
			return optionalMulta.get();
		} else {
			throw new RuntimeException("No se encuentra multa con id: " + id);
		}

	}

	@Override
	public void saveMulta(Multa multa) {
		multaRepository.save(multa);
	}

	@Override
	public void deleteMultaById(Long id) {
		multaRepository.deleteById(id);		
	}


}
