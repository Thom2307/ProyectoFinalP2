package com.logistics.repository;

import com.logistics.model.entities.Usuario;
import com.logistics.patterns.creacional.singleton.InMemoryDatabase;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositorio para la gestión de usuarios en la base de datos en memoria.
 * Proporciona operaciones CRUD para usuarios.
 */
public class UsuarioRepository {
    private InMemoryDatabase db = InMemoryDatabase.getInstance();

    /**
     * Guarda o actualiza un usuario en la base de datos.
     * 
     * @param usuario La entidad Usuario a guardar
     */
    public void save(Usuario usuario) {
        db.getUsuarios().put(usuario.getIdUsuario(), usuario);
    }

    /**
     * Busca un usuario por su identificador único.
     * 
     * @param id El identificador único del usuario
     * @return La entidad Usuario encontrada, o null si no existe
     */
    public Usuario findById(String id) {
        return db.getUsuarios().get(id);
    }

    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param correo El correo electrónico del usuario
     * @return La entidad Usuario encontrada, o null si no existe
     */
    public Usuario findByCorreo(String correo) {
        return db.getUsuarios().values().stream()
                .filter(u -> u.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     * 
     * @return Lista con todos los usuarios
     */
    public List<Usuario> findAll() {
        return db.getUsuarios().values().stream().collect(Collectors.toList());
    }

    /**
     * Elimina un usuario de la base de datos.
     * 
     * @param id El identificador único del usuario a eliminar
     */
    public void delete(String id) {
        db.getUsuarios().remove(id);
    }
}

