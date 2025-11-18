package com.logistics.repository;

import com.logistics.model.entities.Usuario;
import com.logistics.patterns.creational.singleton.InMemoryDatabase;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioRepository {
    private InMemoryDatabase db = InMemoryDatabase.getInstance();

    public void save(Usuario usuario) {
        db.getUsuarios().put(usuario.getIdUsuario(), usuario);
    }

    public Usuario findById(String id) {
        return db.getUsuarios().get(id);
    }

    public Usuario findByCorreo(String correo) {
        return db.getUsuarios().values().stream()
                .filter(u -> u.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> findAll() {
        return db.getUsuarios().values().stream().collect(Collectors.toList());
    }

    public void delete(String id) {
        db.getUsuarios().remove(id);
    }
}

