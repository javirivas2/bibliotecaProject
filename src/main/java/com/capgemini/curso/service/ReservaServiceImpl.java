package com.capgemini.curso.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Reserva;
import com.capgemini.curso.repository.ReservaRepository;

@Service("reservaServiceImpl")
@Transactional
public class ReservaServiceImpl implements ReservaService {
	private static final Logger logger = LoggerFactory.getLogger(ReservaServiceImpl.class);
	@Autowired
	private ReservaRepository reservaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Reserva> getAllReservas() {
		logger.info("ReservaServiceImpl getAllReservas");
		List<Reserva> reservas = new ArrayList<>();
		reservaRepository.findAll().forEach(reservas::add);
		return reservas;
	}

	@Override
	@Transactional(readOnly = true)
	public Reserva getReservaById(Long id) {
		logger.info("ReservaServiceImpl getReservaById");
		Optional<Reserva> optReserva = reservaRepository.findById(id);
		if (optReserva.isEmpty()) {
			throw new RuntimeException("No existe la reserva con id: " + id);
		}
		return optReserva.get();
	}

	@Override
	public void saveReserva(Reserva reserva) {
		logger.info("ReservaServiceImpl saveReserva");
		reservaRepository.save(reserva);

	}

	@Override
	public void deleteById(Long id) {
		logger.info("ReservaServiceImpl deleteById");
		reservaRepository.deleteById(id);
	}

	@Override
	public Reserva update(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
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
		logger.info("ReservaServiceImpl findReservasByFechaAndLector");
		List<Reserva> todaslasReservas = reservaRepository.findAll();

		Collections.sort(todaslasReservas, Comparator.comparing(Reserva::getFechaPeticionReserva)
				.thenComparing(reserva -> reserva.getLector().getNombre()));
		return todaslasReservas;
	}

	@Override
	public List<Reserva> getReservasByLibro(Libro libro) {
		logger.info("ReservaServiceImpl getReservasByLibro");
		return reservaRepository.findByLibro(libro);
	}

	@Override
	public List<Reserva> getReservasActivas() {
		logger.info("ReservaServiceImpl getReservasActivas");
		return reservaRepository.findByIsActive(true);
	}

}
