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

	public Prestamo() {
	}

	public Prestamo(Long id, LocalDate inicio, LocalDate fin, Lector lector, Copia copia) {
		this.Id = id;
		this.inicio = inicio;
		this.fin = fin;
		this.lector = lector;
		this.copia = copia;
	}

	@Override
	public String toString() {
		return "Prestamo [Id=" + Id + ", inicio=" + inicio + ", fin=" + fin + ", lector=" + lector + ", copia=" + copia
				+ "]";
	}
}
