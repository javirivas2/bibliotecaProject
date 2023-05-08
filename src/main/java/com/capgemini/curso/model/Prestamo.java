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
	private int numDiasMax = 30;

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
	
	public Prestamo() {}

	public Prestamo(long id, LocalDate inicio, LocalDate fin, Lector lector, Copia copia) {
		this.id = id;
		this.inicio = inicio;
		this.fin = fin;
		this.lector = lector;
		this.copia = copia;
	}
	
	public int getRetrasoPrestamo(LocalDate fechaDevolucion) {
		int retraso = fechaDevolucion.m
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFin() {
		return fin;
	}

	public void setFin(LocalDate fin) {
		this.fin = fin;
	}

	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}

	public Copia getCopia() {
		return copia;
	}

	public void setCopia(Copia copia) {
		this.copia = copia;
	}

	@Override
	public String toString() {
		return "Prestamo [id=" + id + ", inicio=" + inicio + ", fin=" + fin + ", copia=" + copia + "]";
	}
	
}
