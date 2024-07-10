package com.empleos.service;

import java.util.List;

import com.empleos.model.Categoria;
import com.empleos.model.Vacante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/** Ejercicio: Implementar método para registrar un usuario nuevo.

 * 	1. Usar la plantilla del archivo formRegistro.html
 *
 * 	2. El método para mostrar el formulario para registrar y el método para guardar el usuario deberá
 * 	   estar en el Controlador HomeController.
 *
 * 	3. Al guardar el usuario se le asignará el perfil USUARIO y la fecha de Registro
 * 	   sera la fecha actual del sistema.
 * @param_usuario
 */



public interface ICategoriasService {

	void guardar(Categoria categoria);

	List<Categoria> buscarTodas();

	Categoria buscarPorId(Integer idCategoria);

	void eliminar(Integer idCategoria);

	// El método findAll,estasobrecargado con un parámetro de tipo Pageable.
	//para paginar la lista de vacantes
	Page<Categoria>buscarTodas(Pageable page);


}