package com.logistics.viewController.admin;

import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ReportesView {
    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Generador de Reportes"));
        root.setLeft(crearMenuLateral());

        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        contenido.setMaxWidth(600);

        Label titulo = new Label("Generar Reporte");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));

        ComboBox<String> tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Entregas por periodo", "Tiempos promedio", "Ingresos por servicio", "Top incidencias");
        tipoCombo.setValue("Entregas por periodo");

        DatePicker fechaInicio = new DatePicker();
        DatePicker fechaFin = new DatePicker();

        ComboBox<String> formatoCombo = new ComboBox<>();
        formatoCombo.getItems().addAll("CSV", "PDF");
        formatoCombo.setValue("CSV");

        Button generarButton = new Button("Generar Reporte");
        generarButton.setPrefWidth(Double.MAX_VALUE);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.add(new Label("Tipo:"), 0, 0);
        grid.add(tipoCombo, 1, 0);
        grid.add(new Label("Fecha Inicio:"), 0, 1);
        grid.add(fechaInicio, 1, 1);
        grid.add(new Label("Fecha Fin:"), 0, 2);
        grid.add(fechaFin, 1, 2);
        grid.add(new Label("Formato:"), 0, 3);
        grid.add(formatoCombo, 1, 3);

        contenido.getChildren().addAll(titulo, grid, generarButton);
        contenido.setAlignment(Pos.CENTER);

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
            new DashboardAdminView().crearScene()));
        menu.getChildren().add(btn);
        return menu;
    }
}

