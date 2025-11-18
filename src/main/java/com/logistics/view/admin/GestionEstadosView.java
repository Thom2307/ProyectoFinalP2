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

public class GestionEstadosView {
    private GestionEstadosController controller;
    private TableView<EnvioTableItem> tabla;
    private ObservableList<EnvioTableItem> datos;

    public GestionEstadosView() {
        this.controller = new GestionEstadosController();
    }

    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Gestión de Estados de Envíos"));
        root.setLeft(crearMenuLateral());

        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));

        Label titulo = new Label("Gestión de Estados");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));

        // Filtros
        HBox filtrosBox = new HBox(15);
        filtrosBox.setAlignment(Pos.CENTER_LEFT);
        Label filtroLabel = new Label("Filtrar:");
        ComboBox<String> estadoCombo = new ComboBox<>();
        estadoCombo.getItems().addAll("Todos", "SOLICITADO", "ASIGNADO", "EN_RUTA", "ENTREGADO", "CANCELADO");
        estadoCombo.setValue("Todos");
        Button filtrarButton = new Button("Filtrar");
        TextField buscarField = new TextField();
        buscarField.setPromptText("Buscar por ID...");
        buscarField.setPrefWidth(200);
        Button buscarButton = new Button("Buscar");
        filtrosBox.getChildren().addAll(filtroLabel, estadoCombo, filtrarButton, buscarField, buscarButton);

        // Tabla
        tabla = new TableView<>();
        tabla.setPrefHeight(400);
        datos = FXCollections.observableArrayList();

        TableColumn<EnvioTableItem, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colId.setPrefWidth(120);

        TableColumn<EnvioTableItem, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> {
            EnvioTableItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getEstado().toString());
        });
        colEstado.setPrefWidth(100);

        TableColumn<EnvioTableItem, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaFormateada"));
        colFecha.setPrefWidth(150);

        TableColumn<EnvioTableItem, String> colUsuario = new TableColumn<>("Usuario");
        colUsuario.setCellValueFactory(cellData -> {
            EnvioTableItem item = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(item.getOrigen());
        });
        colUsuario.setPrefWidth(150);

        TableColumn<EnvioTableItem, String> colCosto = new TableColumn<>("Costo");
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costoFormateado"));
        colCosto.setPrefWidth(100);

        TableColumn<EnvioTableItem, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setPrefWidth(300);
        colAcciones.setCellFactory(param -> new TableCell<EnvioTableItem, Void>() {
            private final Button btnCambiarEstado = new Button("Cambiar Estado");
            private final Button btnIncidencia = new Button("Incidencia");

            {
                btnCambiarEstado.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 11px;");
                btnIncidencia.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-font-size: 11px;");
                
                btnCambiarEstado.setOnAction(e -> {
                    EnvioTableItem item = getTableView().getItems().get(getIndex());
                    mostrarDialogoCambioEstado(item);
                });
                
                btnIncidencia.setOnAction(e -> {
                    EnvioTableItem item = getTableView().getItems().get(getIndex());
                    mostrarDialogoIncidencia(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5);
                    hbox.getChildren().addAll(btnCambiarEstado, btnIncidencia);
                    setGraphic(hbox);
                }
            }
        });

        tabla.getColumns().addAll(colId, colEstado, colFecha, colUsuario, colCosto, colAcciones);
        tabla.setItems(datos);

        // Cargar datos
        cargarDatos(estadoCombo.getValue());
        
        filtrarButton.setOnAction(e -> cargarDatos(estadoCombo.getValue()));
        buscarButton.setOnAction(e -> {
            String idBuscar = buscarField.getText();
            if (idBuscar != null && !idBuscar.isEmpty()) {
                buscarEnvio(idBuscar);
            } else {
                cargarDatos(estadoCombo.getValue());
            }
        });

        contenido.getChildren().addAll(titulo, filtrosBox, tabla);

        root.setCenter(contenido);

        Scene scene = new Scene(root, 1400, 800);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
    }

    private void cargarDatos(String filtroEstado) {
        datos.clear();
        java.util.List<EnvioDTO> envios = controller.obtenerTodosLosEnvios();
        
        for (EnvioDTO envio : envios) {
            if (filtroEstado.equals("Todos") || envio.getEstado().toString().equals(filtroEstado)) {
                String usuario = envio.getNombreUsuario() != null ? envio.getNombreUsuario() : "N/A";
                EnvioTableItem item = new EnvioTableItem(
                    envio.getIdEnvio(),
                    envio.getEstado(),
                    envio.getFechaCreacion(),
                    envio.getCosto(),
                    usuario,
                    envio.getEstado().toString()
                );
                datos.add(item);
            }
        }
    }

    private void buscarEnvio(String idEnvio) {
        datos.clear();
        EnvioDTO envio = controller.obtenerEnvio(idEnvio);
        
        if (envio != null) {
            String usuario = envio.getNombreUsuario() != null ? envio.getNombreUsuario() : "N/A";
            EnvioTableItem item = new EnvioTableItem(
                envio.getIdEnvio(),
                envio.getEstado(),
                envio.getFechaCreacion(),
                envio.getCosto(),
                usuario,
                envio.getEstado().toString()
            );
            datos.add(item);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No encontrado");
            alert.setContentText("No se encontró un envío con el ID: " + idEnvio);
            alert.showAndWait();
        }
    }

    private void mostrarDialogoCambioEstado(EnvioTableItem item) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Cambiar Estado del Envío");
        dialog.setHeaderText("Envío: " + item.getIdEnvio() + " - Estado actual: " + item.getEstado());

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label label = new Label("Seleccione la nueva acción:");
        ComboBox<String> accionCombo = new ComboBox<>();
        
        // Determinar acciones disponibles según el estado actual
        EstadoEnvio estadoActual = item.getEstado();
        Label infoLabel = null;
        if (estadoActual == EstadoEnvio.SOLICITADO) {
            accionCombo.getItems().addAll("EN_RUTA", "CANCELAR");
            infoLabel = new Label("Nota: Para asignar un repartidor, use la sección 'Asignación de Envíos'");
            infoLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        } else if (estadoActual == EstadoEnvio.ASIGNADO) {
            accionCombo.getItems().addAll("EN_RUTA", "CANCELAR");
        } else if (estadoActual == EstadoEnvio.EN_RUTA) {
            accionCombo.getItems().add("ENTREGADO");
        } else {
            accionCombo.getItems().add("(No hay acciones disponibles)");
            accionCombo.setDisable(true);
        }

        if (infoLabel != null) {
            content.getChildren().addAll(label, accionCombo, infoLabel);
        } else {
            content.getChildren().addAll(label, accionCombo);
        }
        dialog.getDialogPane().setContent(content);

        ButtonType cambiarButtonType = new ButtonType("Cambiar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cambiarButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == cambiarButtonType && accionCombo.getValue() != null) {
                return accionCombo.getValue();
            }
            return null;
        });

        java.util.Optional<String> result = dialog.showAndWait();
        result.ifPresent(accion -> {
            try {
                String accionUpper = accion.toUpperCase();
                controller.cambiarEstado(item.getIdEnvio(), accionUpper);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setContentText("Estado del envío actualizado correctamente");
                alert.showAndWait();
                
                cargarDatos("Todos");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No se pudo cambiar el estado: " + e.getMessage());
                alert.showAndWait();
            }
        });
    }

    private void mostrarDialogoIncidencia(EnvioTableItem item) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Reportar Incidencia");
        dialog.setHeaderText("Envío: " + item.getIdEnvio());

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label label = new Label("Descripción de la incidencia:");
        TextArea descripcionArea = new TextArea();
        descripcionArea.setPromptText("Ingrese los detalles de la incidencia...");
        descripcionArea.setPrefRowCount(5);
        descripcionArea.setPrefColumnCount(30);

        content.getChildren().addAll(label, descripcionArea);
        dialog.getDialogPane().setContent(content);

        ButtonType reportarButtonType = new ButtonType("Reportar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(reportarButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == reportarButtonType && !descripcionArea.getText().isEmpty()) {
                return descripcionArea.getText();
            }
            return null;
        });

        java.util.Optional<String> result = dialog.showAndWait();
        result.ifPresent(descripcion -> {
            try {
                controller.reportarIncidencia(item.getIdEnvio(), descripcion);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setContentText("Incidencia reportada correctamente");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No se pudo reportar la incidencia: " + e.getMessage());
                alert.showAndWait();
            }
        });
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
            new DashboardAdminView().crearScene()));
        menu.getChildren().add(btn);
        return menu;
    }
}

