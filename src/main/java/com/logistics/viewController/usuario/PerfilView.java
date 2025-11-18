package com.logistics.viewController.usuario;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class PerfilView {
    
    public Scene crearScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/usuario/perfil.fxml"));
            Scene scene = new Scene(loader.load(), 1200, 800);
            String cssPath = com.logistics.util.CssLoader.getCssPath();
            if (cssPath != null) {
                scene.getStylesheets().add(cssPath);
            }
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar perfil.fxml", e);
        }
    }
}

