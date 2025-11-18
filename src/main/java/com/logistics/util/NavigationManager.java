package com.logistics.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationManager {
    private static NavigationManager instance;
    private Stage primaryStage;
    private String usuarioActualId;
    private boolean esAdmin;

    private NavigationManager() {}

    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void navegarA(Scene scene) {
        if (primaryStage != null) {
            primaryStage.setScene(scene);
        }
    }

    public void setUsuarioActual(String usuarioId, boolean esAdmin) {
        this.usuarioActualId = usuarioId;
        this.esAdmin = esAdmin;
    }

    public String getUsuarioActualId() {
        return usuarioActualId;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }
}

