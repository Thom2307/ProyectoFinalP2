package com.logistics.view.usuario;

import com.logistics.controller.usuario.DashboardUsuarioController;
import com.logistics.util.NavigationManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DashboardUsuarioView {
    private DashboardUsuarioController controller;

    public DashboardUsuarioView() {
        this.controller = new DashboardUsuarioController();
    }

    public Scene crearScene() {
        BorderPane root = new BorderPane();

        // Barra superior
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2563eb;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("Dashboard - Usuario");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        titulo.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Cerrar Sesión");
        logoutButton.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> {
            NavigationManager.getInstance().navegarA(
                new com.logistics.view.LoginView().crearScene()
            );
        });

        topBar.getChildren().addAll(titulo, spacer, logoutButton);

        // Menú lateral
        VBox menuLateral = new VBox(10);
        menuLateral.setPadding(new Insets(20));
        menuLateral.setStyle("-fx-background-color: #1e293b; -fx-min-width: 200px;");
        menuLateral.setPrefWidth(200);

        Button btnDashboard = crearBotonMenu("📊 Dashboard", true);
        Button btnPerfil = crearBotonMenu("👤 Perfil", false);
        Button btnCotizador = crearBotonMenu("💰 Cotizar", false);
        Button btnCrearEnvio = crearBotonMenu("📦 Crear Envío", false);
        Button btnRastreo = crearBotonMenu("🔍 Rastrear", false);
        Button btnHistorial = crearBotonMenu("📋 Historial", false);

        btnPerfil.setOnAction(e -> NavigationManager.getInstance().navegarA(
            new PerfilView().crearScene()));
        btnCotizador.setOnAction(e -> NavigationManager.getInstance().navegarA(
            new CotizadorView().crearScene()));
        btnCrearEnvio.setOnAction(e -> NavigationManager.getInstance().navegarA(
            new CrearEnvioView().crearScene()));
        btnRastreo.setOnAction(e -> NavigationManager.getInstance().navegarA(
            new RastreoView().crearScene()));
        btnHistorial.setOnAction(e -> NavigationManager.getInstance().navegarA(
            new HistorialView().crearScene()));

        menuLateral.getChildren().addAll(btnDashboard, btnPerfil, btnCotizador, 
            btnCrearEnvio, btnRastreo, btnHistorial);

        // Contenido central
        ScrollPane contenido = new ScrollPane();
        VBox contenidoBox = new VBox(20);
        contenidoBox.setPadding(new Insets(30));

        Label bienvenida = new Label("Bienvenido");
        bienvenida.setFont(Font.font("System", FontWeight.BOLD, 28));

        // Cards de resumen
        HBox cardsRow = new HBox(20);
        cardsRow.setPadding(new Insets(20, 0, 20, 0));

        VBox card1 = crearCardMetrica("Envíos Activos", 
            String.valueOf(controller.obtenerEnviosActivos()));
        VBox card2 = crearCardMetrica("Envíos Totales", 
            String.valueOf(controller.obtenerTotalEnvios()));
        VBox card3 = crearCardMetrica("En Tránsito", 
            String.valueOf(controller.obtenerEnTransito()));

        cardsRow.getChildren().addAll(card1, card2, card3);

        // Acceso rápido
        Label accesoRapido = new Label("Acceso Rápido");
        accesoRapido.setFont(Font.font("System", FontWeight.BOLD, 20));

        HBox botonesRapidos = new HBox(15);
        Button btnCrearRapido = new Button("➕ Crear Nuevo Envío");
        btnCrearRapido.setPrefWidth(200);
        btnCrearRapido.setPrefHeight(50);
        btnCrearRapido.setOnAction(e -> NavigationManager.getInstance().navegarA(
            new CrearEnvioView().crearScene()));

        Button btnRastrearRapido = new Button("🔍 Rastrear Envío");
        btnRastrearRapido.setPrefWidth(200);
        btnRastrearRapido.setPrefHeight(50);
        btnRastrearRapido.setOnAction(e -> NavigationManager.getInstance().navegarA(
            new RastreoView().crearScene()));

        botonesRapidos.getChildren().addAll(btnCrearRapido, btnRastrearRapido);

        contenidoBox.getChildren().addAll(bienvenida, cardsRow, accesoRapido, botonesRapidos);
        contenido.setContent(contenidoBox);
        contenido.setFitToWidth(true);

        root.setTop(topBar);
        root.setLeft(menuLateral);
        root.setCenter(contenido);

        Scene scene = new Scene(root, 1200, 800);
        String cssPath = com.logistics.util.CssLoader.getCssPath();
        if (cssPath != null) {
            scene.getStylesheets().add(cssPath);
        }
        return scene;
    }

    private Button crearBotonMenu(String texto, boolean activo) {
        Button btn = new Button(texto);
        btn.setPrefWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(activo ? 
            "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12px;" :
            "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 12px;");
        return btn;
    }

    private VBox crearCardMetrica(String titulo, String valor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        card.setPrefWidth(200);

        Label valorLabel = new Label(valor);
        valorLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        valorLabel.setStyle("-fx-text-fill: #2563eb;");

        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");

        card.getChildren().addAll(valorLabel, tituloLabel);
        return card;
    }
}

