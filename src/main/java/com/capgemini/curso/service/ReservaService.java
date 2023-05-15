package com.capgemini.curso.service;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.Reserva;

public interface ReservaService {
	
	List<Reserva> getAllReservas();
	Reserva getReservaById(Long id);
	
	void saveReserva(Reserva reserva);
	void deleteReservaById(Long id);
	
	void reservar(Long idLibro, Long idLector);
	void asignarCopia(Long idReserva, Long idCopia);
	void finalizarReserva(Long idReserva, LocalDate fechaFin);

	void comprobarReservas(Copia copia);
	
}
