package com.logistics.view.usuario;

import com.logistics.controller.usuario.PerfilController;
import com.logistics.model.dto.UsuarioDTO;
import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PerfilView {
    private PerfilController controller;
    private TextField nombreField;
    private TextField correoField;
    private TextField telefonoField;

    public PerfilView() {
        this.controller = new PerfilController();
    }

    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Mi Perfil"));
        root.setLeft(crearMenuLateral());
        
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        contenido.setMaxWidth(600);
        
        Label titulo = new Label("Información Personal");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        
        nombreField = new TextField();
        nombreField.setPrefWidth(300);
        
        correoField = new TextField();
        correoField.setPrefWidth(300);
        correoField.setEditable(false); // Correo no se puede editar
        correoField.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #64748b;");
        
        telefonoField = new TextField();
        telefonoField.setPrefWidth(300);
        
        Label nombreLabel = new Label("Nombre:");
        nombreLabel.setFont(Font.font("System", 14));
        
        Label correoLabel = new Label("Correo:");
        correoLabel.setFont(Font.font("System", 14));
        
        Label telefonoLabel = new Label("Teléfono:");
        telefonoLabel.setFont(Font.font("System", 14));
        
        // Agregar nota sobre el correo
        Label notaCorreo = new Label("(El correo no se puede modificar)");
        notaCorreo.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 11px;");
        
        grid.add(nombreLabel, 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(correoLabel, 0, 1);
        grid.add(correoField, 1, 1);
        grid.add(notaCorreo, 1, 2);
        grid.add(telefonoLabel, 0, 3);
        grid.add(telefonoField, 1, 3);
        
        Button actualizarButton = new Button("Actualizar Datos");
        actualizarButton.setPrefWidth(Double.MAX_VALUE);
        actualizarButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12px;");
        actualizarButton.setOnAction(e -> actualizarDatos());
        
        contenido.getChildren().addAll(titulo, grid, actualizarButton);
        contenido.setAlignment(Pos.CENTER);
        
        // Cargar datos del usuario actual
        cargarDatosUsuario();
        
        root.setCenter(contenido);
        
        Scene scene = new Scene(root, 1200, 800);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
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
    
    private void actualizarDatos() {
        String nombre = nombreField.getText().trim();
        String telefono = telefonoField.getText().trim();
        
        // Validaciones
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
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private HBox crearTopBar(String titulo) {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2563eb;");
        topBar.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(titulo);
        label.setFont(Font.font("System", FontWeight.BOLD, 20));
        label.setStyle("-fx-text-fill: white;");
        topBar.getChildren().add(label);
        return topBar;
    }

    private VBox crearMenuLateral() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: #1e293b; -fx-min-width: 200px;");
        Button btn = new Button("← Volver");
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        btn.setOnAction(e -> NavigationManager.getInstance().navegarA(
            new DashboardUsuarioView().crearScene()));
        menu.getChildren().add(btn);
        return menu;
    }
}

