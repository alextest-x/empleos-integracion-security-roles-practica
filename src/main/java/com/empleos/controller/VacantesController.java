package com.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.empleos.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empleos.model.Vacante;
import com.empleos.service.ICategoriasService;
import com.empleos.service.IVacantesService;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

	//inyectando desde el properties ponemos la propiedad que esta en el properties
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	@Qualifier("categoriaServiceJpa")
	private ICategoriasService serviceCategorias; 
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
    	model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}


	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}


	//pasamos como parametro un objeto de tipo vacante
	@GetMapping("/create")
		public String crear(@ModelAttribute Vacante vacante) {	
	    //public String crear(Vacante vacante, Model model) {
	    //se creo un metodo setGenericos(Model model) ahi la pasamos
		//model.addAttribute("categorias", serviceCategorias.buscarTodas() );
		return "vacantes/formVacante";
	}


    //agregamos al metodo @RequestParam("archivoImagen") MultipartFile multiPart
	//public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes) {
	@PostMapping("/save")
	public String guardar(@ModelAttribute Vacante vacante, BindingResult result,  Model model, 
						  @RequestParam("archivoImagen") MultipartFile multiPart, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			//for (ObjectError error: result.getAllErrors()){
			//	System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
			//}

           	System.out.println("Existieron errores");
			return "vacantes/formVacante";
		}

		if (!multiPart.isEmpty()) {
            //String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			//se comenta porque la ruta se puso en el properties
			//String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null){ // La imagen si se subio
                //Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}


		serviceVacantes.guardar(vacante);
		attributes.addFlashAttribute("msg", "Los datos de la vacante fueron guardados!");		
		System.out.println("Vacante: " + vacante);		
		//return "redirect:/vacantes/index"; 
		return "redirect:/vacantes/indexPaginate";		
	}


	/*
	@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion, 
			@RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha, @RequestParam("destacado") int destacado, 
			@RequestParam("salario") double salario, @RequestParam("detalles") String detalles) {
		System.out.println("Nombre Vacante: " + nombre);
		System.out.println("Descripcion: " + descripcion);
		System.out.println("Estatus: " + estatus);
		System.out.println("Fecha Publicaci�n: " + fecha);
		System.out.println("Destacado: " + destacado);
		System.out.println("Salario Ofrecido: " + salario);
		System.out.println("detalles: " + detalles);
		return "vacantes/listVacantes"; 
	}
	*/

	/*
    @GetMapping("/delete/")
    public String eliminar(@RequestParam("id") int idVacante, Model model) {
        System.out.println("Borrando vacante con id: " + idVacante);
        model.addAttribute("id", idVacante);
        return "mensaje";
    }
    */


	//borra con JPA
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {
		System.out.println("Borrando vacante con id: " + idVacante);
		serviceVacantes.eliminar(idVacante);
		attributes.addFlashAttribute("msg", "La vacante fue eliminada");
		return "redirect:/vacantes/indexPaginate";

	}


	//editar el formulario

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model){
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		//model.addAtribute(atributo: , valor: objeto vacante)
		model.addAttribute("vacante", vacante);  //envia al formulario

		//pasamos el model al metodo de setGenericos(Model model)
		//model.addAttribute("categorias", serviceCategorias.buscarTodas() ); //busca en el formulario en el combo categorias
		return "vacantes/formVacante";
	}


    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model) {

        Vacante vacante = serviceVacantes.buscarPorId(idVacante);

        System.out.println("Vacante: " + vacante);
        model.addAttribute("vacante", vacante);

        // Buscar los detalles de la vacante en la BD...
        return "detalle";
    }


	//metodo donde
	//se agrega la modelo un atributo categorias que tiene el estado de categorias
	@ModelAttribute //agrega datos al modelo con un metodo generico.
	public void setGenericos(Model model){
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
	}



	/*
	   @InitBinder permite crear metodos para configurar el DataBinding directamente en el controlador
		para uno tipo de datos que esten en la vista del input formulario
	    como las fechas
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");             //false
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
