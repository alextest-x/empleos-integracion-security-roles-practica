/**
 * Esta clase representa una entidad (un registro) en la tabla de Usuarios de la base de datos
 */
package com.empleos.model;

import jakarta.persistence.*;


import java.util.*;

@Entity
@Table(name = "Usuarios")
public class Usuario {
    //se implementa el security
    //public class Usuario  implements UserDetails {

	//private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment MySQL

	private Integer id;

	private String username;

	private String nombre;

	private String email;

	private String password;

	private Integer estatus;

	private Date fechaRegistro;

	/*

	@ManytoMany
	eager sirve para para especificar cuando hagamos una busquedad de un usuario
	en automatico se recupere en la misma consulta todos los perfilies que tenga el usuario

     joinTable confifura la tabla intermedia

	 joinColumns = @JoinColumn(name="idUsuario"),
	tabla intermedia sirve para representar de muchos a muchos entre la tabla perfil y usuarios
	ponemos primero la llave foranea que esta en la tabla usuarios y esta en la clase de modelo usuario

	inverseJoinColumns = @JoinColumn(name="idPerfil")
	llave foranea de la otra tabla que se esta relacionando el usuario
	el nombre de la llave foranea de la tabla intermedia UsuarioPerfil


    un usuario tiene varios perfiles por eso el atributo perfiles es de tipo lista
    para que acepte varios perfiles

     se agrega un metodo tipo helper para ayudar agregar perfiles a la lista
     para guaradar en el metodo


     EL orden de joinColumns debe ser en orden porque el atributo esta en esta clase usuario
     inverseJoinColumns va el nombre de la llave foranea de la otra tabla con la que se relaciona el usuario


	 */


	@ManyToMany(fetch=FetchType.EAGER) //persiste en el mismo momento todos los perfiles
	@JoinTable(name="UsuarioPerfil",
			   joinColumns = @JoinColumn(name="idUsuario"),       //llave foranea de la tabla UsuarioPerfil
			   inverseJoinColumns = @JoinColumn(name="idPerfil")  //llave foranea de la tabla UsuarioPerfil
			)
	private List<Perfil> perfiles;    //atributo perfiles porque un usuario va a tener varios perfiles




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
		

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}


	//metodo helper
	public void agregar(Perfil tempPerfil) {
		//si el perfil es nulo crea una lista
		//si no es nulo entonces la lista ya esta creada
		if (perfiles == null) {
			perfiles = new LinkedList<Perfil>();
		}
		//tempPerfil agrega perfil al usuario
		perfiles.add(tempPerfil);
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", email=" + email
				+ ", password=" + password + ", estatus=" + estatus + ", fechaRegistro=" + fechaRegistro + "]";
	}
	
}
