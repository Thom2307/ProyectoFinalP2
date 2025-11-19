package com.logistics.viewController;

import com.logistics.controller.AdminLoginController;
import com.logistics.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminLoginViewController {
    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button volverButton;
    
    private AdminLoginController controller;
    
    public void initialize() {
        this.controller = new AdminLoginController();
    }
    
    @FXML
    private void onLoginClick() {
        String usuario = usuarioField.getText().trim();
        String contrasena = contrasenaField.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor complete todos los campos");
            return;
        }

        try {
            controller.iniciarSesion(usuario, contrasena);
            NavigationManager.getInstance().navegarA(
                new com.logistics.viewController.admin.DashboardAdminView().crearScene()
            );
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }
    
    @FXML
    private void onVolverClick() {
        NavigationManager.getInstance().navegarA(new MainMenuView().crearScene());
    }
    
    private void mostrarError(String mensaje) {
        errorLabel.setText(mensaje);
    }
}

