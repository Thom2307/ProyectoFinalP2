package com.logistics.controller;

import com.logistics.service.UsuarioService;
import com.logistics.util.NavigationManager;
import com.logistics.view.usuario.DashboardUsuarioView;
import com.logistics.view.admin.DashboardAdminView;
import javafx.scene.control.Label;

public class LoginController {
    private UsuarioService usuarioService;

    public LoginController() {
        this.usuarioService = new UsuarioService();
    }

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

    public void registrarUsuario(String nombre, String correo, String telefono, String contrasena) {
        if (nombre == null || nombre.isEmpty() || correo == null || correo.isEmpty() ||
            telefono == null || telefono.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son requeridos");
        }

        usuarioService.registrarUsuario(nombre, correo, telefono, contrasena);
    }

    private void mostrarError(Label errorLabel, String mensaje) {
        if (errorLabel != null) {
            errorLabel.setText(mensaje);
            errorLabel.setVisible(true);
        }
    }
}

