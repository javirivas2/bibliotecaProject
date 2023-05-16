package com.capgemini.curso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.service.GestionReservasPrestamosService;
import com.capgemini.curso.service.LectorService;
import com.capgemini.curso.service.LibroService;
import com.capgemini.curso.service.ReservaService;

@Controller
public class ReservaController {

	@Autowired
	private LectorService lectorService;
	
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private ReservaService reservaService;

	@Autowired
	private GestionReservasPrestamosService gestionReservasPrestamosService;
	
	@GetMapping("/verReservas")
	private String viewReservas(Model model) {
//		model.addAttribute("reservas", reservaService;
		return "reservas/verReservas";
	}

	@GetMapping("/reserva/add")
	public String createReserva(@RequestParam Long idLector, @RequestParam Long idLibro, Model model) {
		Lector lector = lectorService.getLectorById(idLector);
		model.addAttribute("lector", lector);
		
		Libro libro = libroService.getLibroById(idLibro);
		model.addAttribute("libro", libro);

		return "reservas/nueva_reserva";
	}

	@PostMapping("/reserva/save_reserva")
	public String savePrestamo(@RequestParam("lector") Long idLector, @RequestParam("libro") Long idLibro, Model model) {

		try { // Intentamos hacer la reserva
			gestionReservasPrestamosService.reservar(idLibro, idLector);
		} catch (RuntimeException e) { // Si algo sale mal, damos el error y volvemos
			model.addAttribute("error", e.getMessage());
			
			
			return createReserva(idLector, idLibro, model);
		}

		return viewReservas(model);
	}


}
