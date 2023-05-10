package com.capgemini.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.proyecto.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
