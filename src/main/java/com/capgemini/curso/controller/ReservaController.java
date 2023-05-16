package com.capgemini.curso.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.model.Reserva;
import com.capgemini.curso.service.GestionReservasPrestamosService;
import com.capgemini.curso.service.LectorService;
import com.capgemini.curso.service.LibroService;
import com.capgemini.curso.service.ReservaService;

@Controller
public class ReservaController {
	@Autowired
	private ReservaService reservaService;

	@Autowired
	private LibroService libroService;

	@Autowired
	private LectorService lectorService;

	@Autowired
	private GestionReservasPrestamosService gestionReservasPrestamosService;
	
	@GetMapping("/verReservas")
	public String viewReservas(Model model) {

		model.addAttribute("reservas", reservaService.findReservasByFechaAndLector());

		return "reservas/ver_reservas";
	}

	@GetMapping("reservas/formaddReserva")
	public String showFormAddReservas(Model model) {
		Reserva reserva = new Reserva();
		model.addAttribute("reserva", reserva);
		model.addAttribute("reservas", reservaService.getAllReservas());

		return "reservas/nueva_reserva";
	}

	@GetMapping("/reserva/add")
	public String createReservaConLibroYLector(@RequestParam Long idLector, @RequestParam Long idLibro, Model model) {
		Lector lector = lectorService.getLectorById(idLector);
		model.addAttribute("lector", lector);
		
		Libro libro = libroService.getLibroById(idLibro);
		model.addAttribute("libro", libro);

		return "reservas/confirmacion_reserva";
	}

	@PostMapping("/addreserva")
	public String addReserva(@ModelAttribute Reserva reserva,@RequestParam("lector") long idLector,@RequestParam("libro") long idLibro,Model model) {
		try {
			gestionReservasPrestamosService.reservar(idLibro, idLector);

		}catch(RuntimeException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "redirect:/verReservas";
	}
}
