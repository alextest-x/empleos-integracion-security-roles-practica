package com.empleos.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.empleos.model.Perfil;
import com.empleos.model.Usuario;
import com.empleos.model.Vacante;
import com.empleos.service.ICategoriasService;
import com.empleos.service.IUsuarioService;
import com.empleos.service.IVacantesService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ICategoriasService serviceCategorias;

    @Autowired
    private IVacantesService serviceVacantes;

    @Autowired
    private IUsuarioService serviceUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

/*
   datos en duro
   
    @GetMapping("/tabla")
    public String mostrarTabla(Model model) {
        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes", lista);
        return "tabla";
    }


    @GetMapping("/detalle")
    public String mostrarDetalle(Model model) {
        Vacante vacante = new Vacante();
        //poniendo valores al objeto vacante
        vacante.setNombre("Ingeniero de comunicaciones");
        vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
        vacante.setFecha(new Date());
        vacante.setSalario(9700.0);
        //agregando el tipo de dato vacante al model para pasarlo a la vista
        model.addAttribute("vacante", vacante);
        return "detalle";
    }



    @GetMapping("/listado")
    public String mostrarListado(Model model) {
        List<String> lista = new LinkedList<String>();
        lista.add("Ingeniero  de Sistemas");
        lista.add("Auxiliar de Contabilidad");
        lista.add("Vendedor");
        lista.add("Arquitecto");

        model.addAttribute("empleos", lista);
        return "listado";
    }

*/

