package com.logistics.view;

import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenuView {
    
    public Scene crearScene() {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2563eb, #1e40af);");

        // Título principal
        Label titulo = new Label("Sistema de Logística y Envíos Urbanos");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 36));
        titulo.setStyle("-fx-text-fill: white;");

        Label subtitulo = new Label("Seleccione su tipo de acceso");
        subtitulo.setFont(Font.font("System", 18));
        subtitulo.setStyle("-fx-text-fill: rgba(255, 255, 255, 0.9);");

        // Card de opciones
        VBox card = new VBox(20);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 20, 0, 0, 8);");
        card.setPadding(new Insets(50));
        card.setMaxWidth(500);
        card.setAlignment(Pos.CENTER);

        Label instruccion = new Label("¿Cómo desea ingresar?");
        instruccion.setFont(Font.font("System", FontWeight.BOLD, 20));
        instruccion.setStyle("-fx-text-fill: #1e293b;");

        Button btnUsuario = new Button("👤 Ingresar como Usuario");
        btnUsuario.setPrefWidth(350);
        btnUsuario.setPrefHeight(60);
        btnUsuario.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; " +
            "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8px;");
        btnUsuario.setOnAction(e -> {
            NavigationManager.getInstance().navegarA(new LoginView().crearScene());
        });

        Button btnAdmin = new Button("🔐 Ingresar como Administrador");
        btnAdmin.setPrefWidth(350);
        btnAdmin.setPrefHeight(60);
        btnAdmin.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; " +
            "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 8px;");
        btnAdmin.setOnAction(e -> {
            NavigationManager.getInstance().navegarA(new AdminLoginView().crearScene());
        });

        card.getChildren().addAll(instruccion, btnUsuario, btnAdmin);
        root.getChildren().addAll(titulo, subtitulo, card);

        Scene scene = new Scene(root, 900, 700);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
    }
}

