package com.capgemini.curso.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reserva{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column
	private LocalDate fechaRealizacion;
	
	@Column
	private LocalDate fechaEstimada;
	
	@Column
	private LocalDate fechaFinReserva;
	
	@ManyToOne
	@JoinColumn(name = "libro_id")
	private Libro libro;
	
	@ManyToOne
	@JoinColumn(name = "lector_id")
	private Lector lector;
	
	@OneToOne
	@JoinColumn(name = "copia")
	private Copia copia;
	
	@Column
	private boolean isActive;
	
	public Reserva() {}

	public Reserva(Long id, LocalDate fechaRealizacion, LocalDate fechaEstimada, LocalDate fechaFinReserva, Libro libro,
			Lector lector, Copia copia, boolean isActive) {
		Id = id;
		this.fechaRealizacion = fechaRealizacion;
		this.fechaEstimada = fechaEstimada;
		this.fechaFinReserva = fechaFinReserva;
		this.libro = libro;
		this.lector = lector;
		this.copia = copia;
		this.isActive = isActive;
	}



	public Reserva(LocalDate fechaRealizacion, LocalDate fechaEstimada, Libro libro, Lector lector) {
		this.fechaRealizacion = fechaRealizacion;
		this.fechaEstimada = fechaEstimada;
		this.libro = libro;
		this.lector = lector;
		this.isActive = true;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public LocalDate getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(LocalDate fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public LocalDate getFechaEstimada() {
		return fechaEstimada;
	}

	public void setFechaEstimada(LocalDate fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
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
	
	public boolean tieneCopia() {
		return copia != null;
	}

	public LocalDate getFechaFinReserva() {
		return fechaFinReserva;
	}

	public void setFechaFinReserva(LocalDate fechaFinReserva) {
		this.fechaFinReserva = fechaFinReserva;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	

}
