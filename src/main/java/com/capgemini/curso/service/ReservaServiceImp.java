package com.capgemini.curso.service;

import java.util.Collections;
import java.util.Comparator;
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
	public void deleteById(Long id) {
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

	@Override
	public Reserva update(Reserva reserva) {
		return reservaRepository.save(reserva);
	}

	@Override
	public List<Object[]> countReservasByLector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reserva> findReservasByLectorId(Long ReservaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reserva> findReservasByFechaAndLector() {
		List<Reserva> todaslasReservas = reservaRepository.findAll();

		Collections.sort(todaslasReservas, Comparator.comparing(Reserva::getFechaPeticionReserva)
				.thenComparing(reserva -> reserva.getLector().getNombre()));
		return todaslasReservas;
	}

	

}
