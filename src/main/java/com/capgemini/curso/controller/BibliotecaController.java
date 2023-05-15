package com.capgemini.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.capgemini.curso.model.Copia;
import com.capgemini.curso.model.EstadoCopia;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.service.AutorService;
import com.capgemini.curso.service.CopiaService;
import com.capgemini.curso.service.LibroService;

@Controller
public class BibliotecaController {

	@Autowired
	private LibroService libroService;
	@Autowired
	private AutorService autorService;
	@Autowired
	private CopiaService copiaService;

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

	/**
	 * Controlador para las copias
	 */

	/**
	 * Metodo para ver las copias de cada libro y su estado mostrandolo en una vista
	 * 
	 * @param idLibro
	 * @param model
	 * @return
	 */
	@GetMapping("/libros/{idLibro}/vercopias")
	public String verCopiasDeLibro(@PathVariable("idLibro") Long idLibro, Model model) {
		// obtenemos el libro por su ID
		Libro libro = libroService.getLibroById(idLibro);

		// Comprobamos que ese libro existe
		if (libro != null) {
			// obtenemos la lista de copias del libro y agregmos el libro y la lista de
			// copias al modelo
			List<Copia> copias = libro.getEjemplares();
			model.addAttribute("libro", libro);
			model.addAttribute("copias", copias);
			return "libros/vercopias";
		} else {
			// Redirigimos a la list de libros en el caso deq eu no exista
			return "redirect:/verLibros";
		}
	}

	@GetMapping("/libros/{id}/insertarcopia")
	public String insertarCopia(@PathVariable("id") Long idLibro, Model model) {
		// obtenemos el libro por su ID
		Libro libro = libroService.getLibroById(idLibro);
		model.addAttribute("libro", libro);
		model.addAttribute("copia", new Copia());
		model.addAttribute("id", idLibro);
		return "libros/nueva_copia";
	}

	@RequestMapping(value = "/addcopia", method = RequestMethod.POST)
	public String addcopia(@ModelAttribute Copia copia, @RequestParam("estadoCopia") EstadoCopia estadoCopia,
			@RequestParam("ejemplar.id") Long ejemplarId, RedirectAttributes redirectAttributes) {
		Libro ejemplar = libroService.getLibroById(ejemplarId);
		copia.setEjemplar(ejemplar);
		copia.setEstadoCopia(estadoCopia);
		copiaService.saveCopia(copia);
		Long idLibro = ejemplar.getId();// Obtenemos el ID del libro
		redirectAttributes.addAttribute("idLibro", idLibro);
		// model.addAttribute("idLibro",idLibro);
		return "redirect:/libros/{idLibro}/vercopias";

	}

	@GetMapping("/libros/{idLibro}/cambioestado/{idCopia}")
	public String mostrarFormularioCambioEstado(@PathVariable("idLibro") Long idLibro,
			@PathVariable("idCopia") Long idCopia, Model model) {
		model.addAttribute("idLibro", idLibro);
		model.addAttribute("idCopia", idCopia);
		return "libros/cambioestadocopia";
	}
}
