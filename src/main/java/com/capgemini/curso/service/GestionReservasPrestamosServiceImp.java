package com.capgemini.curso.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.EstadoCopia;
import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Multa;
import com.capgemini.curso.model.Prestamo;
import com.capgemini.curso.model.Reserva;
import com.capgemini.curso.model.RestriccionesReserva;

@Service
public class GestionReservasPrestamosServiceImp implements GestionReservasPrestamosService {

	@Autowired
	private MultaService multaService;

	@Autowired
	private LibroService libroService;

	@Autowired
	private CopiaService copiaService;

	@Autowired
	private LectorService lectorService;

	@Autowired
	private PrestamoService prestamoService;

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private EmailService emailService;

	@Override
	public void devolver(long idLector, long idPrestamo, LocalDate fechaAct) {
		Prestamo prestamo = prestamoService.getPrestamoById(idPrestamo);

		Lector lec = lectorService.getLectorById(idLector);

		Copia copia = prestamo.getCopia();

		Optional<Multa> multa = lec.devolver(prestamo, fechaAct);
		if (multa.isPresent()) {// Hay multa nueva
			multaService.saveMulta(multa.get()); // La guardamos en db
			lec.setMulta(multa.get()); // La linkamos
		}

		// Guardamos los cambios
		copia.setEstadoCopia(EstadoCopia.BIBLIOTECA);
		copiaService.saveCopia(copia);

		prestamoService.savePrestamo(prestamo);
		lectorService.saveLector(lec);

		// Comprobamos que no existan reservas pendientes
		comprobarReservas(prestamo.getCopia());
	}

	@Override
	public void prestar(long idLector, long idLibro, LocalDate fechaAct) {
		Libro libro = libroService.getLibroById(idLibro);

		Lector lector = lectorService.getLectorById(idLector);
		if (!lector.puedeCogerLibro(fechaAct)) {
			throw new RuntimeException(
					"El lector " + lector.getNombre() + "(" + idLector + ") no puede coger mas libros");
		}

		List<Copia> ejemplaresDisponibles = libro.getEjemplaresDisponibles();
		if (ejemplaresDisponibles.isEmpty()) {
			throw new RuntimeException("No hay copias disponibles de " + libro.getTitulo());
		}

		Copia copia = ejemplaresDisponibles.get(0);

		Prestamo prestamo = new Prestamo(fechaAct, lector, copia, true);
		prestamoService.savePrestamo(prestamo);

		lector.addPrestamo(prestamo);

		copia.setEstadoCopia(EstadoCopia.PRESTADO);
		copiaService.saveCopia(copia);

	}

	@Override
	public void reservar(Long idLibro, Long idLector) {
		// Obtenemos el libro y verificamos que no tenga copias disponibles
		Libro libro = libroService.getLibroById(idLibro);
		if (!libro.getEjemplaresDisponibles().isEmpty()) { // Si no esta vacio
			throw new RuntimeException(
					"No se puede reservar el libro " + libro.getTitulo() + " (Existen copias disponibles)");
		}

		// Obtenemos el lector y verificamos que pueda reservar(No multas)
		Lector lector = lectorService.getLectorById(idLector);
		if (lector.tieneMultasPendientes(LocalDate.now())) {
			throw new RuntimeException("El lector " + lector.getId() + " no puede reservar (Tiene multas pendientes)");
		}

		// Obtenemos la fecha aproximada de fin de reserva
		List<Copia> copias = libro.getEjemplares();
		if (copias.isEmpty()) {
			throw new RuntimeException("El libro " + libro.getTitulo() + " no posee ninguna copia");
		}

		LocalDate fechaMin = LocalDate.MAX;
		for (Copia copia : copias) {
			LocalDate fechaFinTeorico = prestamoService.getPrestamoActivoByCopia(copia).getFin();
			if (fechaFinTeorico.isBefore(fechaMin)) {
				fechaMin = fechaFinTeorico;
			}
		}

		// Todo correcto generamos reserva
		Reserva reserva = new Reserva(LocalDate.now(), fechaMin, libro, lector);

		reservaService.saveReserva(reserva);
	}

	@Override
	public void asignarCopia(Long idReserva, Long idCopia) {
		// Obtenemos la copia
		Copia copia = copiaService.getCopiaById(idCopia);
		if (!copia.isDisponible())
			throw new RuntimeException("No se puede reservar la " + "copia " + copia.getId() + "(no disponible)");

		// Obtenemos la reserva
		Reserva reserva = reservaService.getReservaById(idReserva);

		// Reservamos la copia
		reserva.setCopia(copia);
		reserva.setFechaFinReserva(LocalDate.now().plusDays(RestriccionesReserva.DIAS_MAX));
		reservaService.saveReserva(reserva);

		copia.setEstadoCopia(EstadoCopia.RESERVADO);
		copiaService.saveCopia(copia);

		// Mandar correo al lector
		Lector lector = reserva.getLector();
		emailService.send("capgeminibiblioteca@gmail.com", lector.getUsername(), "[BIBLIOTECA] Reserva disponible",
				"Su reserva del libro: " + reserva.getLibro().getTitulo()
						+ " ya esta disponible /n RECUERDE tiene 48 horas a partir de este mensaje para recojer"
						+ " el libro.");
	}

	@Override
	public void finalizarReserva(Long idReserva, LocalDate fechaFin) {
		Reserva reserva = reservaService.getReservaById(idReserva);
		Copia copia = reserva.getCopia();

		// Terminamos reserva
		reserva.setActive(false);
		reserva.setCopia(null);
		reservaService.saveReserva(reserva);

		copia.setEstadoCopia(EstadoCopia.BIBLIOTECA);
		copiaService.saveCopia(copia);

		// Se paso la reserva
		if (reserva.getFechaFinReserva().isBefore(fechaFin)) {
			// Lanzamos la comprobacion de reserva del libro y salimos
			comprobarReservas(copia);
			return;
		} else {// El lector se lleva el libro
			prestar(reserva.getLector().getId(), copia.getId(), fechaFin);
			return;
		}

	}

	@Override
	public void comprobarReservas(Copia copia) {
		List<Reserva> reservasLibro = reservaService.getReservasByLibro(copia.getEjemplar());

		LocalDate fechaMin = LocalDate.MAX;
		Reserva reservaPendiente = null;

		for (Reserva reserva : reservasLibro) {
			if (reserva.isActive() && !reserva.tieneCopia() && reserva.getFechaRealizacion().isBefore(fechaMin)) {
				fechaMin = reserva.getFechaRealizacion();
				reservaPendiente = reserva;
			}
		}

		if (reservaPendiente != null) { // Tenemos reserva pendiente
			// Asignamos copia
			asignarCopia(reservaPendiente.getId(), copia.getId());
		} // Sino no hacemos nada

	}

}
