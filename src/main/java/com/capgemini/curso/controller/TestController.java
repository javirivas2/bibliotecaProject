package com.capgemini.curso.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.RestriccionesPrestamo;
import com.capgemini.curso.service.AutorService;
import com.capgemini.curso.service.EmailService;
import com.capgemini.curso.service.LectorService;

@Controller
public class TestController {

	@Autowired
	private LectorService lectorService;

	@Autowired
	private AutorService autorService;

	@Autowired
	private EmailService emailService;

	// prueba de email
	@GetMapping("/email")
	public void testEmail() {
		try {
			emailService.send("capgeminibiblioteca@gmail.com", "capgeminibiblioteca@gmail.com",
					"capgeminibiblioteca@gmail.com", "capgeminibiblioteca@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Probamos a hacer 3 prestamos con un lector
	@GetMapping("/simularPrestamoA")
	public void simularPrestamoA() {
		List<Lector> lectores = lectorService.getAllLectores();
		Lector lector = lectores.get(0);

		// Un libro que no existe deberia fallar
		try {
			lectorService.prestar(lector.getId(), -1, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo inexistene");
		}

		// Un Lector que no existe deberia fallar
		try {
			lectorService.prestar(-1, 6, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo inexistene");
		}

		// Dos del mismo
		lectorService.prestar(lector.getId(), 2, LocalDate.now());
		lectorService.prestar(lector.getId(), 2, LocalDate.now());

		// Un Libro sin copias disponibles deberia fallar
		try {
			lectorService.prestar(lector.getId(), 2, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo no disponible");
		}

		// Uno de otro
		lectorService.prestar(lector.getId(), 5, LocalDate.now());

		// Al probar un cuarto deberia dar una excepcion
		try {
			lectorService.prestar(lector.getId(), 6, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo de mas");
		}

	}

	@GetMapping("/deshacerPrestamoA")
	public void deshacerPrestamoA() {
		List<Lector> lectores = lectorService.getAllLectores();
		Lector lector = lectores.get(0);

		lectorService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), LocalDate.now());
		lectorService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), LocalDate.now());
		lectorService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), LocalDate.now());
	}

	// Probamos el funcionamiento de las multas
	@GetMapping("/simularMultas")
	public void simularMultas() {
		List<Lector> lectores = lectorService.getAllLectores();
		Lector lector = lectores.get(1);

		// Añadimos un libro al prestamo
		lectorService.prestar(lector.getId(), 1, LocalDate.now());

		// Lo devolvemos mas alla del limite de dias
		int diasDeMas = 5;
		int diasDeMulta = diasDeMas * 2;
		LocalDate pastDate = LocalDate.now().plusDays(RestriccionesPrestamo.DIAS_MAX + diasDeMas);
		lectorService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), pastDate);

		// Deberiamos tener una multa
		System.out.println("*****Multas*****");
		System.out.println("Tenemos multa: " + lector.getMulta() != null);
		System.out.println("Fin de la multa: " + pastDate.plusDays(diasDeMulta) + " | " + lector.getMulta().getfFin());

		// No podemos coger mas prestamos hoy
		try {
			lectorService.prestar(lector.getId(), 5, LocalDate.now());
		} catch (Exception e) {
			System.err.println("Fallo al coger prestado");
		}

		// Podemos coger una vez pasada la multa
		lectorService.prestar(lector.getId(), 5, pastDate.plusDays(diasDeMulta + 1));
	}
}
