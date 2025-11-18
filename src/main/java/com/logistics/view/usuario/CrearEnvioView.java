package com.logistics.view.usuario;

import com.logistics.controller.usuario.CrearEnvioController;
import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CrearEnvioView {
    private CrearEnvioController controller;

    public CrearEnvioView() {
        this.controller = new CrearEnvioController();
    }

    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Crear Nuevo Envío"));
        root.setLeft(crearMenuLateral());

        ScrollPane contenido = new ScrollPane();
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(30));
        formBox.setMaxWidth(800);

        Label titulo = new Label("Nuevo Envío");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));

        Label origenLabel = new Label("Origen:");
        origenLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        ComboBox<String> origenCombo = new ComboBox<>();
        origenCombo.setPromptText("Seleccionar dirección origen (Quindío)");
        origenCombo.setPrefWidth(400);
        controller.cargarDirecciones(origenCombo);
        grid.add(origenLabel, 0, 0);
        grid.add(origenCombo, 1, 0);

        Label destinoLabel = new Label("Destino:");
        destinoLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        ComboBox<String> destinoCombo = new ComboBox<>();
        destinoCombo.setPromptText("Seleccionar dirección destino (Quindío)");
        destinoCombo.setPrefWidth(400);
        controller.cargarDirecciones(destinoCombo);
        grid.add(destinoLabel, 0, 1);
        grid.add(destinoCombo, 1, 1);
        
        // Información sobre municipios disponibles
        Label infoLabel = new Label("ℹ️ Direcciones disponibles en: Armenia, Calarcá, Montenegro, La Tebaida, Quimbaya, Salento, Circasia, Filandia");
        infoLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        grid.add(infoLabel, 1, 2);
        GridPane.setColumnSpan(infoLabel, 2);

        TextField pesoField = new TextField();
        pesoField.setPromptText("Ejemplo: 1.5");
        grid.add(new Label("Peso (kg):"), 0, 3);
        grid.add(pesoField, 1, 3);

        CheckBox seguroCheck = new CheckBox("Seguro (+5%)");
        CheckBox fragilCheck = new CheckBox("Frágil (+8%)");
        CheckBox firmaCheck = new CheckBox("Firma requerida (+$3000)");
        CheckBox prioridadCheck = new CheckBox("Prioridad (+15%)");
        VBox serviciosBox = new VBox(10, seguroCheck, fragilCheck, firmaCheck, prioridadCheck);
        grid.add(new Label("Servicios Adicionales:"), 0, 4);
        grid.add(serviciosBox, 1, 4);

        ComboBox<String> metodoPagoCombo = new ComboBox<>();
        metodoPagoCombo.getItems().addAll("TARJETA", "PSE");
        metodoPagoCombo.setValue("TARJETA");
        grid.add(new Label("Método de Pago:"), 0, 5);
        grid.add(metodoPagoCombo, 1, 5);

        Label costoLabel = new Label("Costo estimado: $0.00");
        costoLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        costoLabel.setStyle("-fx-text-fill: #2563eb;");
        grid.add(costoLabel, 1, 6);

        Button crearButton = new Button("Crear Envío");
        crearButton.setPrefWidth(Double.MAX_VALUE);
        crearButton.setPrefHeight(45);
        crearButton.setOnAction(e -> {
            try {
                java.util.List<String> servicios = new java.util.ArrayList<>();
                if (seguroCheck.isSelected()) servicios.add("SEGURO");
                if (fragilCheck.isSelected()) servicios.add("FRAGIL");
                if (firmaCheck.isSelected()) servicios.add("FIRMA");
                if (prioridadCheck.isSelected()) servicios.add("PRIORIDAD");

                // Validaciones
                if (origenCombo.getValue() == null || origenCombo.getValue().isEmpty()) {
                    throw new IllegalArgumentException("Por favor seleccione una dirección de origen");
                }
                if (destinoCombo.getValue() == null || destinoCombo.getValue().isEmpty()) {
                    throw new IllegalArgumentException("Por favor seleccione una dirección de destino");
                }
                if (pesoField.getText() == null || pesoField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Por favor ingrese el peso del paquete");
                }
                
                double peso = Double.parseDouble(pesoField.getText());
                if (peso <= 0) {
                    throw new IllegalArgumentException("El peso debe ser mayor a 0");
                }
                
                controller.crearEnvio(
                    origenCombo.getValue(),
                    destinoCombo.getValue(),
                    peso,
                    servicios,
                    metodoPagoCombo.getValue()
                );
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("✅ Éxito");
                alert.setHeaderText("Envío creado correctamente");
                alert.setContentText("Su envío ha sido registrado y está siendo procesado.");
                alert.showAndWait();
                
                NavigationManager.getInstance().navegarA(new DashboardUsuarioView().crearScene());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de Validación");
                alert.setContentText("Por favor ingrese un peso válido (número mayor a 0)");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se pudo crear el envío");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        formBox.getChildren().addAll(titulo, grid, crearButton);
        contenido.setContent(formBox);
        contenido.setFitToWidth(true);

        root.setCenter(contenido);

        Scene scene = new Scene(root, 1200, 800);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
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

