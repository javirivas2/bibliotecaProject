package com.capgemini.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.curso.model.Autor;
import com.capgemini.curso.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

	List<Libro> findByAutor(Autor autor);

}
