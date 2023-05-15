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
import com.capgemini.curso.model.Reserva;
import com.capgemini.curso.model.RestriccionesReserva;
import com.capgemini.curso.repository.ReservaRepository;

@Service
public class ReservaServiceImp implements ReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private CopiaService copiaService;
	
	@Autowired
	private LectorService lectorService;
	
	@Autowired
	private PrestamoService prestamoService;

	@Override
	public List<Reserva> getAllReservas() {
		return reservaRepository.findAll();
	}

	@Override
	public Reserva getReservaById(Long id) {
		Optional<Reserva> optReserva = reservaRepository.findById(id);
		if(optReserva.isEmpty()) {
			throw new RuntimeException("No existe la reserva con id: " + id);
		}
		return optReserva.get();
	}

	@Override
	public void saveReserva(Reserva reserva) {
		reservaRepository.save(reserva);
	}

	@Override
	public void deleteReservaById(Long id) {
		reservaRepository.deleteById(id);
	}

	@Override
	public void reservar(Long idLibro, Long idLector) {
		//Obtenemos el libro y verificamos que no tenga copias disponibles
		Libro libro = libroService.getLibroById(idLibro);
		if(!libro.getEjemplaresDisponibles().isEmpty()) { //Si no esta vacio
			throw new RuntimeException("No se puede reservar el libro " + libro.getTitulo() 
				+ " (Existen copias disponibles)");
		}
		
		//Obtenemos el lector y verificamos que pueda reservar(No multas)
		Lector lector = lectorService.getLectorById(idLector);
		if(lector.tieneMultasPendientes(LocalDate.now())) { 
			throw new RuntimeException("El lector " + lector.getId() 
				+ " no puede reservar (Tiene multas pendientes)");
		}
		
		//Obtenemos la fecha aproximada de fin de reserva
		List<Copia> copias = libro.getEjemplares();
		if(copias.isEmpty()) {
			throw new RuntimeException("El libro " + libro.getTitulo()
					+ " no posee ninguna copia");
		}
		LocalDate fechaMin = LocalDate.MAX;
		for (Copia copia : copias) {
			LocalDate fechaFinTeorico = prestamoService.getPrestamoActivoByCopia(copia).getFin();
			if(fechaFinTeorico.isBefore(fechaMin)) {
				fechaMin = fechaFinTeorico;
			}
		}
		
		//Todo correcto generamos reserva
		Reserva reserva = new Reserva(LocalDate.now(), fechaMin, libro, lector);
		
		reservaRepository.save(reserva);
	}

	@Override
	public void asignarCopia(Long idReserva, Long idCopia) {
		//Obtenemos la copia
		Copia copia = copiaService.getCopiaById(idCopia);
		if(!copia.isDisponible())
			throw new RuntimeException("No se puede reservar la "
					+ "copia " + copia.getId() +"(no disponible)");
		
		//Obtenemos la reserva	
		Reserva reserva = getReservaById(idReserva);
		
		//Reservamos la copia
		reserva.setCopia(copia);
		reserva.setFechaFinReserva(LocalDate.now().plusDays(RestriccionesReserva.DIAS_MAX));
		
		copia.setEstadoCopia(EstadoCopia.RESERVADO);

		//TODO Mandar correo al lector **************************
		Lector lector = reserva.getLector();
		
	}

	@Override
	public void finalizarReserva(Long idReserva, LocalDate fechaFin) {
		Reserva reserva = getReservaById(idReserva);
		Copia copia = reserva.getCopia();
		
		//Terminamos reserva			
		reserva.setActive(false);
		reserva.setCopia(null);
		
		copia.setEstadoCopia(EstadoCopia.BIBLIOTECA);
		
		//Se paso la reserva
		if(reserva.getFechaFinReserva().isBefore(fechaFin)) {
			//Lanzamos la comprobacion de reserva del libro y salimos
			comprobarReservas(copia);
			return;
		}else {//El lector se lleva el libro
			lectorService.prestar(reserva.getLector().getId(), copia.getId(), fechaFin);
			return;
		}
		

	}

	@Override
	public void comprobarReservas(Copia copia) {
		List<Reserva> reservasLibro = reservaRepository.findByLibro(copia.getEjemplar());
		
		LocalDate fechaMin = LocalDate.MAX;
		Reserva reservaPendiente = null;
		
		for (Reserva reserva : reservasLibro) {
			if(reserva.isActive() &&
					reserva.getFechaRealizacion().isBefore(fechaMin)) {
				fechaMin = reserva.getFechaRealizacion();
				reservaPendiente = reserva;
			}
		}
		
		if(reservaPendiente != null) { //Tenemos reserva pendiente
			//Asignamos copia
			asignarCopia(reservaPendiente.getId(), copia.getId());
		}//Sino no hacemos nada
		
	}

}
