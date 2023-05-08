package com.capgemini.curso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prestamos")
public class Copia {
//TODO: Sin acabar solo para probar el lector y prestamos

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
}
