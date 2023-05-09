package com.capgemini.curso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "copias")
public class Copia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Enumerated(EnumType.STRING)
	private EstadoCopia estadoCopia;
	
	@ManyToOne
	@JoinColumn(name = "copia_id")
	private Libro ejemplar;

	public Copia() {
	}

	public Copia(Long id, EstadoCopia estadoCopia, Libro ejemplar) {
		this.Id = id;
		this.estadoCopia = estadoCopia;
		this.ejemplar = ejemplar;
	}
	
	public Copia(EstadoCopia estadoCopia, Libro ejemplar) {
		this.estadoCopia = estadoCopia;
		this.ejemplar = ejemplar;
	}
	
	public boolean isDisponible() {
		return this.estadoCopia.equals(EstadoCopia.BIBLIOTECA);
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
