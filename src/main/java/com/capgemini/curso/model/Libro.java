package com.capgemini.curso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="libros")
public class Libro {
	
	private TipoLibro tipo;

}
