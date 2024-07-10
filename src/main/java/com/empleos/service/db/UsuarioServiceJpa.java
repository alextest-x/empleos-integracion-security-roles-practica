package com.empleos.service.db;

import com.empleos.model.Usuario;
import com.empleos.repository.UsuariosRepository;
import com.empleos.service.IUsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceJpa implements IUsuarioService {

    @Autowired
    public UsuariosRepository usuariosRepository;

    @Override
    public void guardar(Usuario usuario) {
        usuariosRepository.save(usuario);
    };

    @Override
    public void eliminar(Integer idUsuario) {
        usuariosRepository.deleteById(idUsuario);

    }

    @Override
    public List<Usuario> buscarTodos() {

        return usuariosRepository.findAll();
    }

    @Override
    public Usuario buscarPorUsername(String username) {

        return usuariosRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> buscarRegistrados() {

        return usuariosRepository.findByFechaRegistroNotNull();
    }

    @Override
    public Usuario buscarPorId(Integer idUsuario) {
        Optional<Usuario> optional = usuariosRepository.findById(idUsuario);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Transactional
    @Override
    public int bloquear(int idUsuario) {
        int rows = usuariosRepository.lock(idUsuario);
        return rows;
    }

    @Transactional
    @Override
    public int activar(int idUsuario) {
        int rows = usuariosRepository.unlock(idUsuario);
        return rows;
    }


}
