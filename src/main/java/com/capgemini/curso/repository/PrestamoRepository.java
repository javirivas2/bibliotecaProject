package com.capgemini.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

	List<Prestamo> findByActivo(boolean activo);

	List<Prestamo> findByCopia(Copia copia);

}
