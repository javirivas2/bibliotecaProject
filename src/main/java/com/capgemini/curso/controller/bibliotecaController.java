package com.capgemini.curso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.curso.model.Autor;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.service.AutorService;
import com.capgemini.curso.service.LibroService;


@Controller
public class bibliotecaController {
	@Autowired
	private LibroService libroService;
	@Autowired
	private AutorService autorService;
	

	@GetMapping("/")
	public String verLibros(Model model) {
		model.addAttribute("libros", libroService.getAllLibros());
		return "verLibros";
	}
	
	@GetMapping("/edit/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
		Libro libro = this.libroService.getLibroById(id);
		Autor autor = this.autorService.getAutorById(id);
		model.addAttribute("autores", autorService.getAllAutores());
		model.addAttribute("libro", libro);
		return "editLibro";
	}
	@PostMapping("/save")
	public String saveLibro(@ModelAttribute("libros") Libro libro, @RequestParam("autor") Long id) {
		libroService.saveLibro(libro);
		autorService.getAutorById(id);
		return "redirect:/";
	}
	@GetMapping("/delete/{id}")
	public String deleteLibro(@PathVariable(value = "id") long id) {
		this.libroService.deleteLibroById(id);
		return "redirect:/";
	}
	@GetMapping("/add")
	public String showNewLibroForm(Model model) {
		Libro libro=new Libro();
		model.addAttribute("libro", libro);
		model.addAttribute("autores", autorService.getAllAutores());
		return "insertLibro";
	}
}
