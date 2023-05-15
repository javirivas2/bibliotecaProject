package com.capgemini.curso.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.curso.model.Autor;
import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.EstadoCopia;
import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Prestamo;
import com.capgemini.curso.model.TipoLibro;
import com.capgemini.curso.repository.AutorRepository;
import com.capgemini.curso.repository.CopiaRepository;
import com.capgemini.curso.repository.LectorRepository;
import com.capgemini.curso.repository.LibroRepository;
import com.capgemini.curso.repository.PrestamoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class DBInitiliazer {
	private final static Logger logger = LoggerFactory.getLogger(DBInitiliazer.class);

	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private LectorRepository lectorRepository;
	@Autowired
	private CopiaRepository copiaRepository;
	@Autowired
	private PrestamoRepository prestamoRepository;

	@PostConstruct
	public void initDB() {
		logger.info("Init DB....");
		List<Autor> autores = new ArrayList<>(
				Arrays.asList(new Autor("Jose Luis Borges", "Argentino", LocalDate.of(1899, Month.JUNE, 24)),
						new Autor("Gabriel García Marquez", "Colombiano", LocalDate.of(1927, Month.FEBRUARY, 27)),
						new Autor("Ray Loriga", "Española", LocalDate.of(1967, Month.MARCH, 5)),
						new Autor("Alfonsina Storni", "Argentina", LocalDate.of(1892, Month.MAY, 29)),
						new Autor("Alejandra Pitarnik", "Argentina", LocalDate.of(1936, Month.APRIL, 29)),
						new Autor("Eduardo Galeano", "Uruguayo", LocalDate.of(1940, Month.SEPTEMBER, 3))));

		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Calendar calendar = Calendar.getInstance();

		for (Autor a : autores) {
			autorRepository.save(a);
		}
		logger.info("Autores insertados correctamente");

		List<Autor> autoresexistentes = autorRepository.findAll();

		List<Libro> libros = new ArrayList<>(Arrays.asList(
				new Libro("Ficciones", TipoLibro.ENSAYO, "Sur", 1944, autoresexistentes.get(0)),
				new Libro("Cien años de soledad", TipoLibro.NOVELA, "Sudamericana", 1967, autoresexistentes.get(1)),
				new Libro("La velocidad de la luz", TipoLibro.NOVELA, "Alfaguara", 1992, autoresexistentes.get(2)),
				new Libro("Ocre", TipoLibro.POESIA, "Nosotros ", 1925, autoresexistentes.get(3)),
				new Libro("Los trabajos y las noches", TipoLibro.POESIA, "Ediciones Siglo XXI", 1965,
						autoresexistentes.get(4)),
				new Libro("El amor en los tiempos del cólera", TipoLibro.NOVELA, "Oveja Negra", 1985,
						autoresexistentes.get(1)),
				new Libro("Las venas abiertas de América Latina", TipoLibro.ENSAYO, "Ediciones Siglo XXI", 1971,
						autoresexistentes.get(5)),
				new Libro("Memoria del fuego", TipoLibro.NOVELA, "Ediciones Siglo XXI", 1982,
						autoresexistentes.get(5))));

		for (Libro libro : libros) {
			libroRepository.save(libro);
		}
		logger.info("Libros insertados correctamente");

		List<Libro> librosExistentes = libroRepository.findAll();

		List<Copia> ejemplares = new ArrayList<>(Arrays.asList(new Copia(EstadoCopia.PRESTADO, librosExistentes.get(0)),
				new Copia(EstadoCopia.PRESTADO, librosExistentes.get(0)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(0)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(1)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(1)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(2)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(3)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(3)),
				new Copia(EstadoCopia.REPARACION, librosExistentes.get(3)),
				new Copia(EstadoCopia.REPARACION, librosExistentes.get(3)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(4)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(4)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(5)),
				new Copia(EstadoCopia.BIBLIOTECA, librosExistentes.get(6))));

		for (Copia ejemplar : ejemplares) {
			copiaRepository.save(ejemplar);
		}
		List<Copia> copiasExistentes = copiaRepository.findAll();

		logger.info("Libros insertados correctamente");

		List<Lector> lectores = new ArrayList<>(Arrays.asList(
				new Lector("Ricardo Darín", "22-1234-5678", "c/Santa Fe 1860", "darin", "darin", "USER"),
				new Lector("Dario Grandinetti", "23-1234-5678", "c/México 564", "grandinetti", "grandinetti", "USER"),
				new Lector("Juan José Campanella", "15-1234-5678", "c/Corrientes 1439", "campanella", "campanella",
						"USER"),
				new Lector("Guillermo Francella", "24-1234-5678", "c/Lavalle 3025", "francella", "francella", "USER"),
				new Lector("Federico Luppi", "11-1234-5678", "c/Solís 475", "luppi", "luppi", "USER"),
				new Lector("Umberto Eco", "11-6634-5678", "c/Biblioteca", "admin", "admin", "ADMIN")));

		for (Lector lector : lectores) {
			lectorRepository.save(lector);
		}
		logger.info("Lectores insertados correctamente");

		List<Lector> lectoresExistentes = lectorRepository.findAll();

		List<Prestamo> prestamos = new ArrayList<>(Arrays.asList(
				new Prestamo(LocalDate.of(2023, 5, 5), lectoresExistentes.get(0), copiasExistentes.get(0), true),
				new Prestamo(LocalDate.of(2023, 5, 7), lectoresExistentes.get(0), copiasExistentes.get(1), true)));

		for (Prestamo prestamo : prestamos) {
			prestamoRepository.save(prestamo);
		}
		logger.info("Prestamos insertados correctamente");

	}
}
