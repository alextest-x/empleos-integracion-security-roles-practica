package com.empleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.empleos.model.Solicitud;
import com.empleos.model.Usuario;
import com.empleos.model.Vacante;
import com.empleos.service.ISolicitudesService;
import com.empleos.service.IUsuarioService;
import com.empleos.service.IVacantesService;
import com.empleos.util.Utileria;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	/**
	 * Inyectando desde el properties ponemos la propiedad que esta en el properties
	 * EJERCICIO: Declarar esta propiedad en el archivo application.properties. El valor sera el directorio
	 * en donde se guardarán los archivos de los Curriculums Vitaes de los usuarios.
	 */

	@Value("${empleosapp.ruta.cv}")
	private String ruta;

	// Inyectamos una instancia desde nuestro ApplicationContext
    @Autowired
	private ISolicitudesService serviceSolicitudes;

    // Inyectamos una instancia desde nuestro ApplicationContext
    @Autowired
	private IVacantesService serviceVacantes;
    
    @Autowired
   	private IUsuarioService serviceUsuarios;


    /**
	 * Metodo que muestra la lista de solicitudes sin paginacion
	 * Seguridad: Solo disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
    @GetMapping("/index") 
	public String mostrarIndex(Model model) {
		List<Solicitud> lista = serviceSolicitudes.buscarTodas();
        model.addAttribute("solicitudes", lista);
		// EJERCICIO
		return "solicitudes/listSolicitudes";
		
	}
    
    /**
	 * Metodo que muestra la lista de solicitudes con paginacion
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Solicitud> lista = serviceSolicitudes.buscarTodas(page);
		model.addAttribute("solicitudes", lista);
		// EJERCICIO
		return "solicitudes/listSolicitudes";
		
	}
    
	/**
	 * Método para renderizar el formulario para aplicar para una Vacante
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@GetMapping("/create/{idVacante}")
	public String crear(Solicitud solicitud, @PathVariable Integer idVacante, Model model) {
		// Traemos los detalles de la Vacante seleccionada para despues mostrarla en la vista
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		return "solicitudes/formSolicitud";
	}
	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	 
	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * @param solicitud
	 * @param result
	 * @param model
	 * @param session
	 * @param multiPart
	 * @param attributes
	 * @return
	 */
 
	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, Model model, HttpSession session,
			@RequestParam("archivoCV") MultipartFile multiPart, RedirectAttributes attributes, Authentication authentication) {
		
		// Recuperamos el username que inicio sesión
		String username = authentication.getName();


		if (result.hasErrors()) {
			System.out.println("Existieron errores");
			//for (ObjectError error: result.getAllErrors()){
			//	System.out.println("Ocurrio un error: "+ error.getDefaultMessage());
			//}

			return "solicitudes/formSolicitud";
		}

		if (!multiPart.isEmpty()) {
			//String ruta = "/empleos/files-cv/"; // Linux/MAC
			//se comenta porque la ruta se puso en el properties
			//String ruta = "c:/empleos/files-cv/"; // Windows
			String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreArchivo != null){ // El archivo (CV) si se subio	
				//Procesamos la variable nombreImagen
				solicitud.setArchivo(nombreArchivo); // Asignamos el nombre al archivo
			}
		}

         //Buscamos el objeto Usuario en BD	
	     Usuario usuario = serviceUsuarios.buscarPorUsername(username);
		solicitud.setUsuario(usuario); // Referenciamos la solicitud con el usuario 
		solicitud.setFecha(new Date());
        // Guadamos el objeto solicitud en la bd
		serviceSolicitudes.guardar(solicitud);
		attributes.addFlashAttribute("msg", "Gracias por enviar tu CV!");
		System.out.println("Solicitud: " + solicitud);
	    //return "redirect:/solicitudes/index";
		return "redirect:/";
	}
	

	/**
	 * Método para eliminar una solicitud
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR. 
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attributes) {
	serviceSolicitudes.eliminar(idSolicitud);
	attributes.addFlashAttribute("msg", "La solicitud fue eliminada");

		// EJERCICIO
		return "redirect:/solicitudes/indexPaginate";
		
	}

			
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
