package com.capgemini.curso.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.curso.model.Autor;
import com.capgemini.curso.service.AutorService;

@Controller
public class AutorController {

	@Autowired
	private AutorService autorService;

	@GetMapping("/verautores")
	public String viewAutores(Model model) {
		model.addAttribute("autores", autorService.getAllAutores());
		return "autores/viewautores";
	}

	@RequestMapping("/insertarautor")
	public String insertarAutor(Model model) {
		model.addAttribute("autor", new Autor());
		return "autores/nuevo_autor";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/addautor", method = RequestMethod.POST)
	public String addautor(@ModelAttribute Autor autor) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String fechaNacimientoStr = autor.getFechaNacimiento().format(formatter); // Obtén la fecha en formato de cadena
		LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formatter); // Convierte la cadena a LocalDate
		autor.setFechaNacimiento(fechaNacimiento);
		autorService.saveAutor(autor);
		return "redirect:/verautores";
	}

	@RequestMapping("/modificarautor")
	public String modificarAutor(@RequestParam Long id, Model model) {
		Autor autor = autorService.getAutorById(id);
		String fechaNa = autor.getFechaNacimiento().toString();
		model.addAttribute("autor", autor);
		model.addAttribute("fecha", fechaNa);
		return "autores/modificarautor";
	}
	@RequestMapping("/eliminarAutor/{id}")
	public String deleteAutor(@PathVariable(value = "id") long id, Model model) {
		try {
			this.autorService.deleteById(id);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return viewAutores(model);
		}

		return "redirect:/verautores";


	}

	public String eliminarAutor(@RequestParam Long id) {
		try {
			autorService.deleteById(id);
			return "redirect:/autores/viewautores";
		} catch (IllegalStateException e) {
			// Manejar la excepción en caso de que no se pueda eliminar el autor
			// Puedes agregar un mensaje de error al modelo y redirigir a la página de
			// autores
			return "redirect:/verautores";
		}
	}

}
