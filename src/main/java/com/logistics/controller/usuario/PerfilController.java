package com.logistics.controller.usuario;

import com.logistics.model.dto.UsuarioDTO;
import com.logistics.service.UsuarioService;
import com.logistics.util.NavigationManager;

public class PerfilController {
    private UsuarioService usuarioService;

    public PerfilController() {
        this.usuarioService = new UsuarioService();
    }

    public UsuarioDTO obtenerUsuarioActual() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        if (usuarioId == null) {
            return null;
        }
        return usuarioService.obtenerUsuario(usuarioId);
    }

    public UsuarioDTO actualizarPerfil(String nombre, String telefono) {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        if (usuarioId == null) {
            throw new IllegalStateException("No hay usuario autenticado");
        }
        return usuarioService.actualizarPerfil(usuarioId, nombre, telefono);
    }
}

