package com.capgemini.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.curso.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	@Query("SELECT COUNT(r), r.lector FROM Reserva r GROUP BY r.lector")
	List<Object[]> countReservasByLector();

	List<Reserva> findReservasBylectorId(Long lectorId);
}
