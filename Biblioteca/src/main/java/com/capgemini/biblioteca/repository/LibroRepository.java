package com.capgemini.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
