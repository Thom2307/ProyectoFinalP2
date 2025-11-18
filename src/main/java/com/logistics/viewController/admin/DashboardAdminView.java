package com.logistics.viewController.admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class DashboardAdminView {
    
    public Scene crearScene() {
        try {
            java.net.URL url = getClass().getResource("/view/admin/dashboardadmin.fxml");
            if (url == null) {
                throw new RuntimeException("No se encontró el archivo /view/admin/dashboardadmin.fxml en el classpath. " +
                    "Asegúrate de que el archivo esté en src/main/resources/view/admin/dashboardadmin.fxml");
            }
            
            FXMLLoader loader = new FXMLLoader(url);
            javafx.scene.Parent root = loader.load();
            Scene scene = new Scene(root, 1400, 900);
            
            String cssPath = com.logistics.util.CssLoader.getCssPath();
            if (cssPath != null) {
                scene.getStylesheets().add(cssPath);
            }
            return scene;
        } catch (javafx.fxml.LoadException e) {
            e.printStackTrace();
            String errorMsg = "Error al cargar el FXML dashboardadmin.fxml.\n";
            if (e.getCause() != null) {
                errorMsg += "Causa: " + e.getCause().getMessage() + "\n";
                if (e.getCause().getCause() != null) {
                    errorMsg += "Causa raíz: " + e.getCause().getCause().getMessage();
                }
            }
            throw new RuntimeException(errorMsg, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error inesperado al cargar dashboardadmin.fxml: " + e.getMessage(), e);
        }
    }
}

