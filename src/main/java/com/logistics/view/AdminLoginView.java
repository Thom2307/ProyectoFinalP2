package com.logistics.view;

import com.logistics.controller.AdminLoginController;
import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminLoginView {
    private AdminLoginController controller;
    private TextField usuarioField;
    private PasswordField contrasenaField;
    private Label errorLabel;

    public AdminLoginView() {
        this.controller = new AdminLoginController();
    }

    public Scene crearScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #10b981, #059669);");

        // Título
        Label titulo = new Label("Acceso de Administrador");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 32));
        titulo.setStyle("-fx-text-fill: white;");

        // Card de login
        VBox card = new VBox(20);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 5);");
        card.setPadding(new Insets(40));
        card.setMaxWidth(400);

        Label subtitulo = new Label("Iniciar Sesión");
        subtitulo.setFont(Font.font("System", FontWeight.BOLD, 24));
        subtitulo.setStyle("-fx-text-fill: #1e293b;");

        usuarioField = new TextField();
        usuarioField.setPromptText("Usuario");
        usuarioField.setPrefHeight(45);

        contrasenaField = new PasswordField();
        contrasenaField.setPromptText("Contraseña");
        contrasenaField.setPrefHeight(45);

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 12px;");
        errorLabel.setWrapText(true);

        Button loginButton = new Button("Ingresar");
        loginButton.setPrefWidth(Double.MAX_VALUE);
        loginButton.setPrefHeight(45);
        loginButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; " +
            "-fx-font-size: 16px; -fx-font-weight: bold;");
        loginButton.setOnAction(e -> iniciarSesion());

        Button volverButton = new Button("← Volver al Menú Principal");
        volverButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; " +
            "-fx-font-size: 14px;");
        volverButton.setOnAction(e -> {
            NavigationManager.getInstance().navegarA(new MainMenuView().crearScene());
        });

        card.getChildren().addAll(subtitulo, usuarioField, contrasenaField, errorLabel, loginButton);
        root.getChildren().addAll(titulo, card, volverButton);

        Scene scene = new Scene(root, 900, 700);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
    }

    private void iniciarSesion() {
        String usuario = usuarioField.getText().trim();
        String contrasena = contrasenaField.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor complete todos los campos");
            return;
        }

        try {
            controller.iniciarSesion(usuario, contrasena);
            // Si llegamos aquí, el login fue exitoso
            NavigationManager.getInstance().navegarA(
                new com.logistics.view.admin.DashboardAdminView().crearScene()
            );
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        errorLabel.setText(mensaje);
    }
}

