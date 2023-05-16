package com.capgemini.curso.service;

import java.time.LocalDate;

import com.capgemini.curso.model.Copia;

public interface GestionReservasPrestamosService {

	void devolver(long idLector, long idPrestamo, LocalDate fechaAct);

	void prestar(long idLector, long idLibro, LocalDate fechaAct);

	void reservar(Long idLibro, Long idLector);

	void asignarCopia(Long idReserva, Long idCopia);

	void finalizarReserva(Long idReserva, LocalDate fechaFin);

	void comprobarReservas(Copia copia);
}
