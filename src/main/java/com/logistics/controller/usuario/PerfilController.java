package com.logistics.controller.usuario;

import com.logistics.model.dto.UsuarioDTO;
import com.logistics.service.UsuarioService;
import com.logistics.util.NavigationManager;

/**
 * Controlador para la gestión del perfil de usuario.
 * Permite obtener y actualizar la información del usuario actual.
 */
public class PerfilController {
    private UsuarioService usuarioService;

    /**
     * Constructor que inicializa el servicio de usuarios.
     */
    public PerfilController() {
        this.usuarioService = new UsuarioService();
    }

    /**
     * Obtiene la información del usuario actualmente autenticado.
     * 
     * @return DTO con la información del usuario, o null si no hay usuario autenticado
     */
    public UsuarioDTO obtenerUsuarioActual() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        if (usuarioId == null) {
            return null;
        }
        return usuarioService.obtenerUsuario(usuarioId);
    }

    /**
     * Actualiza la información del perfil del usuario actual.
     * Permite modificar el nombre y teléfono del usuario.
     * 
     * @param nombre El nuevo nombre del usuario
     * @param telefono El nuevo teléfono del usuario
     * @return DTO con la información actualizada del usuario
     * @throws IllegalStateException Si no hay usuario autenticado
     */
    public UsuarioDTO actualizarPerfil(String nombre, String telefono) {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        if (usuarioId == null) {
            throw new IllegalStateException("No hay usuario autenticado");
        }
        return usuarioService.actualizarPerfil(usuarioId, nombre, telefono);
    }
}

