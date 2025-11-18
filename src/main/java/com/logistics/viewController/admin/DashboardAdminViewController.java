package com.logistics.viewController.admin;

import com.logistics.controller.admin.GestionEstadosController;
import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.EnvioTableItem;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.util.NavigationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardAdminViewController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private TableView<EnvioTableItem> tablaEnvios;
    @FXML
    private Button refrescarButton;
    
    private GestionEstadosController estadosController;
    private ObservableList<EnvioTableItem> datosEnvios;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.estadosController = new GestionEstadosController();
        this.datosEnvios = FXCollections.observableArrayList();
        inicializarTabla();
        cargarEnviosActivos();
    }
    
    private void inicializarTabla() {
        // ID Envío
        TableColumn<EnvioTableItem, String> colId = new TableColumn<>("ID Envío");
        colId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colId.setPrefWidth(180);

        // ID Usuario
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
                    
                    if (estado == EstadoEnvio.EN_RUTA) {
                        btnSiguiente.setDisable(false);
                        btnCancelar.setDisable(true);
                        btnCancelar.setStyle("-fx-background-color: #94a3b8; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6px 12px;");
                    } else if (estado == EstadoEnvio.ASIGNADO) {
                        btnSiguiente.setDisable(false);
                        btnCancelar.setDisable(true);
                        btnCancelar.setStyle("-fx-background-color: #94a3b8; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6px 12px;");
                    } else {
                        btnSiguiente.setDisable(false);
                        btnCancelar.setDisable(false);
                        btnCancelar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 6px 12px;");
                    }
                    
                    setGraphic(botonesBox);
                }
            }
        });

        tablaEnvios.getColumns().addAll(colId, colIdUsuario, colEstado, colAcciones);
        tablaEnvios.setItems(datosEnvios);
        
        javafx.scene.control.Label placeholder = new javafx.scene.control.Label("No hay pedidos activos");
        placeholder.setStyle("-fx-text-fill: #64748b; -fx-font-size: 16px;");
        tablaEnvios.setPlaceholder(placeholder);
    }
    
    @FXML
    private void onLogoutClick() {
        NavigationManager.getInstance().navegarA(
            new com.logistics.viewController.MainMenuView().crearScene()
        );
    }
    
    @FXML
    private void onRefrescarClick() {
        cargarEnviosActivos();
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
        
        for (EnvioDTO envio : envios) {
            EstadoEnvio estado = envio.getEstado();
            if (estado == EstadoEnvio.SOLICITADO || 
                estado == EstadoEnvio.ASIGNADO || 
                estado == EstadoEnvio.EN_RUTA) {
                
                EnvioTableItem item = new EnvioTableItem(
                    envio.getIdEnvio(),
                    envio.getEstado(),
                    envio.getFechaCreacion(),
                    envio.getCosto(),
                    "",
                    "",
                    null,
                    envio.getIdUsuario() != null ? envio.getIdUsuario() : "N/A"
                );
                datosEnvios.add(item);
            }
        }
    }
}

