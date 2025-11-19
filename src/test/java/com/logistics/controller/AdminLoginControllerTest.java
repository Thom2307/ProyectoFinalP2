package com.logistics.controller;

import com.logistics.model.dto.UsuarioDTO;
import com.logistics.model.entities.Usuario;
import com.logistics.repository.UsuarioRepository;
import com.logistics.service.UsuarioService;
import com.logistics.util.NavigationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminLoginControllerTest {

    @Test
    void testIniciarSesion_Exitoso() {
        // Arrange
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String usuario = "admin";
        String contrasena = "admin123";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario("u12345678");
        
        Usuario usuarioCompleto = new Usuario();
        usuarioCompleto.setIdUsuario("u12345678");
        usuarioCompleto.setEsAdmin(true);

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.iniciarSesion("admin@logistics.com", contrasena)).thenReturn(usuarioDTO);
                });
            MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById("u12345678")).thenReturn(usuarioCompleto);
                });
            MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class)) {
            
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            
            AdminLoginController controller = new AdminLoginController();
            
            // Act
            assertDoesNotThrow(() -> controller.iniciarSesion(usuario, contrasena));
            
            // Verify
            mockedNav.verify(() -> NavigationManager.getInstance(), times(1));
        }
    }

    @Test
    void testIniciarSesion_ConCorreoCompleto() {
        // Arrange
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String usuario = "admin@logistics.com";
        String contrasena = "admin123";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario("u12345678");
        
        Usuario usuarioCompleto = new Usuario();
        usuarioCompleto.setIdUsuario("u12345678");
        usuarioCompleto.setEsAdmin(true);

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.iniciarSesion(usuario, contrasena)).thenReturn(usuarioDTO);
                });
            MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById("u12345678")).thenReturn(usuarioCompleto);
                });
            MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class)) {
            
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            
            AdminLoginController controller = new AdminLoginController();
            
            // Act
            assertDoesNotThrow(() -> controller.iniciarSesion(usuario, contrasena));
        }
    }

    @Test
    void testIniciarSesion_UsuarioVacio() {
        // Arrange
        AdminLoginController controller = new AdminLoginController();
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> controller.iniciarSesion("", "password"));
        
        assertEquals("Por favor complete todos los campos", exception.getMessage());
    }

    @Test
    void testIniciarSesion_ContrasenaVacia() {
        // Arrange
        AdminLoginController controller = new AdminLoginController();
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> controller.iniciarSesion("admin", ""));
        
        assertEquals("Por favor complete todos los campos", exception.getMessage());
    }

    @Test
    void testIniciarSesion_UsuarioNull() {
        // Arrange
        AdminLoginController controller = new AdminLoginController();
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> controller.iniciarSesion(null, "password"));
        
        assertEquals("Por favor complete todos los campos", exception.getMessage());
    }

    @Test
    void testIniciarSesion_NoEsAdmin() {
        // Arrange
        String usuario = "admin";
        String contrasena = "admin123";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario("u12345678");
        
        Usuario usuarioCompleto = new Usuario();
        usuarioCompleto.setIdUsuario("u12345678");
        usuarioCompleto.setEsAdmin(false);

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.iniciarSesion("admin@logistics.com", contrasena)).thenReturn(usuarioDTO);
                });
            MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById("u12345678")).thenReturn(usuarioCompleto);
                })) {
            
            AdminLoginController controller = new AdminLoginController();
            
            // Act & Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> controller.iniciarSesion(usuario, contrasena));
            
            assertEquals("Acceso denegado. Solo administradores pueden ingresar aquí.", exception.getMessage());
        }
    }

    @Test
    void testIniciarSesion_UsuarioNoEncontrado() {
        // Arrange
        String usuario = "admin";
        String contrasena = "admin123";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario("u12345678");

        try (MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.iniciarSesion("admin@logistics.com", contrasena)).thenReturn(usuarioDTO);
                });
            MockedConstruction<UsuarioRepository> mockedRepo = mockConstruction(UsuarioRepository.class,
                (mock, context) -> {
                    when(mock.findById("u12345678")).thenReturn(null);
                })) {
            
            AdminLoginController controller = new AdminLoginController();
            
            // Act & Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> controller.iniciarSesion(usuario, contrasena));
            
            assertEquals("Acceso denegado. Solo administradores pueden ingresar aquí.", exception.getMessage());
        }
    }
}

