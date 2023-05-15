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
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "fecha Reserva")
	private LocalDate fechaPeticionReserva;

	@ManyToOne
	@JoinColumn(name = "libro_id")
	private Libro libro;

	@ManyToOne
	@JoinColumn(name = "lector_id")
	private Lector lector;

	@OneToOne
	@JoinColumn(name = "copia_id")
	private Copia copia;

	public Reserva() {

	}

	public Reserva(Long id, LocalDate fechaPeticionReserva, Libro libro, Lector lector, Copia copia) {
		this.Id = id;
		this.fechaPeticionReserva = fechaPeticionReserva;
		this.libro = libro;
		this.lector = lector;
		this.copia = copia;
	}
	

	public Reserva(LocalDate fechaPeticionReserva, Libro libro, Lector lector, Copia copia) {
		this.fechaPeticionReserva = fechaPeticionReserva;
		this.libro = libro;
		this.lector = lector;
		this.copia = copia;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public LocalDate getFechaPeticionReserva() {
		return fechaPeticionReserva;
	}

	public void setFechaPeticionReserva(LocalDate fechaPeticionReserva) {
		this.fechaPeticionReserva = fechaPeticionReserva;
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

	@Override
	public String toString() {
		return "Reserva [Id=" + Id + ", fechaPeticionReserva=" + fechaPeticionReserva + ", libro=" + libro + ", lector="
				+ lector + ", copia=" + copia + "]";
	}
	
	

}
