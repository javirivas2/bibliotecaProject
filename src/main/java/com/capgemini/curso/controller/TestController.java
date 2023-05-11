package com.capgemini.curso.controller;

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

import com.capgemini.curso.model.Autor;
import com.capgemini.curso.model.Lector;
import com.capgemini.curso.model.RestriccionesPrestamo;
import com.capgemini.curso.service.AutorService;
import com.capgemini.curso.service.LectorService;

@Controller
public class TestController {

	@Autowired
	private LectorService lectorService;
	
	@Autowired
	private AutorService autorService;


	// Probamos a hacer 3 prestamos con un lector
	@GetMapping("/simularPrestamoA")
	public void simularPrestamoA() {
		List<Lector> lectores = lectorService.getAllLectores();
		Lector lector = lectores.get(0);

		// Un libro que no existe deberia fallar
		try {
			lectorService.prestar(lector.getId(), -1, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo inexistene");
		}

		// Un Lector que no existe deberia fallar
		try {
			lectorService.prestar(-1, 6, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo inexistene");
		}

		// Dos del mismo
		lectorService.prestar(lector.getId(), 2, LocalDate.now());
		lectorService.prestar(lector.getId(), 2, LocalDate.now());

		// Un Libro sin copias disponibles deberia fallar
		try {
			lectorService.prestar(lector.getId(), 2, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo no disponible");
		}

		// Uno de otro
		lectorService.prestar(lector.getId(), 5, LocalDate.now());

		// Al probar un cuarto deberia dar una excepcion
		try {
			lectorService.prestar(lector.getId(), 6, LocalDate.now());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fallo de mas");
		}

	}

	@GetMapping("/deshacerPrestamoA")
	public void deshacerPrestamoA() {
		List<Lector> lectores = lectorService.getAllLectores();
		Lector lector = lectores.get(0);

		lectorService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), LocalDate.now());
		lectorService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), LocalDate.now());
		lectorService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), LocalDate.now());
	}

	// Probamos el funcionamiento de las multas
	@GetMapping("/simularMultas")
	public void simularMultas() {
		List<Lector> lectores = lectorService.getAllLectores();
		Lector lector = lectores.get(1);

		// Añadimos un libro al prestamo
		lectorService.prestar(lector.getId(), 1, LocalDate.now());

		// Lo devolvemos mas alla del limite de dias
		int diasDeMas = 5;
		int diasDeMulta = diasDeMas * 2;
		LocalDate pastDate = LocalDate.now().plusDays(RestriccionesPrestamo.DIAS_MAX + diasDeMas);
		lectorService.devolver(lector.getId(), lector.getPrestamosActivos().get(0).getId(), pastDate);

		// Deberiamos tener una multa
		System.out.println("*****Multas*****");
		System.out.println("Tenemos multa: " + lector.getMulta() != null);
		System.out.println("Fin de la multa: " 
				+ pastDate.plusDays(diasDeMulta)
				+ " | " + lector.getMulta().getfFin());
		
		//No podemos coger mas prestamos hoy
		try {
			lectorService.prestar(lector.getId(), 5, LocalDate.now());
		}catch (Exception e) {
			System.err.println("Fallo al coger prestado");
		}
		
		//Podemos coger una vez pasada la multa
		lectorService.prestar(lector.getId(), 5, pastDate.plusDays(diasDeMulta + 1));
	}
	

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
