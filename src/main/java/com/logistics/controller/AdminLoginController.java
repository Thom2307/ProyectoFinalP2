package com.logistics.controller;

import com.logistics.service.UsuarioService;
import com.logistics.util.NavigationManager;

/**
 * Controlador para el inicio de sesión de administradores.
 * Gestiona la autenticación y validación de permisos de administrador.
 */
public class AdminLoginController {
    private UsuarioService usuarioService;

    /**
     * Constructor que inicializa el servicio de usuarios.
     */
    public AdminLoginController() {
        this.usuarioService = new UsuarioService();
    }

    /**
     * Inicia sesión para un usuario administrador.
     * Valida las credenciales y verifica que el usuario tenga permisos de administrador.
     * 
     * @param usuario El nombre de usuario o correo electrónico (puede ser "admin" o "admin@logistics.com")
     * @param contrasena La contraseña del usuario
     * @throws IllegalArgumentException Si los campos están vacíos o el usuario no es administrador
     */
    public void iniciarSesion(String usuario, String contrasena) {
        if (usuario == null || usuario.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Por favor complete todos los campos");
        }

        // El usuario admin puede ingresar con "admin@logistics.com" o "admin"
        String correo = usuario.contains("@") ? usuario : usuario + "@logistics.com";
        
        // Buscar por correo (el usuario admin tiene correo "admin@logistics.com")
        var usuarioDTO = usuarioService.iniciarSesion(correo, contrasena);
        
        // Verificar que sea administrador
        com.logistics.repository.UsuarioRepository repo = new com.logistics.repository.UsuarioRepository();
        var usuarioCompleto = repo.findById(usuarioDTO.getIdUsuario());
        
        if (usuarioCompleto == null || !usuarioCompleto.isEsAdmin()) {
            throw new IllegalArgumentException("Acceso denegado. Solo administradores pueden ingresar aquí.");
        }
        
        NavigationManager.getInstance().setUsuarioActual(usuarioDTO.getIdUsuario(), true);
    }
}

