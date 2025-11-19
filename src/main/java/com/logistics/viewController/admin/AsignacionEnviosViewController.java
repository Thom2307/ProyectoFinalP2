package com.logistics.viewController.admin;

import com.logistics.controller.admin.AdminRepartidorController;
import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.RepartidorDTO;
import com.logistics.model.enums.EstadoEnvio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AsignacionEnviosViewController implements Initializable {
    
    @FXML private TableView<EnvioDTO> tablaEnviosDisponibles;
    @FXML private TableColumn<EnvioDTO, String> colEnvioId;
    @FXML private TableColumn<EnvioDTO, String> colEnvioOrigen;
    @FXML private TableColumn<EnvioDTO, String> colEnvioDestino;
    @FXML private TableColumn<EnvioDTO, String> colEnvioEstado;
    @FXML private TableColumn<EnvioDTO, Double> colEnvioPeso;
    @FXML private TableColumn<EnvioDTO, Void> colEnvioAcciones;
    
    @FXML private TableView<RepartidorDTO> tablaRepartidores;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorId;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorNombre;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorZona;
    @FXML private TableColumn<RepartidorDTO, String> colRepartidorEstado;
    @FXML private TableColumn<RepartidorDTO, Integer> colRepartidorEnvios;
    @FXML private TableColumn<RepartidorDTO, Void> colRepartidorAcciones;
    
    @FXML private ComboBox<RepartidorDTO> cmbRepartidorAsignar;
    @FXML private Button btnAsignarEnvio;
    @FXML private Button btnReasignar;
    @FXML private Button btnLiberar;
    @FXML private Button btnRefrescar;
    
    @FXML private VBox panelRuta;
    @FXML private Label lblRutaInfo;
    @FXML private TextArea txtRutaDetalle;
    
    private AdminRepartidorController controller;
    private ObservableList<EnvioDTO> datosEnvios;
    private ObservableList<RepartidorDTO> datosRepartidores;
    private EnvioDTO envioSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new AdminRepartidorController();
        datosEnvios = FXCollections.observableArrayList();
        datosRepartidores = FXCollections.observableArrayList();
        
        configurarTablas();
        cargarDatos();
    }

    private void configurarTablas() {
        // Configurar tabla de envíos
        colEnvioId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colEnvioOrigen.setCellValueFactory(cellData -> {
            EnvioDTO envio = cellData.getValue();
            if (envio.getOrigen() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                    envio.getOrigen().getCalle() + ", " + envio.getOrigen().getCiudad()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });
        colEnvioDestino.setCellValueFactory(cellData -> {
            EnvioDTO envio = cellData.getValue();
            if (envio.getDestino() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                    envio.getDestino().getCalle() + ", " + envio.getDestino().getCiudad()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });
        colEnvioEstado.setCellValueFactory(cellData -> {
            EstadoEnvio estado = cellData.getValue().getEstado();
            return new javafx.beans.property.SimpleStringProperty(
                estado != null ? estado.toString() : "N/A"
            );
        });
        colEnvioPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        
        colEnvioAcciones.setCellFactory(param -> new TableCell<EnvioDTO, Void>() {
            private final Button btnAsignar = new Button("📌 Asignar");
            private final Button btnVerRuta = new Button("🗺️ Ver Ruta");
            private final HBox botonesBox = new HBox(5);

            {
                btnAsignar.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4px 8px;");
                btnVerRuta.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4px 8px;");
                
                btnAsignar.setOnAction(e -> {
                    EnvioDTO item = getTableView().getItems().get(getIndex());
                    seleccionarEnvioParaAsignar(item);
                });
                
                btnVerRuta.setOnAction(e -> {
                    EnvioDTO item = getTableView().getItems().get(getIndex());
                    mostrarRuta(item);
                });
                
                botonesBox.getChildren().addAll(btnAsignar, btnVerRuta);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botonesBox);
                }
            }
        });

        tablaEnviosDisponibles.setItems(datosEnvios);
        
        // Configurar tabla de repartidores
        colRepartidorId.setCellValueFactory(new PropertyValueFactory<>("idRepartidor"));
        colRepartidorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRepartidorZona.setCellValueFactory(new PropertyValueFactory<>("zonaCobertura"));
        colRepartidorEstado.setCellValueFactory(cellData -> {
            var estado = cellData.getValue().getDisponibilidad();
            return new javafx.beans.property.SimpleStringProperty(
                estado != null ? estado.toString() : "N/A"
            );
        });
        colRepartidorEnvios.setCellValueFactory(new PropertyValueFactory<>("cantidadEnviosAsignados"));
        
        colRepartidorAcciones.setCellFactory(param -> new TableCell<RepartidorDTO, Void>() {
            private final Button btnSeleccionar = new Button("✓ Seleccionar");
            private final HBox botonesBox = new HBox(5);

            {
                btnSeleccionar.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4px 8px;");
                
                btnSeleccionar.setOnAction(e -> {
                    RepartidorDTO item = getTableView().getItems().get(getIndex());
                    seleccionarRepartidor(item);
                });
                
                botonesBox.getChildren().add(btnSeleccionar);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botonesBox);
                }
            }
        });

        tablaRepartidores.setItems(datosRepartidores);
        
        // Configurar ComboBox
        cmbRepartidorAsignar.setCellFactory(param -> new ListCell<RepartidorDTO>() {
            @Override
            protected void updateItem(RepartidorDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre() + " - " + item.getZonaCobertura() + " (" + item.getDisponibilidad() + ")");
                }
            }
        });
    }

    private void cargarDatos() {
        cargarEnviosDisponibles();
        cargarRepartidoresDisponibles();
    }

    private void cargarEnviosDisponibles() {
        datosEnvios.clear();
        List<EnvioDTO> envios = controller.mostrarEnviosDisponiblesParaAsignar();
        datosEnvios.addAll(envios);
    }

    private void cargarRepartidoresDisponibles() {
        datosRepartidores.clear();
        List<RepartidorDTO> repartidores = controller.mostrarRepartidoresDisponibles();
        datosRepartidores.addAll(repartidores);
        
        // Cargar en ComboBox
        cmbRepartidorAsignar.getItems().clear();
        cmbRepartidorAsignar.getItems().addAll(repartidores);
    }

    @FXML
    private void onAsignarEnvioClick() {
        if (envioSeleccionado == null) {
            mostrarError("Error", "Seleccione un envío para asignar");
            return;
        }

        RepartidorDTO repartidor = cmbRepartidorAsignar.getValue();
        if (repartidor == null) {
            mostrarError("Error", "Seleccione un repartidor");
            return;
        }

        try {
            controller.asignarEnvioARepartidor(envioSeleccionado.getIdEnvio(), repartidor.getIdRepartidor());
            mostrarExito("Envío asignado correctamente al repartidor " + repartidor.getNombre());
            cargarDatos();
            limpiarSeleccion();
        } catch (Exception e) {
            mostrarError("Error", "Error al asignar envío: " + e.getMessage());
        }
    }

    @FXML
    private void onReasignarClick() {
        if (envioSeleccionado == null) {
            mostrarError("Error", "Seleccione un envío para reasignar");
            return;
        }

        RepartidorDTO nuevoRepartidor = cmbRepartidorAsignar.getValue();
        if (nuevoRepartidor == null) {
            mostrarError("Error", "Seleccione un nuevo repartidor");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar reasignación");
        confirmacion.setHeaderText("¿Desea reasignar este envío?");
        confirmacion.setContentText("El envío será asignado a: " + nuevoRepartidor.getNombre());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    controller.reasignarEnvio(envioSeleccionado.getIdEnvio(), nuevoRepartidor.getIdRepartidor());
                    mostrarExito("Envío reasignado exitosamente");
                    cargarDatos();
                    limpiarSeleccion();
                } catch (Exception e) {
                    mostrarError("Error", "Error al reasignar envío: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void onLiberarClick() {
        if (envioSeleccionado == null) {
            mostrarError("Error", "Seleccione un envío para liberar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar liberación");
        confirmacion.setHeaderText("¿Desea liberar este envío del repartidor?");
        confirmacion.setContentText("El envío quedará sin repartidor asignado");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    controller.liberarEnvioDeRepartidor(envioSeleccionado.getIdEnvio());
                    mostrarExito("Envío liberado exitosamente");
                    cargarDatos();
                    limpiarSeleccion();
                } catch (Exception e) {
                    mostrarError("Error", "Error al liberar envío: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void onRefrescarClick() {
        cargarDatos();
    }

    private void seleccionarEnvioParaAsignar(EnvioDTO envio) {
        envioSeleccionado = envio;
        mostrarRuta(envio);
        
        // Filtrar repartidores disponibles según el municipio del destino
        if (envio.getDestino() != null && envio.getDestino().getCiudad() != null) {
            String municipio = envio.getDestino().getCiudad();
            List<RepartidorDTO> repartidoresFiltrados = 
                controller.obtenerRepartidoresDisponiblesPorMunicipio(municipio);
            
            datosRepartidores.clear();
            datosRepartidores.addAll(repartidoresFiltrados);
            
            // Actualizar ComboBox
            cmbRepartidorAsignar.getItems().clear();
            cmbRepartidorAsignar.getItems().addAll(repartidoresFiltrados);
            
            if (repartidoresFiltrados.isEmpty()) {
                mostrarError("Sin repartidores", 
                    "No hay repartidores disponibles para el municipio: " + municipio);
            }
        } else {
            // Si no hay destino, mostrar todos los repartidores
            cargarRepartidoresDisponibles();
        }
    }

    private void seleccionarRepartidor(RepartidorDTO repartidor) {
        cmbRepartidorAsignar.setValue(repartidor);
    }

    private void mostrarRuta(EnvioDTO envio) {
        if (envio == null || envio.getOrigen() == null || envio.getDestino() == null) {
            lblRutaInfo.setText("Ruta no disponible");
            txtRutaDetalle.setText("No hay información de ruta disponible para este envío.");
            return;
        }

        // Calcular distancia aproximada
        double latOrigen = envio.getOrigen().getLat();
        double lonOrigen = envio.getOrigen().getLon();
        double latDestino = envio.getDestino().getLat();
        double lonDestino = envio.getDestino().getLon();
        
        double distancia = Math.hypot(latDestino - latOrigen, lonDestino - lonOrigen) * 111; // Aproximación en km
        
        StringBuilder rutaInfo = new StringBuilder();
        rutaInfo.append("=== RUTA DE ENTREGA ===\n\n");
        rutaInfo.append("ORIGEN:\n");
        rutaInfo.append("  Dirección: ").append(envio.getOrigen().getCalle()).append("\n");
        rutaInfo.append("  Ciudad: ").append(envio.getOrigen().getCiudad()).append("\n");
        rutaInfo.append("  Coordenadas: (").append(String.format("%.4f", latOrigen))
                 .append(", ").append(String.format("%.4f", lonOrigen)).append(")\n\n");
        
        rutaInfo.append("DESTINO:\n");
        rutaInfo.append("  Dirección: ").append(envio.getDestino().getCalle()).append("\n");
        rutaInfo.append("  Ciudad: ").append(envio.getDestino().getCiudad()).append("\n");
        rutaInfo.append("  Coordenadas: (").append(String.format("%.4f", latDestino))
                 .append(", ").append(String.format("%.4f", lonDestino)).append(")\n\n");
        
        rutaInfo.append("DISTANCIA APROXIMADA: ").append(String.format("%.2f", distancia)).append(" km\n");
        rutaInfo.append("PESO: ").append(envio.getPeso()).append(" kg\n");
        
        if (envio.getIdRepartidor() != null) {
            rutaInfo.append("\nREPARTIDOR ASIGNADO: ").append(envio.getNombreRepartidor() != null ? 
                envio.getNombreRepartidor() : envio.getIdRepartidor()).append("\n");
        } else {
            rutaInfo.append("\nREPARTIDOR: Sin asignar\n");
        }

        lblRutaInfo.setText("Ruta del Envío: " + envio.getIdEnvio());
        txtRutaDetalle.setText(rutaInfo.toString());
    }

    private void limpiarSeleccion() {
        envioSeleccionado = null;
        cmbRepartidorAsignar.getSelectionModel().clearSelection();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

