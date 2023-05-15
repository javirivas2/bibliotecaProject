package com.capgemini.curso.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.EstadoCopia;
import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Multa;
import com.capgemini.curso.model.Prestamo;
import com.capgemini.curso.repository.CopiaRepository;
import com.capgemini.curso.repository.LectorRepository;
import com.capgemini.curso.repository.LibroRepository;
import com.capgemini.curso.repository.MultaRepository;
import com.capgemini.curso.repository.PrestamoRepository;

@Service("lectorServiceImpl")
@Transactional
public class LectorServiceImp implements LectorService, UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(LectorServiceImp.class);
	@Autowired
	private LectorRepository lectorRepository;

	@Autowired
	private PrestamoRepository prestamoRepository;

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private MultaRepository multaRepository;

	@Autowired
	private ReservaService reservaService;

	@Override
	@Transactional(readOnly = true)
	public List<Lector> getAllLectores() {
		logger.info("LectorServiceIml getAllLectores");
		List<Lector> lectores = new ArrayList<>();
		lectorRepository.findAll().forEach(lectores::add);
		return lectores;
	}

	@Override
	@Transactional(readOnly = true)
	public Lector getLectorById(long id) {
		logger.info("LectorServiceIml getLectorById");
		Optional<Lector> optLector = lectorRepository.findById(id);
		if (optLector.isEmpty())
			throw new RuntimeException("No existe el lector " + id);

		return optLector.get();
	}

	@Override
	public void saveLector(Lector lector) {
		lectorRepository.save(lector);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Lector> findAllPage(int pageNum, int pageSize, String sortField, String sortDirection) {
		Sort ordenador = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, ordenador);

		return lectorRepository.findAll(pageable);
	}

	@Override
	public void devolver(long idLector, long idPrestamo, LocalDate fechaAct) {
		Optional<Prestamo> optPrestamo = prestamoRepository.findById(idPrestamo);
		if (optPrestamo.isEmpty()) {
			throw new RuntimeException("No existe el prestamo " + idPrestamo);
		}
		Prestamo prestamo = optPrestamo.get();

		Lector lec = getLectorById(idLector);

		prestamo.getCopia().setEstadoCopia(EstadoCopia.BIBLIOTECA);
		Optional<Multa> multa = lec.devolver(prestamo, fechaAct);
		if (multa.isPresent()) {// Hay multa nueva
			multaRepository.save(multa.get()); // La guardamos en db
			lec.setMulta(multa.get()); // La linkamos
		}
		
		//Comprobamos que no existan reservas pendientes
		reservaService.comprobarReservas(prestamo.getCopia());
	}

	@Override
	public void prestar(long idLector, long idLibro, LocalDate fechaAct) {
		Optional<Libro> optLibro = libroRepository.findById(idLibro);
		if (optLibro.isEmpty()) {
			throw new RuntimeException("No existe el libro " + idLibro);
		}

		Lector lector = getLectorById(idLector);
		if (!lector.puedeCogerLibro(fechaAct)) {
			throw new RuntimeException(
					"El lector " + lector.getNombre() + "(" + idLector + ") no puede coger mas libros");
		}

		Libro libro = optLibro.get();
		List<Copia> ejemplaresDisponibles = libro.getEjemplaresDisponibles();
		if (ejemplaresDisponibles.isEmpty()) {
			throw new RuntimeException("No hay copias disponibles de " + libro.getTitulo());
		}

		Copia ejemplar = ejemplaresDisponibles.get(0);

		Prestamo prestamo = new Prestamo(fechaAct, lector, ejemplar, true);
		prestamoRepository.save(prestamo);

		lector.addPrestamo(prestamo);
		ejemplar.setEstadoCopia(EstadoCopia.PRESTADO);

	}

	@Override
	public List<Lector> getLectoresQuePuedenPrestamo(LocalDate fechaPrestamo) {
		List<Lector> lectores = getAllLectores();
		List<Lector> lectoresPrestamos = new ArrayList<>();

		for (Lector lector : lectores) {
			if (lector.puedeCogerLibro(fechaPrestamo)) {
				lectoresPrestamos.add(lector);
			}
		}
		return lectoresPrestamos;
	}

	@Override
	public void deleteById(Long id) {
		Optional<Prestamo> optPrestamo = prestamoRepository.findById(id);
		if (optPrestamo.isEmpty()) {
			lectorRepository.deleteById(id);
		} else {
			throw new RuntimeException("El lector con id: " + id + " tiene prestamos activos");
		}
	}

	@Override
	public List<Object[]> countPrestamosByLector() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Lector lector = lectorRepository.findByUsername(username);
		if (lector == null) {
			throw new UsernameNotFoundException("Lector no enonctrado: " + username);
		}
		//List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(lector.getRoles()));
		UserDetails userDetails = 
				org.springframework.security.core.userdetails.User.builder()
				.username(lector.getUsername())
				.password(lector.getPassword())
				.roles(lector.getRoles())
				.build();
		return userDetails;
	}

}
