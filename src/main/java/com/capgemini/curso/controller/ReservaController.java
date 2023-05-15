package com.capgemini.curso.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.capgemini.curso.model.Libro;
import com.capgemini.curso.service.LibroService;
import com.capgemini.curso.service.ReservaService;

@Controller
public class ReservaController {
	@Autowired
	private ReservaService reservaService;

	@Autowired
	private LibroService libroService;

	@GetMapping("/verReservas")
	public String viewReservas(Model model) {
		
		model.addAttribute("reservas", reservaService.findReservasByFechaAndLector());

		return "reservas/ver_reservas";
	}
}
