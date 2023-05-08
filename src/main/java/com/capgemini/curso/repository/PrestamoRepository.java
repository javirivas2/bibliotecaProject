package com.capgemini.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.curso.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

}
