package com.logistics.view.admin;

import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AsignacionEnviosView {
    public Scene crearScene() {
        BorderPane root = new BorderPane();
        root.setTop(crearTopBar("Asignación de Envíos"));
        root.setLeft(crearMenuLateral());

        HBox contenido = new HBox(20);
        contenido.setPadding(new Insets(30));

        VBox enviosBox = new VBox(10);
        enviosBox.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-padding: 20px;");
        Label titulo1 = new Label("Envíos Sin Asignar");
        titulo1.setFont(Font.font("System", FontWeight.BOLD, 18));
        ListView listaEnvios = new ListView();
        enviosBox.getChildren().addAll(titulo1, listaEnvios);

        VBox repartidoresBox = new VBox(10);
        repartidoresBox.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-padding: 20px;");
        Label titulo2 = new Label("Repartidores Disponibles");
        titulo2.setFont(Font.font("System", FontWeight.BOLD, 18));
        ListView listaRepartidores = new ListView();
        repartidoresBox.getChildren().addAll(titulo2, listaRepartidores);

        contenido.getChildren().addAll(enviosBox, repartidoresBox);
        HBox.setHgrow(enviosBox, Priority.ALWAYS);
        HBox.setHgrow(repartidoresBox, Priority.ALWAYS);

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

