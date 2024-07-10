package com.empleos.service.db;

import com.empleos.model.Categoria;
import com.empleos.model.Vacante;
import com.empleos.repository.CategoriasRepository;
import com.empleos.service.ICategoriasService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

 /*
   para guardar en ls base de datos hay que inyectar CategoriasRepository
   para utilizar los metodos predefinidos omo el save()

   @Primary  solo ponemos la anotacion en la clase service
 */

@Service
@Primary //si la comentamos es proque usamos en el controlador Categorias @Qualifier("categoriasServiceJpa")
public class CategoriaServiceJpa implements ICategoriasService {


   @Autowired
   private CategoriasRepository categoriasRepository;

    @Override
    public void guardar(Categoria categoria) {
             categoriasRepository.save(categoria);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return categoriasRepository.findAll();
    }

    /*
    @Override
    public Optional<Categoria> buscarPorId(Integer idCategoria) {
        return categoriasRepository.findById(idCategoria);
    }
   */


    //es un opcional lo guardamos en una variable de tipo optional
    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        Optional<Categoria> optional = categoriasRepository.findById(idCategoria);
        if(optional.isPresent()){
           return optional.get(); //regresa objeto categoria
        }
        return null; //  regrsa un valor nulo si no encuentra el objeto.
    }

    @Override
    public void eliminar(Integer idCategoria) {
        categoriasRepository.deleteById(idCategoria);

    }

    @Override
    public Page<Categoria> buscarTodas(Pageable page) {
        return categoriasRepository.findAll(page);
    }


}
