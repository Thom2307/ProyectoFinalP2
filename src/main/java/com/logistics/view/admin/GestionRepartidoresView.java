package com.logistics.view.admin;

import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GestionRepartidoresView {
    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Gestión de Repartidores"));
        root.setLeft(crearMenuLateral());

        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));

        Label titulo = new Label("Repartidores");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 24));

        TableView tabla = new TableView();
        Button agregarButton = new Button("+ Agregar Repartidor");

        contenido.getChildren().addAll(titulo, agregarButton, tabla);

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

