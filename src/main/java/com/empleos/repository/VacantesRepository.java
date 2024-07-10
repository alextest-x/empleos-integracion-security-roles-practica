package com.empleos.repository;

import com.empleos.model.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> {


    /* Spring Boot y Spring Data JPA - Query Methods consulta personalizadas
    Son métodos que permiten encontrar información (SELECT) en la base de datos
    y son DECLARADOS en la interfaz del repositorio.

    Nota: Estos métodos solo se DECLARAN en la interfaz, Spring Data JPA
    realiza la implementación del método, dependiendo del NOMBRE DEL METODO y losPARAMETROS que recibe.
    Ejemplo: Método para recuperar las entidades de tipo Vacante, filtradas por el campo estatus:

    findByEstatus(String estatus);



     KeyWord: findBy
     Atributo de la clase: Estatus
     Parametro: (String estatus)
    */


    //select * from Vacantes where estatus = ?
    //lista de objetos vacantes
    List<Vacante> findByEstatus(String estatus);


    List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);


    List<Vacante> findBySalarioBetweenOrderBySalarioDesc(double s1, double s2);


    List<Vacante> findByEstatusIn(String[] estatus);


}

