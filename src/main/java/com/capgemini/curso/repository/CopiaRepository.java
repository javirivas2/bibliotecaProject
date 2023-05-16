package com.capgemini.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.Libro;

@Repository
public interface CopiaRepository extends JpaRepository<Copia, Long> {
	List<Copia> findByEjemplar(Libro libro);
}
