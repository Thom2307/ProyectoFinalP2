package com.logistics;

import com.logistics.util.DatosIniciales;
import com.logistics.util.NavigationManager;
import com.logistics.viewController.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Inicializar datos
        DatosIniciales.inicializarDatos();
        
        // Configurar NavigationManager
        NavigationManager.getInstance().setPrimaryStage(primaryStage);
        
        // Crear y mostrar MainMenuView (pantalla inicial de selección)
        MainMenuView mainMenuView = new MainMenuView();
        Scene mainMenuScene = mainMenuView.crearScene();
        
        primaryStage.setTitle("Sistema de Logística y Envíos Urbanos");
        primaryStage.setScene(mainMenuScene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
