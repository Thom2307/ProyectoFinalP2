package com.logistics.controller;

import com.logistics.service.UsuarioService;
import com.logistics.util.NavigationManager;
import com.logistics.viewController.usuario.DashboardUsuarioView;
import com.logistics.viewController.admin.DashboardAdminView;
import javafx.scene.control.Label;

/**
 * Controlador para el inicio de sesión de usuarios.
 * Gestiona la autenticación de usuarios regulares y administradores, y el registro de nuevos usuarios.
 */
public class LoginController {
    private UsuarioService usuarioService;

    /**
     * Constructor que inicializa el servicio de usuarios.
     */
    public LoginController() {
        this.usuarioService = new UsuarioService();
    }

    /**
     * Inicia sesión para un usuario con correo y contraseña.
     * Valida las credenciales y redirige al dashboard correspondiente según el tipo de usuario.
     * 
     * @param correo El correo electrónico del usuario
     * @param contrasena La contraseña del usuario
     * @param errorLabel Etiqueta de JavaFX para mostrar mensajes de error
     */
    public void iniciarSesion(String correo, String contrasena, Label errorLabel) {
        try {
            if (correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
                mostrarError(errorLabel, "Por favor complete todos los campos");
                return;
            }

            var usuarioDTO = usuarioService.iniciarSesion(correo, contrasena);
            
            // Obtener usuario completo para verificar si es admin
            com.logistics.repository.UsuarioRepository repo = new com.logistics.repository.UsuarioRepository();
            var usuario = repo.findById(usuarioDTO.getIdUsuario());
            boolean esAdmin = usuario != null && usuario.isEsAdmin();
            
            NavigationManager.getInstance().setUsuarioActual(usuarioDTO.getIdUsuario(), esAdmin);

            if (esAdmin) {
                NavigationManager.getInstance().navegarA(new DashboardAdminView().crearScene());
            } else {
                NavigationManager.getInstance().navegarA(new DashboardUsuarioView().crearScene());
            }
        } catch (Exception e) {
            mostrarError(errorLabel, e.getMessage());
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida que todos los campos estén completos antes de registrar.
     * 
     * @param nombre El nombre completo del usuario
     * @param correo El correo electrónico del usuario
     * @param telefono El número de teléfono del usuario
     * @param contrasena La contraseña del usuario
     * @throws IllegalArgumentException Si algún campo está vacío o es nulo
     */
    public void registrarUsuario(String nombre, String correo, String telefono, String contrasena) {
        if (nombre == null || nombre.isEmpty() || correo == null || correo.isEmpty() ||
            telefono == null || telefono.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son requeridos");
        }

        usuarioService.registrarUsuario(nombre, correo, telefono, contrasena);
    }

    /**
     * Muestra un mensaje de error en la etiqueta proporcionada.
     * 
     * @param errorLabel La etiqueta de JavaFX donde se mostrará el error
     * @param mensaje El mensaje de error a mostrar
     */
    private void mostrarError(Label errorLabel, String mensaje) {
        if (errorLabel != null) {
            errorLabel.setText(mensaje);
            errorLabel.setVisible(true);
        }
    }
}

