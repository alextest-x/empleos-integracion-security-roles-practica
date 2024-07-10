package com.empleos.service;

import com.empleos.model.Vacante;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVacantesService {

	List<Vacante> buscarTodas();

	Vacante buscarPorId(Integer idVacante);

	void guardar(Vacante vacante);

	List<Vacante> buscarDestacadas();

	void eliminar(Integer idVacante);


	//recibe como parametro el tipo de dato Example<Vacante>
	//metodo que hace el filtro a la base de datos de busqueda
	//consulta por muestra o Query Por Example en la base de datos
	//forma consulta sql donde las condiciones se forman dinamicamente en base de clase de modelo
	// que pasamos como parametro que es la Clase Vacante
	// los filtros que forma en la vista de los atributos que no sean nulos
	List<Vacante> buscarByExample(Example<Vacante> example);

	// El método findAll,estasobrecargado con un parámetro de tipo Pageable.
	//para paginar la lista de vacantes
	Page<Vacante> buscarTodas(Pageable page);



}
