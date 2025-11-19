package com.logistics.viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class MainMenuView {
    
    public Scene crearScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainmenu.fxml"));
            Scene scene = new Scene(loader.load(), 900, 700);
            String cssPath = com.logistics.util.CssLoader.getCssPath();
            if (cssPath != null) {
                scene.getStylesheets().add(cssPath);
            }
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar mainmenu.fxml", e);
        }
    }
}

