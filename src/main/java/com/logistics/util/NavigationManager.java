package com.logistics.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Gestor de navegación y sesión de usuario (Singleton).
 * Gestiona la navegación entre vistas de JavaFX y mantiene la información del usuario actual.
 */
public class NavigationManager {
    private static NavigationManager instance;
    private Stage primaryStage;
    private String usuarioActualId;
    private boolean esAdmin;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private NavigationManager() {}

    /**
     * Obtiene la instancia única del NavigationManager (Singleton).
     * 
     * @return La instancia única del NavigationManager
     */
    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    /**
     * Establece el Stage principal de JavaFX para la navegación.
     * 
     * @param stage El Stage principal de la aplicación
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Obtiene el Stage principal de JavaFX.
     * 
     * @return El Stage principal de la aplicación
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Navega a una nueva escena en el Stage principal.
     * 
     * @param scene La escena de JavaFX a la que se desea navegar
     */
    public void navegarA(Scene scene) {
        if (primaryStage != null) {
            primaryStage.setScene(scene);
        }
    }

    /**
     * Establece el usuario actualmente autenticado y su tipo.
     * 
     * @param usuarioId El identificador único del usuario
     * @param esAdmin true si el usuario es administrador, false en caso contrario
     */
    public void setUsuarioActual(String usuarioId, boolean esAdmin) {
        this.usuarioActualId = usuarioId;
        this.esAdmin = esAdmin;
    }

    /**
     * Obtiene el identificador del usuario actualmente autenticado.
     * 
     * @return El identificador único del usuario, o null si no hay usuario autenticado
     */
    public String getUsuarioActualId() {
        return usuarioActualId;
    }

    /**
     * Verifica si el usuario actual es administrador.
     * 
     * @return true si el usuario es administrador, false en caso contrario
     */
    public boolean isEsAdmin() {
        return esAdmin;
    }
}

