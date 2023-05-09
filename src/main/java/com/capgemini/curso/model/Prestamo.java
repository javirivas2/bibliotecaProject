package com.capgemini.curso.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
	private Long Id;
	@Column(name = "fecha_prestamo")
	private LocalDate inicio;
	@Column(name = "fecha_devolucion")
	private LocalDate fin;

	@ManyToOne()
	@JoinColumn(name = "lector_id")
	private Lector lector;
	@OneToOne
	@JoinColumn(name = "copia")
	private Copia copia;
	
	public Prestamo() {}
	
	public Prestamo(LocalDate inicio, LocalDate fin, Lector lector, Copia copia) {
		this.inicio = inicio;
		this.fin = fin;
		this.lector = lector;
		this.copia = copia;
	}

	public Prestamo(long id, LocalDate inicio, LocalDate fin, Lector lector, Copia copia) {
		this.Id = id;
		this.inicio = inicio;
		this.fin = fin;
		this.lector = lector;
		this.copia = copia;
	}
	
	public int getDuracionPrestamo(LocalDate fechaDevolucion) {
		return (int) ChronoUnit.DAYS.between(this.inicio, fechaDevolucion);
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		this.Id = id;
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
		return "Prestamo [id=" + Id + ", inicio=" + inicio + ", fin=" + fin + ", copia=" + copia + "]";
	}
	
}
