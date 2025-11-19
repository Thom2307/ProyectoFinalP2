package com.logistics.viewController.admin;

import com.logistics.controller.admin.AdminRepartidorController;
import com.logistics.model.dto.RepartidorDTO;
import com.logistics.model.enums.EstadoRepartidor;
import com.logistics.model.enums.Ruta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GestionRepartidoresViewController implements Initializable {
    
    @FXML private TextField txtIdRepartidor;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtVehiculo;
    @FXML private ComboBox<EstadoRepartidor> cmbDisponibilidad;
    @FXML private ComboBox<String> cmbZonaCobertura;
    @FXML private ComboBox<Ruta> cmbRuta;
    
    @FXML private Button btnCrear;
    @FXML private Button btnActualizar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnCancelar;
    
    @FXML private TableView<RepartidorDTO> tablaRepartidores;
    @FXML private TableColumn<RepartidorDTO, String> colId;
    @FXML private TableColumn<RepartidorDTO, String> colNombre;
    @FXML private TableColumn<RepartidorDTO, String> colDocumento;
    @FXML private TableColumn<RepartidorDTO, String> colTelefono;
    @FXML private TableColumn<RepartidorDTO, String> colDisponibilidad;
    @FXML private TableColumn<RepartidorDTO, String> colZona;
    @FXML private TableColumn<RepartidorDTO, String> colRuta;
    @FXML private TableColumn<RepartidorDTO, Integer> colEnviosAsignados;
    @FXML private TableColumn<RepartidorDTO, Void> colAcciones;
    
    @FXML private ComboBox<EstadoRepartidor> cmbFiltroEstado;
    @FXML private ComboBox<String> cmbFiltroZona;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnLimpiarFiltros;
    @FXML private Button btnRefrescar;
    
    private AdminRepartidorController controller;
    private ObservableList<RepartidorDTO> datosRepartidores;
    private RepartidorDTO repartidorSeleccionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new AdminRepartidorController();
        datosRepartidores = FXCollections.observableArrayList();
        
        inicializarComponentes();
        cargarDatosIniciales();
        configurarTabla();
        cargarTablaRepartidores();
    }

    private void inicializarComponentes() {
        // ComboBox de disponibilidad
        cmbDisponibilidad.getItems().addAll(EstadoRepartidor.values());
        
        // ComboBox de zonas
        cmbZonaCobertura.getItems().addAll(controller.cargarComboBoxZonas());
        
        // ComboBox de rutas
        cmbRuta.getItems().addAll(Ruta.values());
        cmbRuta.setCellFactory(param -> new ListCell<Ruta>() {
            @Override
            protected void updateItem(Ruta item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre());
                }
            }
        });
        cmbRuta.setButtonCell(new ListCell<Ruta>() {
            @Override
            protected void updateItem(Ruta item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre());
                }
            }
        });
        
        // ComboBox de filtros
        cmbFiltroEstado.getItems().addAll(EstadoRepartidor.values());
        cmbFiltroZona.getItems().addAll(controller.cargarComboBoxZonas());
        
        // Deshabilitar ID (auto-generado)
        txtIdRepartidor.setDisable(true);
        
        // Configurar botones
        btnActualizar.setDisable(true);
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idRepartidor"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDisponibilidad.setCellValueFactory(cellData -> {
            EstadoRepartidor estado = cellData.getValue().getDisponibilidad();
            return new javafx.beans.property.SimpleStringProperty(
                estado != null ? estado.toString() : ""
            );
        });
        colZona.setCellValueFactory(new PropertyValueFactory<>("zonaCobertura"));
        colRuta.setCellValueFactory(cellData -> {
            Ruta ruta = cellData.getValue().getRuta();
            return new javafx.beans.property.SimpleStringProperty(
                ruta != null ? ruta.getNombre() : "Sin ruta"
            );
        });
        colEnviosAsignados.setCellValueFactory(new PropertyValueFactory<>("cantidadEnviosAsignados"));
        
        // Columna de acciones
        colAcciones.setCellFactory(param -> new TableCell<RepartidorDTO, Void>() {
            private final Button btnEditar = new Button("✏️ Editar");
            private final Button btnEliminar = new Button("🗑️ Eliminar");
            private final Button btnVer = new Button("👁️ Ver");
            private final HBox botonesBox = new HBox(5);

            {
                btnEditar.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4px 8px;");
                btnEliminar.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4px 8px;");
                btnVer.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 4px 8px;");
                
                btnEditar.setOnAction(e -> {
                    RepartidorDTO item = getTableView().getItems().get(getIndex());
                    cargarRepartidorEnFormulario(item);
                });
                
                btnEliminar.setOnAction(e -> {
                    RepartidorDTO item = getTableView().getItems().get(getIndex());
                    eliminarRepartidor(item);
                });
                
                btnVer.setOnAction(e -> {
                    RepartidorDTO item = getTableView().getItems().get(getIndex());
                    verDetalleRepartidor(item);
                });
                
                botonesBox.getChildren().addAll(btnEditar, btnVer, btnEliminar);
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
        
        // Listener para selección
        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    repartidorSeleccionado = newSelection;
                }
            }
        );
    }

    private void cargarDatosIniciales() {
        // Ya se cargan en inicializarComponentes
    }

    @FXML
    private void onCrearClick() {
        try {
            RepartidorDTO dto = obtenerDatosFormulario();
            String error = controller.obtenerMensajeErrorValidacion(dto);
            if (error != null) {
                mostrarError("Error de validación", error);
                return;
            }

            controller.crearRepartidorDesdeVista(dto);
            mostrarExito("Repartidor creado exitosamente");
            limpiarFormulario();
            cargarTablaRepartidores();
        } catch (Exception e) {
            mostrarError("Error", "Error al crear repartidor: " + e.getMessage());
        }
    }

    @FXML
    private void onActualizarClick() {
        try {
            if (repartidorSeleccionado == null) {
                mostrarError("Error", "Seleccione un repartidor para actualizar");
                return;
            }

            RepartidorDTO dto = obtenerDatosFormulario();
            dto.setIdRepartidor(repartidorSeleccionado.getIdRepartidor());
            
            String error = controller.obtenerMensajeErrorValidacion(dto);
            if (error != null) {
                mostrarError("Error de validación", error);
                return;
            }

            controller.actualizarRepartidorDesdeVista(dto);
            mostrarExito("Repartidor actualizado correctamente");
            limpiarFormulario();
            cargarTablaRepartidores();
        } catch (Exception e) {
            mostrarError("Error", "Error al actualizar repartidor: " + e.getMessage());
        }
    }

    @FXML
    private void onLimpiarClick() {
        limpiarFormulario();
    }

    @FXML
    private void onCancelarClick() {
        limpiarFormulario();
    }

    @FXML
    private void onBuscarClick() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            cargarTablaRepartidores();
            return;
        }

        List<RepartidorDTO> resultados;
        if (texto.matches("^[0-9]+$")) {
            // Buscar por documento
            resultados = controller.buscarRepartidorPorDocumento(texto);
        } else {
            // Buscar por nombre
            resultados = controller.buscarRepartidorPorNombre(texto);
        }

        datosRepartidores.clear();
        datosRepartidores.addAll(resultados);
    }

    @FXML
    private void onLimpiarFiltrosClick() {
        cmbFiltroEstado.getSelectionModel().clearSelection();
        cmbFiltroZona.getSelectionModel().clearSelection();
        txtBuscar.clear();
        cargarTablaRepartidores();
    }

    @FXML
    private void onRefrescarClick() {
        cargarTablaRepartidores();
    }

    @FXML
    private void onFiltroEstadoChange() {
        aplicarFiltros();
    }

    @FXML
    private void onFiltroZonaChange() {
        aplicarFiltros();
    }

    private void aplicarFiltros() {
        List<RepartidorDTO> todos = controller.cargarTablaRepartidores();
        
        // Filtrar por estado
        if (cmbFiltroEstado.getValue() != null) {
            todos = controller.filtrarRepartidoresPorEstado(cmbFiltroEstado.getValue());
        }
        
        // Filtrar por zona
        if (cmbFiltroZona.getValue() != null) {
            todos = todos.stream()
                    .filter(r -> r.getZonaCobertura() != null && 
                               r.getZonaCobertura().equalsIgnoreCase(cmbFiltroZona.getValue()))
                    .collect(java.util.stream.Collectors.toList());
        }

        datosRepartidores.clear();
        datosRepartidores.addAll(todos);
    }

    private void cargarTablaRepartidores() {
        datosRepartidores.clear();
        datosRepartidores.addAll(controller.cargarTablaRepartidores());
    }

    private void cargarRepartidorEnFormulario(RepartidorDTO dto) {
        txtIdRepartidor.setText(dto.getIdRepartidor());
        txtNombre.setText(dto.getNombre());
        txtDocumento.setText(dto.getDocumento());
        txtTelefono.setText(dto.getTelefono());
        txtVehiculo.setText(dto.getVehiculo());
        cmbDisponibilidad.setValue(dto.getDisponibilidad());
        cmbZonaCobertura.setValue(dto.getZonaCobertura());
        cmbRuta.setValue(dto.getRuta());
        
        btnCrear.setDisable(true);
        btnActualizar.setDisable(false);
        repartidorSeleccionado = dto;
    }

    private void eliminarRepartidor(RepartidorDTO dto) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar este repartidor?");
        confirmacion.setContentText("Repartidor: " + dto.getNombre());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    controller.eliminarRepartidorDesdeVista(dto.getIdRepartidor());
                    mostrarExito("Repartidor eliminado");
                    cargarTablaRepartidores();
                } catch (Exception e) {
                    mostrarError("Error", e.getMessage());
                }
            }
        });
    }

    private void verDetalleRepartidor(RepartidorDTO dto) {
        RepartidorDTO detalle = controller.verDetalleRepartidor(dto.getIdRepartidor());
        List<com.logistics.model.dto.EnvioDTO> envios = controller.verEnviosDeRepartidor(dto.getIdRepartidor());
        
        StringBuilder info = new StringBuilder();
        info.append("=== DETALLE DE REPARTIDOR ===\n\n");
        info.append("ID: ").append(detalle.getIdRepartidor()).append("\n");
        info.append("Nombre: ").append(detalle.getNombre()).append("\n");
        info.append("Documento: ").append(detalle.getDocumento()).append("\n");
        info.append("Teléfono: ").append(detalle.getTelefono()).append("\n");
        info.append("Vehículo: ").append(detalle.getVehiculo()).append("\n");
        info.append("Estado: ").append(detalle.getDisponibilidad()).append("\n");
        info.append("Zona: ").append(detalle.getZonaCobertura()).append("\n");
        info.append("Envíos asignados: ").append(envios.size()).append("\n");
        info.append("Tasa de entrega: ").append(String.format("%.2f", controller.calcularTasaEntrega(dto.getIdRepartidor()))).append("%\n");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalle de Repartidor");
        alert.setHeaderText("Información del Repartidor");
        alert.setContentText(info.toString());
        alert.showAndWait();
    }

    private RepartidorDTO obtenerDatosFormulario() {
        RepartidorDTO dto = new RepartidorDTO();
        dto.setIdRepartidor(txtIdRepartidor.getText().trim());
        dto.setNombre(txtNombre.getText().trim());
        dto.setDocumento(txtDocumento.getText().trim());
        dto.setTelefono(txtTelefono.getText().trim());
        dto.setVehiculo(txtVehiculo.getText().trim());
        dto.setDisponibilidad(cmbDisponibilidad.getValue());
        dto.setZonaCobertura(cmbZonaCobertura.getValue());
        dto.setRuta(cmbRuta.getValue());
        return dto;
    }

    private void limpiarFormulario() {
        txtIdRepartidor.clear();
        txtNombre.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtVehiculo.clear();
        cmbDisponibilidad.getSelectionModel().clearSelection();
        cmbZonaCobertura.getSelectionModel().clearSelection();
        cmbRuta.getSelectionModel().clearSelection();
        
        btnCrear.setDisable(false);
        btnActualizar.setDisable(true);
        repartidorSeleccionado = null;
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

