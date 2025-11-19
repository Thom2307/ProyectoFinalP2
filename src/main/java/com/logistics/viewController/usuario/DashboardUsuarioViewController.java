package com.logistics.viewController.usuario;

import com.logistics.controller.usuario.DashboardUsuarioController;
import com.logistics.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardUsuarioViewController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCotizador;
    @FXML
    private Button btnCrearEnvio;
    @FXML
    private Button btnRastreo;
    @FXML
    private Button btnHistorial;
    @FXML
    private HBox cardsRow;
    @FXML
    private HBox botonesRapidos;
    @FXML
    private Button btnCrearRapido;
    @FXML
    private Button btnRastrearRapido;
    
    private DashboardUsuarioController controller;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.controller = new DashboardUsuarioController();
        inicializarCards();
    }
    
    private void inicializarCards() {
        VBox card1 = crearCardMetrica("Envíos Activos", 
            String.valueOf(controller.obtenerEnviosActivos()));
        VBox card2 = crearCardMetrica("Envíos Totales", 
            String.valueOf(controller.obtenerTotalEnvios()));
        VBox card3 = crearCardMetrica("En Tránsito", 
            String.valueOf(controller.obtenerEnTransito()));
        
        cardsRow.getChildren().addAll(card1, card2, card3);
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
    
    @FXML
    private void onLogoutClick() {
        NavigationManager.getInstance().navegarA(
            new com.logistics.viewController.LoginView().crearScene()
        );
    }
    
    @FXML
    private void onDashboardClick() {
        // Ya estamos en dashboard
    }
    
    @FXML
    private void onPerfilClick() {
        NavigationManager.getInstance().navegarA(new PerfilView().crearScene());
    }
    
    @FXML
    private void onCotizadorClick() {
        NavigationManager.getInstance().navegarA(new CotizadorView().crearScene());
    }
    
    @FXML
    private void onCrearEnvioClick() {
        NavigationManager.getInstance().navegarA(new CrearEnvioView().crearScene());
    }
    
    @FXML
    private void onRastreoClick() {
        NavigationManager.getInstance().navegarA(new RastreoView().crearScene());
    }
    
    @FXML
    private void onHistorialClick() {
        NavigationManager.getInstance().navegarA(new HistorialView().crearScene());
    }
}

