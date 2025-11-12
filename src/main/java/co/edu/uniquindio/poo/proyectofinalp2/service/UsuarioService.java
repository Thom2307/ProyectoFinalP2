package service;

import model.*;
import java.util.*;

public class UsuarioService {
    private List<Usuario> usuarios;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    public Usuario crearUsuario(String idUsuario, String nombreCompleto, String correo, String telefono, String contrasena) {
        Usuario usuario = new Usuario(idUsuario, nombreCompleto, correo, telefono, contrasena);
        usuarios.add(usuario);
        return usuario;
    }

    public Usuario buscarPorId(String idUsuario) {
        return usuarios.stream()
            .filter(u -> u.getIdUsuario().equals(idUsuario))
            .findFirst()
            .orElse(null);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarios.stream()
            .filter(u -> u.getCorreo().equals(correo))
            .findFirst()
            .orElse(null);
    }

    public boolean autenticar(String correo, String contrasena) {
        Usuario usuario = buscarPorCorreo(correo);
        return usuario != null && usuario.getContrasena().equals(contrasena);
    }

    public void actualizarUsuario(Usuario usuario, String nombre, String correo, String telefono) {
        usuario.setNombreCompleto(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
    }

    public void eliminarUsuario(String idUsuario) {
        usuarios.removeIf(u -> u.getIdUsuario().equals(idUsuario));
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public void agregarDireccion(Usuario usuario, Direccion direccion) {
        usuario.agregarDireccion(direccion);
    }

    public void eliminarDireccion(Usuario usuario, String idDireccion) {
        usuario.eliminarDireccion(idDireccion);
    }

    public void agregarMetodoPago(Usuario usuario, MetodoPago metodoPago) {
        usuario.agregarMetodoPago(metodoPago);
    }

    public void eliminarMetodoPago(Usuario usuario, String idMetodoPago) {
        usuario.eliminarMetodoPago(idMetodoPago);
    }

    public List<Envio> consultarEnviosUsuario(Usuario usuario) {
        return usuario.getEnvios();
    }
}

