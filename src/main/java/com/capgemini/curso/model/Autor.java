package com.capgemini.curso.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(name = "nombre", unique = true, nullable = false)
	private String nombre;

	@Column(name = "nacionalidad")
	private String nacionalidad;

	@Column(name = "fecha_nacimiento")
	@DateTimeFormat(pattern = "mm-dd-yyyy")
	private LocalDate fechaNacimiento;
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Libro> obras;

	public Autor() {
	}

	public Autor(Long id, String nombre, String nacionalidad, LocalDate fechaNacimiento) {
		this.Id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Autor(String nombre, String nacionalidad, LocalDate fechaNacimiento) {
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Libro> getObras() {
		return obras;
	}

	public void setObras(List<Libro> obras) {
		this.obras = obras;
	}

	@Override
	public String toString() {
		return "Autor [Id=" + Id + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", fechaNacimiento="
				+ fechaNacimiento + "]";
	}

}
