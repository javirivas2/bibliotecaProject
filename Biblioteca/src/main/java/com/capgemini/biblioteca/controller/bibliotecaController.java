package com.capgemini.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.capgemini.biblioteca.model.Libro;
import com.capgemini.biblioteca.service.LibroService;


@Controller
public class bibliotecaController {
	@Autowired
	private LibroService libroService;

	@GetMapping("/")
	public String verLibros(Model model) {
		model.addAttribute("libros", libroService.getAllLibros());
		return "verLibros";
	}

	
}
