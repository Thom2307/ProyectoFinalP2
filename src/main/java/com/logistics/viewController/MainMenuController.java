package com.logistics.viewController;

import com.logistics.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {
    @FXML
    private Button btnUsuario;
    
    @FXML
    private Button btnAdmin;
    
    @FXML
    private void onUsuarioClick() {
        NavigationManager.getInstance().navegarA(new LoginView().crearScene());
    }
    
    @FXML
    private void onAdminClick() {
        NavigationManager.getInstance().navegarA(new AdminLoginView().crearScene());
    }
}

