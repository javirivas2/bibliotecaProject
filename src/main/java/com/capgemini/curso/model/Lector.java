package com.capgemini.curso.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private int maxPrestamoDays = 30;

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
	private List<Prestamo> prestamos;
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

	public Lector(Long id, String nombre, String telefono, String direccion, List<Prestamo> prestamos, Multa multa) {
		this.Id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.prestamos = prestamos;
		this.multa = multa;
	}

	public void devolver(Prestamo prestamo, LocalDate fechaDev) {
		// Comprobamos que la devolución es posible
		if (prestamos.isEmpty() || !prestamos.contains(prestamo)) {
			throw new RuntimeException("El lector " + Id + " no tiene asignado el prestamo " + prestamo.getId());
		}

		// Comprobamos si debemos multar
		int duracion = prestamo.getDuracionPrestamo(fechaDev);
		if (duracion > maxPrestamoDays) { // Multa
			int diasAñadir = duracion - maxPrestamoDays;
			multar(diasAñadir);
		}

		prestamo.setFin(fechaDev);
		prestamos.remove(prestamo);
		return;
	}
	
	public boolean puedeCogerLibro(LocalDate fechaInc) {
		if (multa != null && multa.getfFin().isAfter(fechaInc)) { // Hay multas pendientes
			return false;
		}
		if(prestamos.size() >= 3) { // Tiene mas de tres prestamos
			return false;
		}
		return true;
	}

	public void addPrestamo(Prestamo prestamo) {
		prestamos.add(prestamo);
	}

	public void multar(int dias) {
		int diasAñadir = dias * 2;
		if (multa == null) {
			LocalDate fechaFinMulta = LocalDate.now().plusDays(diasAñadir);
			multa = new Multa(LocalDate.now(), fechaFinMulta);
		} else {
			multa.setfFin(multa.getfFin().plusDays(diasAñadir));
		}
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

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		if (prestamos != null && prestamos.size() > 3) {
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

	@Override
	public String toString() {
		return "Lector [nSocio=" + Id + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion
				+ ", prestamos=" + prestamos + ", multa=" + multa + "]";
	}

}
