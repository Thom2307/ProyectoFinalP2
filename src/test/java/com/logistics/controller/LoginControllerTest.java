package com.logistics.controller;

import com.logistics.model.dto.UsuarioDTO;
import com.logistics.model.entities.Usuario;
import com.logistics.repository.UsuarioRepository;
import com.logistics.service.UsuarioService;
import com.logistics.util.NavigationManager;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Test
    void testIniciarSesion_Exitoso_UsuarioNormal() {
        // Arrange
        Label errorLabel = mock(Label.class);
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String correo = "usuario@test.com";
        String contrasena = "password123";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario("u12345678");
        
        Usuario usuarioCompleto = new Usuario();
        usuarioCompleto.setIdUsuario("u12345678");
        usuarioCompleto.setEsAdmin(false);

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.iniciarSesion(correo, contrasena)).thenReturn(usuarioDTO);
                });
            MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById("u12345678")).thenReturn(usuarioCompleto);
                });
            MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class)) {
            
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            
            LoginController controller = new LoginController();
            
            // Act
            controller.iniciarSesion(correo, contrasena, errorLabel);
            
            // Verify
            verify(navigationManager, times(1)).setUsuarioActual("u12345678", false);
            verify(navigationManager, times(1)).navegarA(any());
            verify(errorLabel, never()).setText(anyString());
        }
    }

    @Test
    void testIniciarSesion_Exitoso_UsuarioAdmin() {
        // Arrange
        Label errorLabel = mock(Label.class);
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String correo = "admin@logistics.com";
        String contrasena = "admin123";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario("u12345678");
        
        Usuario usuarioCompleto = new Usuario();
        usuarioCompleto.setIdUsuario("u12345678");
        usuarioCompleto.setEsAdmin(true);

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.iniciarSesion(correo, contrasena)).thenReturn(usuarioDTO);
                });
            MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById("u12345678")).thenReturn(usuarioCompleto);
                });
            MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class)) {
            
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            
            LoginController controller = new LoginController();
            
            // Act
            controller.iniciarSesion(correo, contrasena, errorLabel);
            
            // Verify
            verify(navigationManager, times(1)).setUsuarioActual("u12345678", true);
            verify(navigationManager, times(1)).navegarA(any());
        }
    }

    @Test
    void testIniciarSesion_CorreoVacio() {
        // Arrange
        Label errorLabel = mock(Label.class);
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class)) {
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            
            LoginController controller = new LoginController();
            
            // Act
            controller.iniciarSesion("", "password", errorLabel);
            
            // Verify
            verify(errorLabel, times(1)).setText("Por favor complete todos los campos");
            verify(errorLabel, times(1)).setVisible(true);
            verify(navigationManager, never()).setUsuarioActual(anyString(), anyBoolean());
        }
    }

    @Test
    void testIniciarSesion_ContrasenaVacia() {
        // Arrange
        Label errorLabel = mock(Label.class);
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class)) {
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            
            LoginController controller = new LoginController();
            
            // Act
            controller.iniciarSesion("test@test.com", "", errorLabel);
            
            // Verify
            verify(errorLabel, times(1)).setText("Por favor complete todos los campos");
            verify(errorLabel, times(1)).setVisible(true);
        }
    }

    @Test
    void testIniciarSesion_CredencialesInvalidas() {
        // Arrange
        Label errorLabel = mock(Label.class);
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String correo = "usuario@test.com";
        String contrasena = "wrongpassword";

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.iniciarSesion(correo, contrasena))
                        .thenThrow(new IllegalArgumentException("Credenciales inválidas"));
                });
            MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class)) {
            
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            
            LoginController controller = new LoginController();
            
            // Act
            controller.iniciarSesion(correo, contrasena, errorLabel);
            
            // Verify
            verify(errorLabel, times(1)).setText("Credenciales inválidas");
            verify(errorLabel, times(1)).setVisible(true);
            verify(navigationManager, never()).setUsuarioActual(anyString(), anyBoolean());
        }
    }

    @Test
    void testRegistrarUsuario_Exitoso() {
        // Arrange
        String nombre = "Juan Pérez";
        String correo = "juan@test.com";
        String telefono = "1234567890";
        String contrasena = "password123";

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class)) {
            LoginController controller = new LoginController();
            
            // Act
            assertDoesNotThrow(() -> controller.registrarUsuario(nombre, correo, telefono, contrasena));
            
            // Verify
            verify(mockedService.constructed().get(0), times(1))
                .registrarUsuario(nombre, correo, telefono, contrasena);
        }
    }

    @Test
    void testRegistrarUsuario_NombreVacio() {
        // Arrange
        LoginController controller = new LoginController();
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> controller.registrarUsuario("", "test@test.com", "123", "pass"));
        
        assertEquals("Todos los campos son requeridos", exception.getMessage());
    }

    @Test
    void testRegistrarUsuario_CorreoVacio() {
        // Arrange
        LoginController controller = new LoginController();
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> controller.registrarUsuario("Juan", "", "123", "pass"));
        
        assertEquals("Todos los campos son requeridos", exception.getMessage());
    }

    @Test
    void testRegistrarUsuario_TelefonoVacio() {
        // Arrange
        LoginController controller = new LoginController();
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> controller.registrarUsuario("Juan", "test@test.com", "", "pass"));
        
        assertEquals("Todos los campos son requeridos", exception.getMessage());
    }

    @Test
    void testRegistrarUsuario_ContrasenaVacia() {
        // Arrange
        LoginController controller = new LoginController();
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> controller.registrarUsuario("Juan", "test@test.com", "123", ""));
        
        assertEquals("Todos los campos son requeridos", exception.getMessage());
    }

    @Test
    void testRegistrarUsuario_CamposNull() {
        // Arrange
        LoginController controller = new LoginController();
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> controller.registrarUsuario(null, "test@test.com", "123", "pass"));
        
        assertEquals("Todos los campos son requeridos", exception.getMessage());
    }

    @Test
    void testIniciarSesion_ErrorLabelNull() {
        // Arrange
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String correo = "usuario@test.com";
        String contrasena = "password123";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario("u12345678");
        
        Usuario usuarioCompleto = new Usuario();
        usuarioCompleto.setIdUsuario("u12345678");
        usuarioCompleto.setEsAdmin(false);

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.iniciarSesion(correo, contrasena)).thenReturn(usuarioDTO);
                });
            MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById("u12345678")).thenReturn(usuarioCompleto);
                });
            MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class)) {
            
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            
            LoginController controller = new LoginController();
            
            // Act - No debe lanzar excepción aunque errorLabel sea null
            assertDoesNotThrow(() -> controller.iniciarSesion(correo, contrasena, null));
        }
    }
}

