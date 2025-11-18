package com.logistics.view.usuario;

import com.logistics.controller.usuario.HistorialController;
import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.EnvioTableItem;
import com.logistics.util.NavigationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HistorialView {
    private HistorialController controller;

    public HistorialView() {
        this.controller = new HistorialController();
    }

    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Historial de Envíos"));
        root.setLeft(crearMenuLateral());
        
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        
        Label titulo = new Label("Historial de Envíos");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        // Filtros
        HBox filtrosBox = new HBox(15);
        filtrosBox.setAlignment(Pos.CENTER_LEFT);
        Label filtroLabel = new Label("Filtrar por estado:");
        ComboBox<String> estadoCombo = new ComboBox<>();
        estadoCombo.getItems().addAll("Todos", "SOLICITADO", "ASIGNADO", "EN_RUTA", "ENTREGADO", "CANCELADO");
        estadoCombo.setValue("Todos");
        Button filtrarButton = new Button("Filtrar");
        filtrosBox.getChildren().addAll(filtroLabel, estadoCombo, filtrarButton);
        
        // Tabla
        TableView<EnvioTableItem> tabla = new TableView<>();
        tabla.setPrefHeight(500);
        
        TableColumn<EnvioTableItem, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colId.setPrefWidth(150);
        
        TableColumn<EnvioTableItem, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> {
            EnvioTableItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getEstado().toString());
        });
        colEstado.setPrefWidth(120);
        
        TableColumn<EnvioTableItem, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaFormateada"));
        colFecha.setPrefWidth(180);
        
        TableColumn<EnvioTableItem, String> colOrigen = new TableColumn<>("Origen");
        colOrigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        colOrigen.setPrefWidth(200);
        
        TableColumn<EnvioTableItem, String> colDestino = new TableColumn<>("Destino");
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colDestino.setPrefWidth(200);
        
        TableColumn<EnvioTableItem, String> colCosto = new TableColumn<>("Costo");
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costoFormateado"));
        colCosto.setPrefWidth(120);
        
        tabla.getColumns().addAll(colId, colEstado, colFecha, colOrigen, colDestino, colCosto);
        
        // Cargar datos
        ObservableList<EnvioTableItem> datos = FXCollections.observableArrayList();
        cargarDatos(datos, estadoCombo.getValue());
        tabla.setItems(datos);
        
        // Botón filtrar
        filtrarButton.setOnAction(e -> cargarDatos(datos, estadoCombo.getValue()));
        
        // Placeholder cuando no hay datos
        Label placeholder = new Label("No hay envíos registrados");
        placeholder.setStyle("-fx-text-fill: #64748b; -fx-font-size: 16px;");
        tabla.setPlaceholder(placeholder);
        
        contenido.getChildren().addAll(titulo, filtrosBox, tabla);
        
        root.setCenter(contenido);
        
        Scene scene = new Scene(root, 1200, 800);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
    }
    
    private void cargarDatos(ObservableList<EnvioTableItem> datos, String filtroEstado) {
        datos.clear();
        java.util.List<EnvioDTO> envios = controller.obtenerEnviosUsuario();
        
        for (EnvioDTO envio : envios) {
            if (filtroEstado.equals("Todos") || envio.getEstado().toString().equals(filtroEstado)) {
                String origen = envio.getOrigen() != null ? 
                    envio.getOrigen().getCalle() + ", " + envio.getOrigen().getCiudad() : "N/A";
                String destino = envio.getDestino() != null ? 
                    envio.getDestino().getCalle() + ", " + envio.getDestino().getCiudad() : "N/A";
                
                String usuario = envio.getNombreUsuario() != null ? envio.getNombreUsuario() : "N/A";
                EnvioTableItem item = new EnvioTableItem(
                    envio.getIdEnvio(),
                    envio.getEstado(),
                    envio.getFechaCreacion(),
                    envio.getCosto(),
                    origen,
                    destino,
                    usuario
                );
                datos.add(item);
            }
        }
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

