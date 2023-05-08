package com.capgemini.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.curso.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
