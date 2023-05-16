package com.capgemini.curso.service;

import java.util.List;

import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Reserva;

public interface ReservaService {
	
	List<Reserva> getAllReservas();
	List<Reserva> getReservasByLibro(Libro libro);
	List<Reserva> getReservasActivas();
	Reserva getReservaById(Long id);
	
	void saveReserva(Reserva reserva);
	void deleteReservaById(Long id);
	
}
