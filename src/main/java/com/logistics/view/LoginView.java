package com.logistics.view;

import com.logistics.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView {
    private LoginController controller;
    private TextField correoField;
    private PasswordField contrasenaField;
    private Button loginButton;
    private Button registroButton;
    private Label errorLabel;

    public LoginView() {
        this.controller = new LoginController();
    }

    public Scene crearScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2563eb, #1e40af);");

        // Título
        Label titulo = new Label("Sistema de Logística");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 32));
        titulo.setStyle("-fx-text-fill: white;");

        // Card de login
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 5);");
        card.setPadding(new Insets(40));
        card.setMaxWidth(400);

        Label subtitulo = new Label("Iniciar Sesión");
        subtitulo.setFont(Font.font("System", FontWeight.BOLD, 24));
        subtitulo.setStyle("-fx-text-fill: #1e293b;");

        correoField = new TextField();
        correoField.setPromptText("Correo electrónico");
        correoField.setPrefHeight(45);

        contrasenaField = new PasswordField();
        contrasenaField.setPromptText("Contraseña");
        contrasenaField.setPrefHeight(45);

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 12px;");
        errorLabel.setVisible(false);

        loginButton = new Button("Iniciar Sesión");
        loginButton.setPrefWidth(Double.MAX_VALUE);
        loginButton.setPrefHeight(45);
        loginButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        loginButton.setOnAction(e -> controller.iniciarSesion(correoField.getText(), contrasenaField.getText(), errorLabel));

        registroButton = new Button("Registrarse");
        registroButton.setPrefWidth(Double.MAX_VALUE);
        registroButton.setPrefHeight(45);
        registroButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-border-color: #2563eb; -fx-border-width: 2px; -fx-font-weight: bold;");
        registroButton.setOnAction(e -> mostrarRegistro());

        card.getChildren().addAll(subtitulo, correoField, contrasenaField, errorLabel, loginButton, registroButton);

        root.getChildren().addAll(titulo, card);

        Scene scene = new Scene(root, 600, 700);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
    }

    private void mostrarRegistro() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Registro de Usuario");
        dialog.setHeaderText("Crear nueva cuenta");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre completo");
        TextField correoRegField = new TextField();
        correoRegField.setPromptText("Correo electrónico");
        TextField telefonoField = new TextField();
        telefonoField.setPromptText("Teléfono");
        PasswordField contrasenaRegField = new PasswordField();
        contrasenaRegField.setPromptText("Contraseña");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Correo:"), 0, 1);
        grid.add(correoRegField, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(telefonoField, 1, 2);
        grid.add(new Label("Contraseña:"), 0, 3);
        grid.add(contrasenaRegField, 1, 3);

        ButtonType registrarButtonType = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registrarButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registrarButtonType) {
                try {
                    controller.registrarUsuario(
                        nombreField.getText(),
                        correoRegField.getText(),
                        telefonoField.getText(),
                        contrasenaRegField.getText()
                    );
                    
                    // Mostrar mensaje de éxito
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registro Exitoso");
                    alert.setHeaderText("Usuario registrado correctamente");
                    alert.setContentText("Sus datos han sido guardados en el archivo: data/usuarios_registrados.txt");
                    alert.showAndWait();
                    
                    return "OK";
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
}

