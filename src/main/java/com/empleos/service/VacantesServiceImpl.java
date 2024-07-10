package com.empleos.service;

import com.empleos.model.Vacante;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

//hay que implentar los metodos de la interface
//la clase de servicio regresa la lista de vacantes

@Service
public class VacantesServiceImpl implements IVacantesService {

    //nivel de la clase para que cualquier metodo tenga acceso en a clse de sericio
    private List<Vacante> lista = null;

    public VacantesServiceImpl() {


        //da formato alas fechas
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //lista de objetos vacantes a una lista enlazada
          lista = new LinkedList<Vacante>();

        try {
            // Creamos la oferta de Trabajo 1.
            Vacante vacante1 = new Vacante();
            vacante1.setId(1);
            vacante1.setNombre("Ingeniero Civil"); // Titulo de la vacante
            vacante1.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal.");
            vacante1.setFecha(sdf.parse("08-02-2019"));
            vacante1.setSalario(14000.0);
            vacante1.setDestacado(1);
            vacante1.setImagen("empresa1.png");
	        vacante1.setEstatus("Aprobada");

            // Creamos la oferta de Trabajo 2.
            Vacante vacante2 = new Vacante();
            vacante2.setId(2);
            vacante2.setNombre("Contador Publico");
            vacante2.setDescripcion("Empresa importante solicita contador con 5 años de experiencia titulado.");
            vacante2.setFecha(sdf.parse("09-02-2019"));
            vacante2.setSalario(12000.0);
            vacante2.setDestacado(0);
            vacante2.setImagen("empresa2.png");
            vacante2.setEstatus("Creada");

            // Creamos la oferta de Trabajo 3.
            Vacante vacante3 = new Vacante();
            vacante3.setId(3);
            vacante3.setNombre("Ingeniero Eléctrico");
            vacante3.setDescripcion("Empresa internacional solicita Ingeniero mecánico para mantenimiento de la instalación eléctrica.");
            vacante3.setFecha(sdf.parse("10-02-2019"));
            vacante3.setSalario(10500.0);
            vacante3.setDestacado(0);
            vacante3.setEstatus("Aprobada");

            // Creamos la oferta de Trabajo 4.
            Vacante vacante4 = new Vacante();
            vacante4.setId(4);
            vacante4.setNombre("Diseñador Gráfico");
            vacante4.setDescripcion("Solicitamos Diseñador Gráfico titulado para diseñar publicidad de la empresa.");
            vacante4.setFecha(sdf.parse("11-02-2019"));
            vacante4.setSalario(7500.0);
            vacante4.setDestacado(1);
            vacante4.setImagen("empresa3.png");
            vacante4.setEstatus("Eliminada");

            /**
             * Agregamos los 4 objetos de tipo Vacante a la lista ...
             */
            lista.add(vacante1);
            lista.add(vacante2);
            lista.add(vacante3);
            lista.add(vacante4);

        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());

        }


        //quitamos el return porque en el constructor no regresa ningun objecto
        //return lista;

    }

    @Override
    public List<Vacante> buscarTodas() {
        return lista;
    }


    @Override
    public Vacante buscarPorId(Integer idVacante) {
        //busca el id
        for (Vacante v : lista){
            if(v.getId()== idVacante){
                return v;
            }
        }
        //no encontro nada
        return null;
    }

	public void guardar(Vacante vacante) {
        lista.add(vacante);
	}

    @Override
    public List<Vacante> buscarDestacadas() {
        return null;
    }

    @Override
    public void eliminar(Integer idVacante) {

    }

    @Override
    public List<Vacante> buscarByExample(Example<Vacante> example) {
        return null;
    }

    @Override
    public Page<Vacante> buscarTodas(Pageable page) {
        return null;
    }

}



