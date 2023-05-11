package com.capgemini.curso.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@OneToMany(mappedBy = "lector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

	public Optional<Multa> devolver(Prestamo prestamo, LocalDate fechaDev) {
		Optional<Multa> multa = Optional.empty();
		
		// Comprobamos que la devolución es posible
		if (prestamos.isEmpty() || !prestamos.contains(prestamo)) {
			throw new RuntimeException("El lector " + Id + " no tiene asignado el prestamo " + prestamo.getId());
		}

		// Comprobamos si debemos multar
		int duracion = prestamo.getDuracionPrestamo(fechaDev);
		if (duracion > RestriccionesPrestamo.DIAS_MAX) { // Multa
			int diasAñadir = duracion - maxPrestamoDays;
			multa = multar(fechaDev, diasAñadir);
		}

		prestamo.setDevolucion(fechaDev);
		prestamo.setActivo(false);
		return multa;
	}

	public boolean puedeCogerLibro(LocalDate fechaInc) {
		if (multa != null && multa.getfFin().isAfter(fechaInc)) { // Hay multas pendientes
			return false;
		}
		if (getPrestamosActivos().size() >= RestriccionesPrestamo.ACTIVOS_MAX) {
			return false; // Tiene mas de los prestamos permitidos
		}
		return true;
	}

	public void addPrestamo(Prestamo prestamo) {
		prestamos.add(prestamo);
	}

	public Optional<Multa> multar(LocalDate fechaMulta, int dias) {
		Optional<Multa> multa = Optional.empty();
		
		int diasAñadir = dias * 2;
		if (this.multa == null) {//Si no tiene multa la creamos y la mandamos
			// para arriba para que la guarde el service
			LocalDate fechaFinMulta = fechaMulta.plusDays(diasAñadir);
			multa = Optional.of(new Multa(fechaMulta, fechaFinMulta));
		} else {
			this.multa.setfFin(this.multa.getfFin().plusDays(diasAñadir));
		}
		
		return multa;
	}

	public List<Prestamo> getPrestamosActivos() {
		List<Prestamo> activos = new ArrayList<>();

		for (Prestamo prestamo : prestamos) {
			if (prestamo.isActivo()) {
				activos.add(prestamo);
			}
		}

		return activos;
	}
	
	public int countPrestamosActivos() {
		return getPrestamosActivos().size();
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
		if (prestamos != null && prestamos.size() > RestriccionesPrestamo.ACTIVOS_MAX) {
			throw new IllegalArgumentException(
					"Solo se permiten hasta " + RestriccionesPrestamo.ACTIVOS_MAX + " prestamos.");
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
