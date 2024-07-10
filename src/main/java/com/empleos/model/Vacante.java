package com.empleos.model;

import jakarta.persistence.*;

import java.util.Date;
//import jakarta.persistence.Transient;

@Entity
@Table(name="Vacantes")
public class Vacante {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nombre; // Nombre de la oferta de trabajo.
	private String descripcion; // Descripcion corta de la oferta de trabajo
	private Date fecha; // Fecha de publicacion de la oferta del trabajo.
	private Double salario; // Salario aproximado que se ofrece por el trabajo.
	private String estatus; // Valores [Creado, Aprobado, Eliminado].
	private Integer destacado; // Valores [0, 1]. 0: No se muestra en la pag. principal | 1: Se muestra en la pagina principal.
	private String imagen="no-image.png"; // Nombre del archivo de la imagen del logotipo de la empresa que ofrece el trabajo.
	private String detalles; // Detalles de la oferta de trabajo.


	//@Transient
	@OneToOne  //uno a uno de la clase modelo vacante a la clase de modelo categoria
	@JoinColumn(name="idCategoria") //hace la union entre las dos tablas (llave foranea de la tabla)  foreignKey en la tabla de Vacantes	
	private Categoria categoria;   // Categoria a la que pertence la oferta de trabajo

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Integer getDestacado() {
		return destacado;
	}

	public void setDestacado(Integer destacado) {
		this.destacado = destacado;
	}


	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
	

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	//reset a la variable de imagen para que no busque por imagen cuando busca
	//por el objeto Vacante(porque trae el arreglo)
	public void reset(){
		this.imagen= null;
	}
	@Override
	public String toString() {
		return "Vacante [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fecha=" + fecha
				+ ", salario=" + salario + ", destacado=" + destacado + ", imagen=" + imagen + ", estatus=" + estatus
				+ ", detalles=" + detalles + ", categoria=" + categoria + "]";
	}

	
	

}
