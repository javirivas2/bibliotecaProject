package com.capgemini.curso.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.RestriccionesPrestamo;
import com.capgemini.curso.service.AutorService;
import com.capgemini.curso.service.EmailService;
import com.capgemini.curso.service.LectorService;
import com.capgemini.curso.service.LibroService;
import com.capgemini.curso.service.ReservaService;

@Controller
public class TestController {

	@Autowired
	private LectorService lectorService;

	@Autowired
	private GestionReservasPrestamosService gestionReservasPrestamosService;

	@Autowired
	private LibroService libroService;

	@Autowired
	private ReservaService reservaService;

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
			gestionReservasPrestamosService.prestar(lector.getId(), -1, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo inexistene");
		}

		// Un Lector que no existe deberia fallar
		try {
			gestionReservasPrestamosService.prestar(-1, 6, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo inexistene");
		}

		// Dos del mismo
		gestionReservasPrestamosService.prestar(lector.getId(), 2, LocalDate.now());
		gestionReservasPrestamosService.prestar(lector.getId(), 2, LocalDate.now());

		// Un Libro sin copias disponibles deberia fallar
		try {
			gestionReservasPrestamosService.prestar(lector.getId(), 2, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo no disponible");
		}

		// Uno de otro
		gestionReservasPrestamosService.prestar(lector.getId(), 5, LocalDate.now());

		// Al probar un cuarto deberia dar una excepcion
		try {
			gestionReservasPrestamosService.prestar(lector.getId(), 6, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo de mas");
		}

	}

	@GetMapping("/deshacerPrestamoA")
	public void deshacerPrestamoA() {
		List<Lector> lectores = lectorService.getAllLectores();
		Lector lector = lectores.get(0);

		gestionReservasPrestamosService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(),
				LocalDate.now());
		gestionReservasPrestamosService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(),
				LocalDate.now());
		gestionReservasPrestamosService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(),
				LocalDate.now());
	}

	// Probamos el funcionamiento de las multas
	@GetMapping("/simularMultas")
	public void simularMultas() {
		List<Lector> lectores = lectorService.getAllLectores();
		Lector lector = lectores.get(1);

		// Añadimos un libro al prestamo
		gestionReservasPrestamosService.prestar(lector.getId(), 1, LocalDate.now());

		// Lo devolvemos mas alla del limite de dias
		int diasDeMas = 5;
		int diasDeMulta = diasDeMas * 2;
		LocalDate pastDate = LocalDate.now().plusDays(RestriccionesPrestamo.DIAS_MAX + diasDeMas);
		gestionReservasPrestamosService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), pastDate);

		// Deberiamos tener una multa
		System.out.println("*****Multas*****");
		System.out.println("Tenemos multa: " + lector.getMulta() != null);
		System.out.println("Fin de la multa: " + pastDate.plusDays(diasDeMulta) + " | " + lector.getMulta().getfFin());

		// No podemos coger mas prestamos hoy
		try {
			gestionReservasPrestamosService.prestar(lector.getId(), 5, LocalDate.now());
		} catch (Exception e) {
			System.err.println("Fallo al coger prestado");
		}

		// Podemos coger una vez pasada la multa
		gestionReservasPrestamosService.prestar(lector.getId(), 5, pastDate.plusDays(diasDeMulta + 1));
	}

	@GetMapping("/testReserva")
	public void testReserva() {

		// Prestamos una copia dle libro uno de manera que ya no
		// queden copias disponibles
		gestionReservasPrestamosService.prestar(2, 1, LocalDate.now());

		Libro libro1 = libroService.getLibroById(1L);
		System.out.println("No hay copias disponibles de libro 1: " + libro1.getEjemplaresDisponibles().isEmpty());

		// Reservamos el libro 1 para que en el momento que quede libre
		// llevarnoslo
		gestionReservasPrestamosService.reservar(1L, 1L);

		System.out.println(reservaService.getAllReservas());

		// Liberamos una copia del primer libro
		gestionReservasPrestamosService.devolver(1L, 1L, LocalDate.now());
		// Se guarda la copia 1 como reservada para el lector 1

		// Liberamos una copia del primer libro
		gestionReservasPrestamosService.devolver(1L, 2L, LocalDate.now());
		// Se guarda la copia 2 como libre/Biblioteca

		// Finalizamos la primera reserva, generando un prestamo de la
		// primera copia
		gestionReservasPrestamosService.finalizarReserva(1L, LocalDate.now());
		
		// Presto la copia libre
		gestionReservasPrestamosService.prestar(4, 1, LocalDate.now());

		// Reservamos para simular reservas caducadas
		gestionReservasPrestamosService.reservar(1L, 3L);
		gestionReservasPrestamosService.reservar(1L, 5L);
		
		// Libero una copia del libro
		gestionReservasPrestamosService.devolver(4, 5, LocalDate.now());
		
		// Finalizo la reserva de manera illegal(Pasada de tiempo)
		gestionReservasPrestamosService.finalizarReserva(2L, LocalDate.now().plusDays(5));
		
		// Esta reserva deberia quedar muerta y para la reserva de la copia a la siguiente
	}

}
