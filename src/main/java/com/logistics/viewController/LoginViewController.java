package com.logistics.viewController;

import com.logistics.controller.LoginController;
import com.logistics.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginViewController {
    @FXML
    private TextField correoField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registroButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button volverButton;
    
    private LoginController controller;
    
    public void initialize() {
        this.controller = new LoginController();
    }
    
    @FXML
    private void onLoginClick() {
        controller.iniciarSesion(correoField.getText(), contrasenaField.getText(), errorLabel);
    }
    
    @FXML
    private void onRegistroClick() {
        mostrarRegistro();
    }
    
    @FXML
    private void onVolverClick() {
        NavigationManager.getInstance().navegarA(new MainMenuView().crearScene());
    }
    
    private void mostrarRegistro() {
        javafx.scene.control.Dialog<String> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Registro de Usuario");
        dialog.setHeaderText("Crear nueva cuenta");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20));

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

