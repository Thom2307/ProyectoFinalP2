package com.logistics.service;

import com.logistics.model.dto.UsuarioDTO;
import com.logistics.model.dto.DireccionDTO;
import com.logistics.model.entities.Usuario;
import com.logistics.model.entities.Direccion;
import com.logistics.repository.UsuarioRepository;
import com.logistics.util.UsuarioFileManager;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsuarioService {
    private UsuarioRepository repository = new UsuarioRepository();

    public UsuarioDTO registrarUsuario(String nombre, String correo, String telefono, String contrasena) {
        if (repository.findByCorreo(correo) != null) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setIdUsuario("u" + UUID.randomUUID().toString().substring(0, 8));
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setContrasena(contrasena);
        usuario.setEsAdmin(false);
        repository.save(usuario);
        
        // Guardar en archivo .txt
        UsuarioFileManager.guardarUsuario(usuario);
        
        return toDTO(usuario);
    }

    public UsuarioDTO iniciarSesion(String correo, String contrasena) {
        Usuario usuario = repository.findByCorreo(correo);
        if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        return toDTO(usuario);
    }

    public UsuarioDTO actualizarPerfil(String idUsuario, String nombre, String telefono) {
        Usuario usuario = repository.findById(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        repository.save(usuario);
        return toDTO(usuario);
    }

    public void agregarDireccion(String idUsuario, DireccionDTO direccionDTO) {
        Usuario usuario = repository.findById(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(direccionDTO.getIdDireccion() != null ? direccionDTO.getIdDireccion() : "d" + UUID.randomUUID().toString().substring(0, 8));
        direccion.setAlias(direccionDTO.getAlias());
        direccion.setCalle(direccionDTO.getCalle());
        direccion.setCiudad(direccionDTO.getCiudad());
        direccion.setLat(direccionDTO.getLat());
        direccion.setLon(direccionDTO.getLon());
        usuario.getDirecciones().add(direccion);
        repository.save(usuario);
    }

    public void eliminarDireccion(String idUsuario, String idDireccion) {
        Usuario usuario = repository.findById(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuario.getDirecciones().removeIf(d -> d.getIdDireccion().equals(idDireccion));
        repository.save(usuario);
    }

    public void agregarMetodoPago(String idUsuario, String metodoPago) {
        Usuario usuario = repository.findById(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        if (!usuario.getMetodosPago().contains(metodoPago)) {
            usuario.getMetodosPago().add(metodoPago);
            repository.save(usuario);
        }
    }

    public java.util.List<UsuarioDTO> listarUsuarios() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void eliminarUsuario(String id) {
        repository.delete(id);
    }

    public UsuarioDTO obtenerUsuario(String id) {
        Usuario usuario = repository.findById(id);
        return usuario != null ? toDTO(usuario) : null;
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setDirecciones(usuario.getDirecciones().stream().map(this::toDireccionDTO).collect(Collectors.toList()));
        return dto;
    }

    private DireccionDTO toDireccionDTO(Direccion direccion) {
        DireccionDTO dto = new DireccionDTO();
        dto.setIdDireccion(direccion.getIdDireccion());
        dto.setAlias(direccion.getAlias());
        dto.setCalle(direccion.getCalle());
        dto.setCiudad(direccion.getCiudad());
        dto.setLat(direccion.getLat());
        dto.setLon(direccion.getLon());
        return dto;
    }
}

