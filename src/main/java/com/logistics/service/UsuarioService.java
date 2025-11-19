package com.logistics.service;

import com.logistics.model.dto.UsuarioDTO;
import com.logistics.model.dto.DireccionDTO;
import com.logistics.model.entities.Usuario;
import com.logistics.model.entities.Direccion;
import com.logistics.repository.UsuarioRepository;
import com.logistics.util.UsuarioFileManager;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de usuarios.
 * Proporciona operaciones CRUD para usuarios, direcciones y métodos de pago.
 */
public class UsuarioService {
    private UsuarioRepository repository = new UsuarioRepository();

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida que el correo no esté duplicado y guarda el usuario en memoria y archivo.
     * 
     * @param nombre El nombre completo del usuario
     * @param correo El correo electrónico del usuario (debe ser único)
     * @param telefono El número de teléfono del usuario
     * @param contrasena La contraseña del usuario
     * @return DTO con la información del usuario registrado
     * @throws IllegalArgumentException Si el correo ya está registrado
     */
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

    /**
     * Inicia sesión de un usuario con correo y contraseña.
     * Valida las credenciales y retorna la información del usuario si son correctas.
     * 
     * @param correo El correo electrónico del usuario
     * @param contrasena La contraseña del usuario
     * @return DTO con la información del usuario autenticado
     * @throws IllegalArgumentException Si las credenciales son inválidas
     */
    public UsuarioDTO iniciarSesion(String correo, String contrasena) {
        Usuario usuario = repository.findByCorreo(correo);
        if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        return toDTO(usuario);
    }

    /**
     * Actualiza la información del perfil de un usuario.
     * Permite modificar el nombre y teléfono del usuario.
     * 
     * @param idUsuario El identificador único del usuario
     * @param nombre El nuevo nombre del usuario
     * @param telefono El nuevo teléfono del usuario
     * @return DTO con la información actualizada del usuario
     * @throws IllegalArgumentException Si el usuario no existe
     */
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

    /**
     * Agrega una nueva dirección a un usuario.
     * 
     * @param idUsuario El identificador único del usuario
     * @param direccionDTO DTO con la información de la dirección a agregar
     * @throws IllegalArgumentException Si el usuario no existe
     */
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

    /**
     * Elimina una dirección de un usuario.
     * 
     * @param idUsuario El identificador único del usuario
     * @param idDireccion El identificador único de la dirección a eliminar
     * @throws IllegalArgumentException Si el usuario no existe
     */
    public void eliminarDireccion(String idUsuario, String idDireccion) {
        Usuario usuario = repository.findById(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuario.getDirecciones().removeIf(d -> d.getIdDireccion().equals(idDireccion));
        repository.save(usuario);
    }

    /**
     * Agrega un método de pago a un usuario.
     * Solo agrega el método si no está ya registrado.
     * 
     * @param idUsuario El identificador único del usuario
     * @param metodoPago El método de pago a agregar (TARJETA, PSE, etc.)
     * @throws IllegalArgumentException Si el usuario no existe
     */
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

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     * 
     * @return Lista de DTOs con la información de todos los usuarios
     */
    public java.util.List<UsuarioDTO> listarUsuarios() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id El identificador único del usuario a eliminar
     */
    public void eliminarUsuario(String id) {
        repository.delete(id);
    }

    /**
     * Obtiene la información de un usuario por su identificador.
     * 
     * @param id El identificador único del usuario
     * @return DTO con la información del usuario, o null si no existe
     */
    public UsuarioDTO obtenerUsuario(String id) {
        Usuario usuario = repository.findById(id);
        return usuario != null ? toDTO(usuario) : null;
    }

    /**
     * Convierte una entidad Usuario a su DTO correspondiente.
     * 
     * @param usuario La entidad Usuario a convertir
     * @return DTO con la información del usuario
     */
    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setTelefono(usuario.getTelefono());
        dto.setDirecciones(usuario.getDirecciones().stream().map(this::toDireccionDTO).collect(Collectors.toList()));
        return dto;
    }

    /**
     * Convierte una entidad Direccion a su DTO correspondiente.
     * 
     * @param direccion La entidad Direccion a convertir
     * @return DTO con la información de la dirección
     */
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

