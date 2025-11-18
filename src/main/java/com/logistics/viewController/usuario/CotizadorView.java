package com.logistics.viewController.usuario;

import com.logistics.controller.usuario.CotizadorController;
import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CotizadorView {
    private CotizadorController controller;
    private TextField origenCalle, origenCiudad, destinoCalle, destinoCiudad;
    private TextField pesoField, latOrigen, lonOrigen, latDestino, lonDestino;
    private CheckBox seguroCheck, fragilCheck, firmaCheck, prioridadCheck;
    private Label costoTotalLabel;
    private VBox desgloseBox;

    public CotizadorView() {
        this.controller = new CotizadorController();
    }

    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Cotizador de Envíos"));
        root.setLeft(crearMenuLateral());
        
        ScrollPane contenido = new ScrollPane();
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(30));
        formBox.setMaxWidth(800);

        // Formulario
        Label titulo = new Label("Calcular Tarifa de Envío");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));

        // Origen
        grid.add(new Label("Origen:"), 0, 0);
        origenCalle = new TextField();
        origenCalle.setPromptText("Calle y número");
        grid.add(origenCalle, 1, 0);
        origenCiudad = new TextField();
        origenCiudad.setPromptText("Ciudad");
        grid.add(origenCiudad, 2, 0);
        
        grid.add(new Label("Latitud:"), 0, 1);
        latOrigen = new TextField();
        latOrigen.setPromptText("4.6097");
        grid.add(latOrigen, 1, 1);
        grid.add(new Label("Longitud:"), 0, 2);
        lonOrigen = new TextField();
        lonOrigen.setPromptText("-74.0817");
        grid.add(lonOrigen, 1, 2);

        // Destino
        grid.add(new Label("Destino:"), 0, 3);
        destinoCalle = new TextField();
        destinoCalle.setPromptText("Calle y número");
        grid.add(destinoCalle, 1, 3);
        destinoCiudad = new TextField();
        destinoCiudad.setPromptText("Ciudad");
        grid.add(destinoCiudad, 2, 3);
        
        grid.add(new Label("Latitud:"), 0, 4);
        latDestino = new TextField();
        latDestino.setPromptText("4.6533");
        grid.add(latDestino, 1, 4);
        grid.add(new Label("Longitud:"), 0, 5);
        lonDestino = new TextField();
        lonDestino.setPromptText("-74.0836");
        grid.add(lonDestino, 1, 5);

        // Peso
        grid.add(new Label("Peso (kg):"), 0, 6);
        pesoField = new TextField();
        pesoField.setPromptText("1.5");
        grid.add(pesoField, 1, 6);

        // Servicios adicionales
        Label serviciosLabel = new Label("Servicios Adicionales:");
        serviciosLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        grid.add(serviciosLabel, 0, 7);
        
        seguroCheck = new CheckBox("Seguro (+5%)");
        fragilCheck = new CheckBox("Frágil (+8%)");
        firmaCheck = new CheckBox("Firma requerida (+$3000)");
        prioridadCheck = new CheckBox("Prioridad (+15%)");
        
        VBox serviciosBox = new VBox(10, seguroCheck, fragilCheck, firmaCheck, prioridadCheck);
        grid.add(serviciosBox, 1, 7);

        Button calcularButton = new Button("Calcular Tarifa");
        calcularButton.setPrefWidth(Double.MAX_VALUE);
        calcularButton.setPrefHeight(45);
        calcularButton.setOnAction(e -> calcularTarifa());

        // Resultado
        VBox resultadoBox = new VBox(15);
        resultadoBox.setPadding(new Insets(20));
        resultadoBox.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8px;");
        
        Label resultadoLabel = new Label("Resultado:");
        resultadoLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        costoTotalLabel = new Label("$0.00");
        costoTotalLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        costoTotalLabel.setStyle("-fx-text-fill: #2563eb;");
        
        desgloseBox = new VBox(5);
        
        resultadoBox.getChildren().addAll(resultadoLabel, costoTotalLabel, new Label("Desglose:"), desgloseBox);

        formBox.getChildren().addAll(titulo, grid, calcularButton, resultadoBox);
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

    private void calcularTarifa() {
        try {
            double latO = Double.parseDouble(latOrigen.getText());
            double lonO = Double.parseDouble(lonOrigen.getText());
            double latD = Double.parseDouble(latDestino.getText());
            double lonD = Double.parseDouble(lonDestino.getText());
            double peso = Double.parseDouble(pesoField.getText());

            java.util.List<String> servicios = new java.util.ArrayList<>();
            if (seguroCheck.isSelected()) servicios.add("SEGURO");
            if (fragilCheck.isSelected()) servicios.add("FRAGIL");
            if (firmaCheck.isSelected()) servicios.add("FIRMA");
            if (prioridadCheck.isSelected()) servicios.add("PRIORIDAD");

            var tarifa = controller.calcularTarifa(latO, lonO, latD, lonD, peso, servicios);
            
            costoTotalLabel.setText(String.format("$%,.2f", tarifa.getCostoTotal()));
            
            desgloseBox.getChildren().clear();
            tarifa.getDesgloseServicios().forEach((k, v) -> {
                Label item = new Label(k + ": $" + String.format("%,.2f", v));
                desgloseBox.getChildren().add(item);
            });
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Por favor complete todos los campos correctamente: " + e.getMessage());
            alert.showAndWait();
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

