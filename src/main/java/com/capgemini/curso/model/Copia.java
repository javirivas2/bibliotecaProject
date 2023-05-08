package com.capgemini.curso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "copias")
public class Copia {
//TODO: Sin acabar solo para probar el lector y prestamos

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Enumerated(EnumType.STRING)
	private EstadoCopia estadoCopia;
	@ManyToOne
	@JoinColumn(name = "copia_id")
	private Libro ejemplar;
	@ManyToOne
	@JoinColumn(name = "prestamo_id")
	private Prestamo prestamo;

	public Copia() {
	}

	public Copia(Long id, EstadoCopia estadoCopia, Libro ejemplar) {
		this.Id = id;
		this.estadoCopia = estadoCopia;
		this.ejemplar = ejemplar;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public EstadoCopia getEstadoCopia() {
		return estadoCopia;
	}

	public void setEstadoCopia(EstadoCopia estadoCopia) {
		this.estadoCopia = estadoCopia;
	}

	public Libro getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Libro ejemplar) {
		this.ejemplar = ejemplar;
	}

	@Override
	public String toString() {
		return "Copia [Id=" + Id + ", estadoCopia=" + estadoCopia + ", ejemplar=" + ejemplar + "]";
	}
}
