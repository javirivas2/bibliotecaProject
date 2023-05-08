package com.capgemini.curso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.curso.model.Lector;
import com.capgemini.curso.service.LectorService;

@RestController
public class TestController {

	@Autowired
	private LectorService lectorService;
	
	@GetMapping("/test")
	public List<Lector> viewHomePage(Model model) {
		return lectorService.getAllLectores();
	}
	
}
