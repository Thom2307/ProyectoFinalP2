package com.logistics.view.usuario;

import com.logistics.controller.usuario.RastreoController;
import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RastreoView {
    private RastreoController controller;

    public RastreoView() {
        this.controller = new RastreoController();
    }

    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Rastrear Envío"));
        root.setLeft(crearMenuLateral());

        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        contenido.setAlignment(Pos.CENTER);

        Label titulo = new Label("Rastrear Envío");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));

        HBox busquedaBox = new HBox(10);
        TextField idField = new TextField();
        idField.setPromptText("Ingrese ID de envío");
        idField.setPrefWidth(300);
        Button buscarButton = new Button("Buscar");
        
        // Área de resultado
        VBox resultadoBox = new VBox(15);
        resultadoBox.setPadding(new Insets(20));
        resultadoBox.setPrefWidth(600);
        resultadoBox.setAlignment(Pos.CENTER);
        resultadoBox.setStyle("-fx-background-color: white; -fx-background-radius: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        resultadoBox.setVisible(false);
        
        Label resultadoTitulo = new Label();
        resultadoTitulo.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        Label resultadoMensaje = new Label();
        resultadoMensaje.setFont(Font.font("System", 14));
        resultadoMensaje.setWrapText(true);
        resultadoMensaje.setAlignment(Pos.CENTER);
        
        resultadoBox.getChildren().addAll(resultadoTitulo, resultadoMensaje);
        
        buscarButton.setOnAction(e -> {
            String idEnvio = idField.getText().trim();
            if (idEnvio.isEmpty()) {
                mostrarError("Por favor ingrese un ID de envío");
                return;
            }
            mostrarResultadoRastreo(idEnvio, resultadoTitulo, resultadoMensaje, resultadoBox);
        });

        busquedaBox.getChildren().addAll(idField, buscarButton);
        busquedaBox.setAlignment(Pos.CENTER);

        contenido.getChildren().addAll(titulo, busquedaBox, resultadoBox);

        root.setCenter(contenido);

        Scene scene = new Scene(root, 1200, 800);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
    }

    private void mostrarResultadoRastreo(String idEnvio, Label titulo, Label mensaje, VBox contenedor) {
        EnvioDTO envio = controller.buscarEnvio(idEnvio);
        
        if (envio == null) {
            // Envío no encontrado
            titulo.setText("❌ Envío No Encontrado");
            titulo.setStyle("-fx-text-fill: #ef4444;");
            mensaje.setText("El pedido con ID \"" + idEnvio + "\" no se encuentra en el sistema.");
            mensaje.setStyle("-fx-text-fill: #64748b;");
            contenedor.setVisible(true);
            return;
        }
        
        // Verificar si el envío está activo
        EstadoEnvio estado = envio.getEstado();
        boolean esActivo = estado == EstadoEnvio.SOLICITADO || 
                          estado == EstadoEnvio.ASIGNADO || 
                          estado == EstadoEnvio.EN_RUTA;
        
        if (esActivo) {
            // Envío activo - mostrar estado
            titulo.setText("✅ Estado del Pedido");
            titulo.setStyle("-fx-text-fill: #10b981;");
            
            String estadoTexto = obtenerTextoEstado(estado);
            mensaje.setText("El pedido con ID \"" + idEnvio + "\" está actualmente en estado:\n\n" +
                          "📦 " + estadoTexto);
            mensaje.setStyle("-fx-text-fill: #1e293b;");
        } else {
            // Envío no activo
            titulo.setText("⚠️ Pedido No Activo");
            titulo.setStyle("-fx-text-fill: #f59e0b;");
            
            String estadoTexto = obtenerTextoEstado(estado);
            mensaje.setText("El pedido con ID \"" + idEnvio + "\" no se encuentra dentro de la lista de pedidos activos.\n\n" +
                          "Estado actual: " + estadoTexto + "\n\n" +
                          "Los pedidos activos son aquellos con estado: SOLICITADO, ASIGNADO o EN_RUTA.");
            mensaje.setStyle("-fx-text-fill: #64748b;");
        }
        
        contenedor.setVisible(true);
    }
    
    private String obtenerTextoEstado(EstadoEnvio estado) {
        switch (estado) {
            case SOLICITADO:
                return "SOLICITADO - El pedido ha sido solicitado y está esperando asignación";
            case ASIGNADO:
                return "ASIGNADO - El pedido ha sido asignado a un repartidor";
            case EN_RUTA:
                return "EN_RUTA - El pedido está en camino a su destino";
            case ENTREGADO:
                return "ENTREGADO - El pedido ha sido entregado exitosamente";
            case CANCELADO:
                return "CANCELADO - El pedido ha sido cancelado";
            default:
                return estado.toString();
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
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

