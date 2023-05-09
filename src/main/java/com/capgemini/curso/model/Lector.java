package com.capgemini.curso.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lectores")
public class Lector {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "direccion")
	private String direccion;
	@OneToMany(mappedBy = "copia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Prestamo[] prestamos;
	@OneToOne
	@JoinColumn(name = "multa")
	private Multa multa;
	
	
	public Lector() {
	}

	public Lector(String nombre, String telefono, String direccion) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
	}

	public Lector(Long id, String nombre, String telefono, String direccion, Prestamo[] prestamos, Multa multa) {
		this.Id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.prestamos = prestamos;
		this.multa = multa;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Prestamo[] getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(Prestamo[] prestamos) {
		if (prestamos != null && prestamos.length > 3) {
			throw new IllegalArgumentException("Solo se permiten hasta 3 prestamos.");
		}
		this.prestamos = prestamos;
	}

	public Multa getMulta() {
		return multa;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}

	public void devolver(Long id, LocalDate fechaDevolucion) {

	}

	public void prestar(long id, LocalDate fechaPrestamo) {

	}

	public void multar(int dias) {

	}

	@Override
	public String toString() {
		return "Lector [Id=" + Id + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion
				+ ", prestamos=" + Arrays.toString(prestamos) + ", multa=" + multa + "]";
	}

}
