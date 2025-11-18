package com.logistics.viewController.usuario;

import com.logistics.controller.usuario.PerfilController;
import com.logistics.model.dto.UsuarioDTO;
import com.logistics.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilViewController implements Initializable {
    @FXML
    private TextField nombreField;
    @FXML
    private TextField correoField;
    @FXML
    private TextField telefonoField;
    @FXML
    private Button actualizarButton;
    @FXML
    private Button volverButton;
    
    private PerfilController controller;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.controller = new PerfilController();
        cargarDatosUsuario();
    }
    
    private void cargarDatosUsuario() {
        UsuarioDTO usuario = controller.obtenerUsuarioActual();
        if (usuario != null) {
            nombreField.setText(usuario.getNombre() != null ? usuario.getNombre() : "");
            correoField.setText(usuario.getCorreo() != null ? usuario.getCorreo() : "");
            telefonoField.setText(usuario.getTelefono() != null ? usuario.getTelefono() : "");
        } else {
            mostrarError("No se pudo cargar la información del usuario");
        }
    }
    
    @FXML
    private void onActualizarClick() {
        String nombre = nombreField.getText().trim();
        String telefono = telefonoField.getText().trim();
        
        if (nombre.isEmpty()) {
            mostrarError("El nombre no puede estar vacío");
            return;
        }
        
        if (telefono.isEmpty()) {
            mostrarError("El teléfono no puede estar vacío");
            return;
        }
        
        try {
            controller.actualizarPerfil(nombre, telefono);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("✅ Éxito");
            alert.setHeaderText("Datos actualizados");
            alert.setContentText("Sus datos han sido actualizados correctamente.");
            alert.showAndWait();
        } catch (Exception e) {
            mostrarError("Error al actualizar los datos: " + e.getMessage());
        }
    }
    
    @FXML
    private void onVolverClick() {
        NavigationManager.getInstance().navegarA(new DashboardUsuarioView().crearScene());
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

