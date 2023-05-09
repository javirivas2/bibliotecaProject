package com.capgemini.curso.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Enumerated(EnumType.STRING)
	private TipoLibro tipoLibro;
	
	@Column(name = "editorial")
	private String editorial;
	
	@Column(name = "año_publicacion")
	private int anyo;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;
	
	@OneToMany(mappedBy = "ejemplar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Copia> ejemplares;

	public Libro() {
	}

	public Libro(Long id, String titulo, TipoLibro tipoLibro, String editorial, int anyo, Autor autor,
			List<Copia> ejemplares) {
		this.Id = id;
		this.titulo = titulo;
		this.tipoLibro = tipoLibro;
		this.editorial = editorial;
		this.anyo = anyo;
		this.autor = autor;
		this.ejemplares = ejemplares;
	}

	public Libro(String titulo, TipoLibro tipoLibro, String editorial, int anyo, Autor autor) {
		this.titulo = titulo;
		this.tipoLibro = tipoLibro;
		this.editorial = editorial;
		this.anyo = anyo;
		this.autor = autor;
	}

	public List<Copia> getEjemplaresDisponibles(){
		List<Copia> disponibles = new ArrayList<>();
		
		for (Copia copia : ejemplares) {
			if(copia.isDisponible()) {
				disponibles.add(copia);
			}
		}
		
		return disponibles;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoLibro getTipoLibro() {
		return tipoLibro;
	}

	public void setTipoLibro(TipoLibro tipoLibro) {
		this.tipoLibro = tipoLibro;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public List<Copia> getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(List<Copia> ejemplares) {
		this.ejemplares = ejemplares;
	}

	@Override
	public String toString() {
		return "Libro [Id=" + Id + ", titulo=" + titulo + ", tipoLibro=" + tipoLibro + ", editorial=" + editorial
				+ ", anyo=" + anyo + ", autor=" + autor + ", ejemplares=" + ejemplares + "]";
	}

}
