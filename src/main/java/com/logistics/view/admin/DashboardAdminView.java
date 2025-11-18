package com.logistics.view.admin;

import com.logistics.controller.admin.GestionEstadosController;
import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.EnvioTableItem;
import com.logistics.model.enums.EstadoEnvio;
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

public class DashboardAdminView {
    private GestionEstadosController estadosController;
    private TableView<EnvioTableItem> tablaEnvios;
    private ObservableList<EnvioTableItem> datosEnvios;

    public DashboardAdminView() {
        this.estadosController = new GestionEstadosController();
        this.datosEnvios = FXCollections.observableArrayList();
    }

    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8fafc;");

        // Barra superior - Diferente a la de usuario
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(20));
        topBar.setStyle("-fx-background-color: #10b981;"); // Verde para diferenciar de usuario (azul)
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("🔐 Panel de Administración");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));
        titulo.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Cerrar Sesión");
        logoutButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 20px;");
        logoutButton.setOnAction(e -> {
            NavigationManager.getInstance().navegarA(
                new com.logistics.view.MainMenuView().crearScene()
            );
        });

        topBar.getChildren().addAll(titulo, spacer, logoutButton);

        // Contenido principal - SIN menú lateral, solo gestión de pedidos
        VBox contenido = new VBox(25);
        contenido.setPadding(new Insets(40));
        contenido.setAlignment(Pos.TOP_CENTER);

        // Título principal
        Label tituloPrincipal = new Label("Gestión de Pedidos Activos");
        tituloPrincipal.setFont(Font.font("System", FontWeight.BOLD, 32));
        tituloPrincipal.setStyle("-fx-text-fill: #1e293b;");

        // Descripción
        Label descripcion = new Label("Administre los pedidos activos del sistema. Use los botones para cambiar estados.");
        descripcion.setFont(Font.font("System", 16));
        descripcion.setStyle("-fx-text-fill: #64748b;");
        
        // Contenedor de la tabla con estilo
        VBox tablaContainer = new VBox(20);
        tablaContainer.setPadding(new Insets(30));
        tablaContainer.setStyle("-fx-background-color: white; -fx-background-radius: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 15, 0, 0, 5);");
        tablaContainer.setMaxWidth(1200);
        
        Label tablaTitulo = new Label("Lista de Pedidos Activos");
        tablaTitulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        tablaTitulo.setStyle("-fx-text-fill: #1e293b;");
        
        // Crear tabla de envíos
        tablaEnvios = crearTablaEnvios();
        cargarEnviosActivos();
        
        // Botón para refrescar
        Button refrescarButton = new Button("🔄 Actualizar Lista");
        refrescarButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 30px; -fx-background-radius: 8px;");
        refrescarButton.setOnAction(e -> cargarEnviosActivos());
        
        HBox botonContainer = new HBox();
        botonContainer.setAlignment(Pos.CENTER);
        botonContainer.getChildren().add(refrescarButton);
        
        tablaContainer.getChildren().addAll(tablaTitulo, tablaEnvios, botonContainer);
        
        contenido.getChildren().addAll(tituloPrincipal, descripcion, tablaContainer);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(contenido);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.setTop(topBar);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1400, 900);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
    }




    private TableView<EnvioTableItem> crearTablaEnvios() {
        TableView<EnvioTableItem> tabla = new TableView<>();
        tabla.setPrefHeight(600);
        tabla.setStyle("-fx-background-color: white;");

        // ID Envío
        TableColumn<EnvioTableItem, String> colId = new TableColumn<>("ID Envío");
        colId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colId.setPrefWidth(180);

        // ID Usuario (quien realizó el pedido)
        TableColumn<EnvioTableItem, String> colIdUsuario = new TableColumn<>("ID Usuario");
        colIdUsuario.setCellValueFactory(cellData -> {
            EnvioTableItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getIdUsuario());
        });
        colIdUsuario.setPrefWidth(180);

        // Estado
        TableColumn<EnvioTableItem, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> {
            EnvioTableItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getEstado().toString());
        });
        colEstado.setPrefWidth(150);

        // Columna de Acciones
        TableColumn<EnvioTableItem, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setPrefWidth(300);
        colAcciones.setCellFactory(param -> new TableCell<EnvioTableItem, Void>() {
            private final Button btnSiguiente = new Button("▶ Siguiente Estado");
            private final Button btnCancelar = new Button("✖ Cancelar");
            private final HBox botonesBox = new HBox(8);

            {
                btnSiguiente.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6px 12px;");
                btnCancelar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6px 12px;");
                
                btnSiguiente.setOnAction(e -> {
                    EnvioTableItem item = getTableView().getItems().get(getIndex());
                    cambiarSiguienteEstado(item);
                });
                
                btnCancelar.setOnAction(e -> {
                    EnvioTableItem item = getTableView().getItems().get(getIndex());
                    cancelarEnvio(item);
                });
                
                botonesBox.getChildren().addAll(btnSiguiente, btnCancelar);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    EnvioTableItem envioItem = getTableView().getItems().get(getIndex());
                    EstadoEnvio estado = envioItem.getEstado();
                    
                    // Habilitar/deshabilitar botones según el estado
                    if (estado == EstadoEnvio.EN_RUTA) {
                        // EN_RUTA solo puede ir a ENTREGADO, no se puede cancelar
                        btnSiguiente.setDisable(false);
                        btnCancelar.setDisable(true);
                        btnCancelar.setStyle("-fx-background-color: #94a3b8; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6px 12px;");
                    } else if (estado == EstadoEnvio.ASIGNADO) {
                        // ASIGNADO puede ir a EN_RUTA, pero no se puede cancelar según el código
                        btnSiguiente.setDisable(false);
                        btnCancelar.setDisable(true);
                        btnCancelar.setStyle("-fx-background-color: #94a3b8; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6px 12px;");
                    } else {
                        // SOLICITADO puede avanzar o cancelar
                        btnSiguiente.setDisable(false);
                        btnCancelar.setDisable(false);
                        btnCancelar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6px 12px;");
                    }
                    
                    setGraphic(botonesBox);
                }
            }
        });

        tabla.getColumns().addAll(colId, colIdUsuario, colEstado, colAcciones);
        tabla.setItems(datosEnvios);
        
        // Placeholder
        Label placeholder = new Label("No hay pedidos activos");
        placeholder.setStyle("-fx-text-fill: #64748b; -fx-font-size: 16px;");
        tabla.setPlaceholder(placeholder);

        return tabla;
    }
    
    private void cambiarSiguienteEstado(EnvioTableItem item) {
        EstadoEnvio estadoActual = item.getEstado();
        String accion;
        
        switch (estadoActual) {
            case SOLICITADO:
                accion = "ASIGNADO";
                break;
            case ASIGNADO:
                accion = "EN_RUTA";
                break;
            case EN_RUTA:
                accion = "ENTREGADO";
                break;
            default:
                mostrarError("No se puede avanzar desde este estado");
                return;
        }
        
        try {
            estadosController.cambiarEstado(item.getIdEnvio(), accion);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("✅ Éxito");
            alert.setHeaderText("Estado actualizado");
            alert.setContentText("El pedido " + item.getIdEnvio() + " ha cambiado a: " + accion);
            alert.showAndWait();
            
            // Refrescar la tabla
            cargarEnviosActivos();
        } catch (Exception e) {
            mostrarError("Error al cambiar el estado: " + e.getMessage());
        }
    }
    
    private void cancelarEnvio(EnvioTableItem item) {
        try {
            estadosController.cambiarEstado(item.getIdEnvio(), "CANCELAR");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("✅ Éxito");
            alert.setHeaderText("Pedido cancelado");
            alert.setContentText("El pedido " + item.getIdEnvio() + " ha sido cancelado.");
            alert.showAndWait();
            
            // Refrescar la tabla
            cargarEnviosActivos();
        } catch (Exception e) {
            mostrarError("Error al cancelar el pedido: " + e.getMessage());
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarEnviosActivos() {
        datosEnvios.clear();
        java.util.List<EnvioDTO> envios = estadosController.obtenerTodosLosEnvios();
        
        // Solo mostrar pedidos activos (SOLICITADO, ASIGNADO, EN_RUTA)
        for (EnvioDTO envio : envios) {
            EstadoEnvio estado = envio.getEstado();
            if (estado == EstadoEnvio.SOLICITADO || 
                estado == EstadoEnvio.ASIGNADO || 
                estado == EstadoEnvio.EN_RUTA) {
                
                // Crear item solo con los datos necesarios
                EnvioTableItem item = new EnvioTableItem(
                    envio.getIdEnvio(),
                    envio.getEstado(),
                    envio.getFechaCreacion(),
                    envio.getCosto(),
                    "", // origen no necesario
                    "", // destino no necesario
                    null, // nombreUsuario no necesario
                    envio.getIdUsuario() != null ? envio.getIdUsuario() : "N/A" // ID Usuario
                );
                datosEnvios.add(item);
            }
        }
    }

}

