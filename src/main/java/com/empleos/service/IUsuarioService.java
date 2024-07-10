package com.empleos.service;

import com.empleos.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    void guardar(Usuario usuario);

    void eliminar(Integer idUsuario);

    List<Usuario> buscarTodos();

    List<Usuario> buscarRegistrados();

    Usuario buscarPorId(Integer idUsuario);

    Usuario buscarPorUsername(String username);

    int bloquear(int idUsuario);

    int activar(int idUsuario);



}
