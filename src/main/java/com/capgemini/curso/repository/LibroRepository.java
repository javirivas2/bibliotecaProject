package com.capgemini.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.curso.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
