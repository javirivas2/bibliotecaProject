package com.capgemini.curso.service;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.EstadoCopia;
import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Prestamo;
import com.capgemini.curso.repository.LectorRepository;
import com.capgemini.curso.repository.LibroRepository;
import com.capgemini.curso.repository.PrestamoRepository;



@Service("lectorServiceImpl")
@Transactional
public class LectorServiceImp implements LectorService {

	
	private static final Logger logger = LoggerFactory.getLogger(LectorServiceImp.class);
	@Autowired
	private LectorRepository lectorRepository;
	
	@Autowired
	private PrestamoRepository prestamoRepository;
	
	@Autowired
	private LibroRepository libroRepository;


	
	@Override
	@Transactional(readOnly=true)
	public List<Lector> getAllLectores() {
		logger.info("LectorServiceIml getAllLectores");
		List<Lector> lectores = new ArrayList<>();
		lectorRepository.findAll().forEach(lectores::add);
		return lectores;
	}

	@Override
	@Transactional(readOnly=true)
	public Lector getLectorById(long id) {
	    logger.info("LectorServiceIml getLectorById");
	    Optional<Lector> optLector = lectorRepository.findById(id);
	    return optLector.isPresent() ? optLector.get() : null;
	}

	@Override
	public void saveLector(Lector lector) {
		lectorRepository.save(lector);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Page<Lector> findAllPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void devolver(long idLector, long idPrestamo, LocalDate fechaAct) {
		Optional<Prestamo> optPrestamo = prestamoRepository.findById(idPrestamo);
		if(optPrestamo.isEmpty()) {
			throw new RuntimeException("No existe el prestamo " + idPrestamo);
		}
		
		Lector lec = getLectorById(idLector);
		lec.devolver(optPrestamo.get(), fechaAct);	
		optPrestamo.get().getCopia().setEstadoCopia(EstadoCopia.BIBLIOTECA);
	}

	@Override
	public void prestar(long idLector, long idLibro, LocalDate fechaAct) {
		Optional<Libro> optLibro = libroRepository.findById(idLibro);
		if(optLibro.isEmpty()) {
			throw new RuntimeException("No existe el libro " + idLibro);
		}
		
		Lector lector = getLectorById(idLector);
		if(lector.puedeCogerLibro(fechaAct)) {
			throw new RuntimeException("El lector " + idLector + " no puede coger mas libros");
		}
		
		Libro libro = optLibro.get();
		List<Copia> ejemplaresDisponibles = libro.getEjemplaresDisponibles();
		if(ejemplaresDisponibles.isEmpty()) {
			throw new RuntimeException("No hay copias disponibles de " + idLibro);
		}
		
		Copia ejemplar = ejemplaresDisponibles.get(0);
		
		LocalDate fechaFin = fechaAct.plusDays(30);//El 30 tendria que salir a una varible o algo global
		
		Prestamo prestamo = new Prestamo(fechaAct, fechaFin, lector, ejemplar);
		prestamoRepository.save(prestamo);
		
		lector.addPrestamo(prestamo);
		ejemplar.setEstadoCopia(EstadoCopia.PRESTADO);
		
	}

}
