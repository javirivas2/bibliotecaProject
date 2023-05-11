package com.capgemini.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.curso.model.Copia;

@Repository
public interface CopiaRepository extends JpaRepository<Copia, Long> {

}
