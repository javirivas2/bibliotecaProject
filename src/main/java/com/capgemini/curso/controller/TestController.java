package com.capgemini.proyecto.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.proyecto.model.Autor;
import com.capgemini.proyecto.model.Lector;
import com.capgemini.proyecto.service.AutorService;
import com.capgemini.proyecto.service.LectorService;
import com.capgemini.proyecto.service.MultaService;

@Controller
public class TestController {

	@Autowired
	private LectorService lectorService;

	@Autowired
	private MultaService multaService;

	@Autowired
	private AutorService autorService;

	@GetMapping("/")
	public String viewHomePage() {

		return "testfragments";
	}

	@GetMapping("/add")
	public String showFormNuevoLibro(Model model) {
		return "nuevo_libro";
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
		return "viewlectores";
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
		return "lectoresPrestamosView";
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

//	@RequestMapping("/eliminar/{id}")
//	public String eliminar(@PathVariable("id") Long id) {
//		lectorService.deleteById(id);
//		return "redirect:/verlectores";
//	}
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
		return "modificarlector";
	}

	// Controlador para los autores
	@GetMapping("/verautores")
	public String viewAutores(Model model) {
		model.addAttribute("autores", autorService.getAllAutores());
		return "viewautores";
	}

	@RequestMapping("/insertarautor")
	public String insertarAutor(Model model) {
		model.addAttribute("autor", new Autor());
		return "nuevo_autor";
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
		model.addAttribute("autor", autorService.getAutorById(id));
		return "modificarautor";
	}

}
