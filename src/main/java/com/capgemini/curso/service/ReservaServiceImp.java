package com.capgemini.curso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Reserva;
import com.capgemini.curso.repository.ReservaRepository;

@Service
public class ReservaServiceImp implements ReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Override
	public List<Reserva> getAllReservas() {
		return reservaRepository.findAll();
	}

	@Override
	public Reserva getReservaById(Long id) {
		Optional<Reserva> optReserva = reservaRepository.findById(id);
		if(optReserva.isEmpty()) {
			throw new RuntimeException("No existe la reserva con id: " + id);
		}
		return optReserva.get();
	}

	@Override
	public void saveReserva(Reserva reserva) {
		reservaRepository.save(reserva);
	}

	@Override
	public void deleteReservaById(Long id) {
		reservaRepository.deleteById(id);
	}

	@Override
	public List<Reserva> getReservasByLibro(Libro libro) {
		return reservaRepository.findByLibro(libro);
	}

	@Override
	public List<Reserva> getReservasActivas() {
		return reservaRepository.findByIsActive(true);
	}

	

}
