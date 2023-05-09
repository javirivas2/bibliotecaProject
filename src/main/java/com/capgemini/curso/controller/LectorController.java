package com.capgemini.curso.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.Libro;
import com.capgemini.curso.service.LectorService;
import com.capgemini.curso.service.LibroService;

@Controller
public class LectorController {
	
	@Autowired
	private LectorService lectorService;
	
	@Autowired
	private LibroService libroService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "nombre", "asc", model);
	}

	@GetMapping("/page/{pageNum}")
	public String findPaginated(@PathVariable(value = "pageNum") int pageNum,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model) {
		int pageSize = 4;
		Page<Lector> page = lectorService.findAllPage(pageNum, pageSize, sortField, sortDir);
		List<Lector> listLectores = page.getContent();
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc")? "desc":"asc");
		model.addAttribute("listReaders", listLectores);
		
		return "view_lectores";		
	}
	
	@GetMapping("/prestamo/{id}")
	public String createPrestamo(@PathVariable(value = "id") int idLector, Model model) {
		Lector lector = lectorService.getLectorById(idLector);
		model.addAttribute("lector", lector);
		
		List<Libro> libros = libroService.getLibrosDisponibles();
		model.addAttribute("libros", libros);
		
		return "nuevo_prestamo";
	}
	
	@PostMapping("/save_prestamo")
	public String savePrestamo(@RequestParam("lector") int idLector, @RequestParam("libro") int idLibro, Model model) {
		lectorService.prestar(idLector, idLibro, LocalDate.now());
		
		return findPaginated(1, "nombre", "asc", model);
	}

}
