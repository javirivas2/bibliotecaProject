package com.capgemini.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Prestamo;
import com.capgemini.curso.service.LectorService;
import com.capgemini.curso.service.LibroService;

@Controller
public class LectorController {

	@Autowired
	private LectorService lectorService;

	@Autowired
	private LibroService libroService;

	@GetMapping("lector/prestamo/{id}")
	public String createPrestamoLector(@PathVariable(value = "id") int idLector, Model model) {
		Lector lector = lectorService.getLectorById(idLector);
		model.addAttribute("lector", lector);

		List<Libro> libros = libroService.getLibrosDisponibles();
		model.addAttribute("libros", libros);

		return "lectores/nuevo_prestamo";
	}

	
	@GetMapping("lector/devolver/{id}")
	public String devolverLector(@PathVariable(value = "id") int idLector, Model model) {
		Lector lector = lectorService.getLectorById(idLector);
		

		List<Prestamo> prestamos = lector.getPrestamosActivos();
		if(prestamos.isEmpty()) { //Si no hay prestamos volvemosy enseñamos error
			model.addAttribute("error", "Lector " + lector.getNombre() + " no tiene prestamos activos.");
			return viewLectores(model);
		}
		
		model.addAttribute("lector", lector);
		model.addAttribute("prestamos", prestamos);

		return "lectores/devolucion";
	}

	// Controlador para los lectores
		/**
		 * 
		 * @param model
		 * @return
		 */
		@GetMapping("/verlectores")
		public String viewLectores(Model model) {
			model.addAttribute("lectores", lectorService.getAllLectores());
			return "lectores/viewlectores";
		}

		/**
		 * 
		 * @param model
		 * @return
		 */
		@GetMapping("/lectoresprestamos")
		public String countPrestamosByLector(Model model) {
			List<Object[]> resultados = lectorService.countPrestamosByLector();
			model.addAttribute("resultados", resultados);
			return "lectores/lectoresPrestamosView";
		}


//		@RequestMapping("/eliminar/{id}")
//		public String eliminar(@PathVariable("id") Long id) {
//			lectorService.deleteById(id);
//			return "redirect:/verlectores";
//		}
		/**
		 * 
		 * @param id
		 * @return
		 */
		@RequestMapping("/eliminar")
		public String eliminar(@RequestParam Long id) {
			lectorService.deleteById(id);
			return "redirect:/verlectores";
		}

		/**
		 * 
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping("/modificar")
		public String modificar(@RequestParam Long id, Model model) {
			model.addAttribute("lector", lectorService.getLectorById(id));
			return "lectores/modificarlector";
		}
		

		/**
		 * 
		 * @param model
		 * @return
		 */
		@RequestMapping("/insertar")
		public String insertar(Model model) {
			model.addAttribute("lector", new Lector());
			return "/nuevo_lector";
		}

		/**
		 * 
		 * @param lector
		 * @return
		 */
		@RequestMapping(value = "/addlector", method = RequestMethod.POST)
		public String addlector(@ModelAttribute Lector lector) {
			lectorService.saveLector(lector);
			return "redirect:/verlectores";
		}
}
