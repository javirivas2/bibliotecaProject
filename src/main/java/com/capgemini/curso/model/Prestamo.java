package com.capgemini.curso.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prestamos")
public class Prestamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private LocalDate inicio;
	
	@Column
	private LocalDate fin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lector")
	private Lector lector;
	
	@OneToOne
	@JoinColumn(name = "copia")
	private Copia copia;
}
