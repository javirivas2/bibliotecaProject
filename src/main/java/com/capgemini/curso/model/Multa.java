package com.capgemini.curso.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "multas")
public class Multa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private LocalDate fInicio;
	
	@Column
	private LocalDate fFin;

	public Multa() {}
	
	public Multa(long id, LocalDate fInicio, LocalDate fFin) {
		this.id = id;
		this.fInicio = fInicio;
		this.fFin = fFin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getfInicio() {
		return fInicio;
	}

	public void setfInicio(LocalDate fInicio) {
		this.fInicio = fInicio;
	}

	public LocalDate getfFin() {
		return fFin;
	}

	public void setfFin(LocalDate fFin) {
		this.fFin = fFin;
	}
	
	
}
