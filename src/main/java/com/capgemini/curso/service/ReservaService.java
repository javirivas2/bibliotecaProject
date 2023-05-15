package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.Reserva;

public interface ReservaService {


	List<Reserva> getAllReservas();

	Reserva getReservaById(Long id);

	void saveReserva(Reserva reserva);

	void deleteById(Long id);

	Reserva update(Reserva reserva);

	List<Object[]> countReservasByLector();

	List<Reserva> findReservasByLectorId(Long ReservaId);
}
