package com.empleos.controller;

import java.util.List;

import com.empleos.model.Vacante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.empleos.model.Categoria;
import com.empleos.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {

	//ponemos el nombre del servicio CategoriaServiceJpa
	//comentamos porque usamos @primary en el servicio
	//@Qualifier("categoriaServiceJpa") hay que ponerla en donde se inyecta
	// Inyectamos una instancia desde nuestro ApplicationContext
	@Autowired
	private ICategoriasService serviceCategorias;


	/**
	 * Metodo que muestra la lista de categorias sin paginacion
	 * @param model
	 * @param-page
	 * @return
	 */
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Categoria> lista = serviceCategorias.buscarTodas();
    	model.addAttribute("categorias", lista);
		return "categorias/listCategorias";		
	}


	/**
	 * Metodo que muestra la lista de categorias con paginacion
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Categoria> lista = serviceCategorias.buscarTodas(page);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}

	/**
	 * Método para renderizar el formulario para crear una nueva Categoría
	 * @param categoria
	 * @return
	 */
	@GetMapping("/create")
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}


	/**
	 * Método para guardar una Categoría en la base de datos
	 * @param categoria
	 * @param result
	 * @param-model
	 * @param attributes
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(Categoria categoria, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()){		
			System.out.println("Existieron errores");
			return "categorias/formCategoria";
		}	
		
		// Guardamos el objeto categoria en la bd
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categoría fueron guardados!");
		//return "redirect:/categorias/index";
		return "redirect:/categorias/indexPaginate";
	}


	/**
	 * Método para renderizar el formulario para editar una Categoría
	 * @param idCategoria
	 * @param model
	 * @return
	 * //model.addAtribute(atributo: , valor: objeto categoria)
	 */

	/*
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model){
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
		model.addAttribute("categoria", categoria);  //envia al formulario

		//pasamos el model al metodo de setGenericos(Model model)
		//eliminar porque registra doble registro
		//model.addAttribute("categoria", serviceCategorias.buscarTodas() ); //busca en el formulario en el combo categorias

		return "categorias/formCategoria";
	}

*/


	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model){
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
		model.addAttribute("categoria", categoria);  //envia al formulario
		return "categorias/formCategoria";
	}


	//borra con JPA
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
		System.out.println("Borrando categoria con id: " + idCategoria);
		serviceCategorias.eliminar(idCategoria);
		attributes.addFlashAttribute("msg", "La categoria fue eliminada");
		//return "redirect:/categorias/index";
		return "redirect:/categorias/indexPaginate";

	}

	
}
