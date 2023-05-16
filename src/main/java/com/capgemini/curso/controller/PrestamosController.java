package com.capgemini.curso.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Prestamo;
import com.capgemini.curso.service.GestionReservasPrestamosService;
import com.capgemini.curso.service.LectorService;
import com.capgemini.curso.service.LibroService;
import com.capgemini.curso.service.PrestamoService;

@Controller
public class PrestamosController {

	@Autowired
	private LectorService lectorService;

	@Autowired
	private LibroService libroService;
	
	@Autowired
	private PrestamoService	prestamoService;
	
	@Autowired
	private GestionReservasPrestamosService gestionReservasPrestamosService;

	@GetMapping("/verprestamos")
	public String viewPrestamos(Model model) {
		List<Prestamo> prestamos = prestamoService.getAllActivePrestamo();
		
		model.addAttribute("prestamos", prestamos);
		
		return "prestamos/view_prestamos";
	}

	@GetMapping("/prestamos/add")
	public String createPrestamo(Model model) {
		List<Lector> lectores = lectorService.getLectoresQuePuedenPrestamo(LocalDate.now());
		model.addAttribute("lectores", lectores);

		List<Libro> libros = libroService.getLibrosDisponibles();
		model.addAttribute("libros", libros);

		return "prestamos/nuevo_prestamo";
	}

	@PostMapping("/prestamos/save_prestamo")
	public String savePrestamo(@RequestParam("lector") int idLector, @RequestParam("libro") int idLibro, Model model) {

		try { // Intentamos hacer el prestamo
			gestionReservasPrestamosService.prestar(idLector, idLibro, LocalDate.now());
		} catch (RuntimeException e) { // Si algo sale mal, damos el error y volvemos
			model.addAttribute("error", e.getMessage());
			return createPrestamo(model);
		}

		return viewPrestamos(model);
	}
	
	@GetMapping("/prestamos/devolver/{id}")
	public String devolver(@PathVariable(value = "id") int idPrestamo, Model model) {
		Prestamo prestamo = prestamoService.getPrestamoById(idPrestamo);
		
		Lector lector = prestamo.getLector();
		Libro libro = prestamo.getCopia().getEjemplar();
		
		model.addAttribute("prestamo", prestamo);
		model.addAttribute("lector", lector);
		model.addAttribute("libro", libro);

		return "prestamos/devolucion";
	}

	@PostMapping("/prestamos/devolver_prestamo")
	public String devolverPrestamo(@RequestParam("lector") int idLector, @RequestParam("prestamo") int idPrestamo, Model model) {

		try { // Intentamos hacer el prestamo
			gestionReservasPrestamosService.devolver(idLector, idPrestamo, LocalDate.now());
		} catch (RuntimeException e) { // Si algo sale mal, damos el error y volvemos
			model.addAttribute("error", e.getMessage());
			return devolver(idLector, model);
		}

		return viewPrestamos(model);
	}
}
