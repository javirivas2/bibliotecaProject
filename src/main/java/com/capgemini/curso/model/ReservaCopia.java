package com.capgemini.curso.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva_copia")
public class ReservaCopia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "fecha_realizada")
	private LocalDate fechaRealizada;
	@Column(name = "fecha_estimada")
	private LocalDate fechaEstimada;
	@Column(name = "copia_libro")
	private String copiaLibro;
	@Column(name = "plazo")
	private String plazo;

	public void reservar() {

	}

	public void finalizar() {

	}

	public ReservaCopia() {
		super();
	}

	public ReservaCopia(LocalDate fechaRealizada, LocalDate fechaEstimada, String copiaLibro, String plazo) {
		super();
		this.fechaRealizada = fechaRealizada;
		this.fechaEstimada = fechaEstimada;
		this.copiaLibro = copiaLibro;
		this.plazo = plazo;
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

	public String getCopiaLibro() {
		return copiaLibro;
	}

	public void setCopiaLibro(String copiaLibro) {
		this.copiaLibro = copiaLibro;
	}

	public String getPlazo() {
		return plazo;
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

}
