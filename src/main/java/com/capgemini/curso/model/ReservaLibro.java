package com.capgemini.curso.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva_libros")
public class ReservaLibro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "fecha_realizada")
	private LocalDate fechaRealizada;
	@Column(name = "fecha_estimada")
	private LocalDate fechaEstimada;
	@Column(name="nombre_lector")
	private String nombreLector;
	@Column(name="nombre_libro")
	private String nombreLibro;

	public void reservar() {

	}

	public void finalizar() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaRealizada() {
		return fechaRealizada;
	}

	public void setFechaRealizada(LocalDate fechaRealizada) {
		this.fechaRealizada = fechaRealizada;
	}

	public LocalDate getFechaEstimada() {
		return fechaEstimada;
	}

	public void setFechaEstimada(LocalDate fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}

	public ReservaLibro(LocalDate fechaRealizada, LocalDate fechaEstimada) {
		super();
		this.fechaRealizada = fechaRealizada;
		this.fechaEstimada = fechaEstimada;
	}

	public ReservaLibro() {
		super();
	}

}
