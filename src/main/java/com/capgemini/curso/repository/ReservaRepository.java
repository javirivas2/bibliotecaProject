package com.capgemini.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	List<Reserva> findByLibro(Libro libro);

}
