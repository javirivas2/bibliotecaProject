package com.capgemini.curso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.curso.model.Libro;
import com.capgemini.curso.service.AutorService;
import com.capgemini.curso.service.LibroService;

@Controller
public class BibliotecaController {

	@Autowired
	private LibroService libroService;
	@Autowired
	private AutorService autorService;

	@GetMapping("/verLibros")
	public String verLibros(Model model) {
		model.addAttribute("libros", libroService.getAllLibros());
		return "libros/verLibros";
	}

	@GetMapping("libro/edit/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
		Libro libro = this.libroService.getLibroById(id);
		model.addAttribute("autores", autorService.getAllAutores());
		model.addAttribute("libro", libro);

		return "libros/editLibro";
	}

	@PostMapping("libro/save")
	public String saveLibro(@ModelAttribute("libros") Libro libro, @RequestParam("autor") Long id) {
		libroService.saveLibro(libro);
		autorService.getAutorById(id);
		return "redirect:/verLibros";
	}

	@GetMapping("libro/delete/{id}")
	public String deleteLibro(@PathVariable(value = "id") long id) {
		this.libroService.deleteLibroById(id);
		return "redirect:/verLibros";
	}

	@GetMapping("libro/add")
	public String showNewLibroForm(Model model) {
		Libro libro = new Libro();
		model.addAttribute("libro", libro);
		model.addAttribute("autores", autorService.getAllAutores());

		return "libros/insertLibro";
	}
}