/*
    @GetMapping("/index")
    public String buscarTodos(Model model){
        List<Usuario> lista = serviceUsuario.buscarTodos();
        model.addAttribute("usuarios", lista);
        return "usuarios/listUsuarios";
    }
*/


    @GetMapping("/")
	public String mostrarHome() {
    //public String mostrarHome(Model model) {
        // se comenta porque se pasa al metodo setGenericos donde estan disponibles para todos los metodos del controlador
        // List<Vacante> lista = serviceVacantes.buscarTodas();
        // model.addAttribute("vacantes", lista);
        return "home";
    }


    //pasamos con la interface Authentication como parametro para recuperar informacion del usuario
    //que inicia sesion
    @GetMapping("/index")
    public String mostrarIndex(Authentication auth, HttpSession session){
        String username = auth.getName();
        System.out.println("Nombre del usuario:  " + username);

       for (GrantedAuthority rol: auth.getAuthorities()){
           System.out.println("rol.getAuthority() = " + rol.getAuthority());
       }

       if(session.getAttribute("usuario") == null){
           //creamos el objeto usuario para agregar a la sesion usuario
           // y lo ponemos en parametro de este metodo mostarIndex()
           Usuario usuario = serviceUsuario.buscarPorUsername(username);
           usuario.setPassword(null); //para que no salga la contraseña de la sesion
           System.out.println("usuario en session:  " + usuario);
           session.setAttribute("usuario", usuario);

       }

        return "redirect:/";
    }


    @GetMapping("/signup")
    public String registrarse(Usuario usuario) {
        return "formRegistro";
    }

    /*

    //registro de usuarios sin encriptar
    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {

        String username = usuario.getUsername();
        System.out.println("username = " + username);

        String password = usuario.getPassword();
        System.out.println("password = " + password);

        usuario.setEstatus(1); // Activado por defecto
        usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor

        // Creamos el Perfil que le asignaremos al usuario nuevo
        Perfil perfil = new Perfil();
        perfil.setId(3); //ponemos el perfil USUARIO
        usuario.agregar(perfil); //agregamos el perfil

        //Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
        serviceUsuario.guardar(usuario);
        attributes.addFlashAttribute("msg", "El registro fue guardado correctamente!");

        return "redirect:/usuarios/index";
    }
    */



    //registro de usuarios con encriptar no valida el login
    //porque no esta actualizando la tabla de authorities y users;
    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {

        String username = usuario.getUsername();
        System.out.println("username = " + username);

        //encriptando la contraseña
        String pwdPlano = usuario.getPassword();
        System.out.println("pwdPlano = " + pwdPlano);

        String pwdEncriptado = passwordEncoder.encode(pwdPlano);
        System.out.println("pwdEncriptado = " + pwdEncriptado);

        //test
        //boolean matches = passwordEncoder.matches(pwdPlano, pwdEncriptado);
        //System.out.println("matches = " + matches);


        usuario.setPassword(pwdEncriptado);

        usuario.setEstatus(1); // Activado por defecto
        usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha actual del servidor

        // Creamos el Perfil que le asignaremos al usuario nuevo
        Perfil perfil = new Perfil();
        perfil.setId(3); //ponemos el perfil USUARIO
        usuario.agregar(perfil); //agregamos el perfil

        //Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
        serviceUsuario.guardar(usuario);
        attributes.addFlashAttribute("msg", "Has sido registrado. ¡Ahora puedes ingresar al sistema!");

        //return "redirect:/usuarios/index";
		return "redirect:/login";
    }




    //data binding en el formulario ponemos como parametro a un objeto de tipo Vacante
    //@ModelAttribute("search") nombre del atributo que se hace el databindig
    @GetMapping("/search")
    public String buscar(@ModelAttribute("search") Vacante vacante, Model model){
        System.out.println("Buscado por: " + vacante);

        /* busca en la caja de texto la vacante en la vacante descripcion
            usamos la interface ExampleMatcher
           no usar el operador =  y en lugar utilizar la palabra reservada like % del sql
           where descripcion like '%?%'
           y la variable matcher la pasamos al example = Example.of(vacante, matcher);

        */
	    vacante.setEstatus("Aprobada");
        ExampleMatcher matcher = ExampleMatcher
                .matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());



        //la varibale example recibe como muestra un objeto de tipo Vacante
        // y le pasamos como parametro el objeto muestra vacante
        Example<Vacante> example = Example.of(vacante, matcher);
        List<Vacante> lista= serviceVacantes.buscarByExample(example);
        //pasa a la vista la lista
         model.addAttribute("vacantes", lista);
        return "home";
    }


    // a nivel de un metodo en un controlador
    //se agrega al modelo todos los atributos donde estan disponibles para todos los metodos en el controlador
    //ponemos un atributo llamado vacantes y llammos la clase de servicio serviceVacantes con el metodo buscarDestacadas
    @ModelAttribute
    public void setGenericos(Model model){

        /* data binding ponemos un objeto de tipo vacante para mapearlo al formulario */
        Vacante vacanteSearch = new Vacante();
        vacanteSearch.reset(); //cambia de imagen a null
		
        model.addAttribute("search", vacanteSearch);
        model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
        //obtiene la lista de categorias para enviar a la vista con el atributo categorias => home.html
        model.addAttribute("categorias", serviceCategorias.buscarTodas());

    }

    /*
         InitBinder para String si los detecta vacios durante el DataBinding los settea a NULL
         registerCustomEditor en el datbinding en los string cuando recibe un valor vacio lo pone null

    */

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

    }


    //login personalizado
    @GetMapping("/login" )
    public String mostrarLogin() {
        return "formLogin";
    }

    //logout personalizado
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler =
                new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        //return "redirect:/login";
		return "redirect:/";
    }


   //  @ResponseBody regresa texto al navegador
    @GetMapping("/bcrypt/{texto}")
    @ResponseBody
    public String encriptar(@PathVariable("texto") String texto){
    return texto + "Texto encriptado en bycypt : " + passwordEncoder.encode(texto);
    }

	@GetMapping("/about")
	public String mostrarAcerca() {			
		return "acerca";
	}
}


/*
    @GetMapping("/")
    public String mostrarHome(Model model) {

		//model.addAttribute("mensaje", "Bienvenidos a Empleos App");
		//model.addAttribute("fecha", new Date());



        //String nombre = "Auxiliar de Contabilidad";
        //Date fechaPub = new Date();
        //double salario = 9000.0;
        //boolean vigente = true;

        //model.addAttribute("nombre", nombre);
        //model.addAttribute("fecha", fechaPub);
        //model.addAttribute("salario", salario);
        //model.addAttribute("vigente", vigente);

        //return "home";


        List<Vacante> lista = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes", lista);
        return "home";
    }
*/


