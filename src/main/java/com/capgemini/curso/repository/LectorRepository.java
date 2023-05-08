package com.capgemini.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.curso.model.Lector;

public interface LectorRepository extends JpaRepository<Lector, Long> {

}
