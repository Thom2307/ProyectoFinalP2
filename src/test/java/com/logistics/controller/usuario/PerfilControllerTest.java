package com.logistics.controller.usuario;

import com.logistics.model.dto.UsuarioDTO;
import com.logistics.service.UsuarioService;
import com.logistics.util.NavigationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilControllerTest {

    @Test
    void testObtenerUsuarioActual_Exitoso() {
        // Arrange
        String usuarioId = "u12345678";
        UsuarioDTO usuarioEsperado = new UsuarioDTO();
        usuarioEsperado.setIdUsuario(usuarioId);
        usuarioEsperado.setNombre("Juan Pérez");
        usuarioEsperado.setCorreo("juan@test.com");
        usuarioEsperado.setTelefono("1234567890");

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.obtenerUsuario(usuarioId)).thenReturn(usuarioEsperado);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            PerfilController controller = new PerfilController();
            
            // Act
            UsuarioDTO resultado = controller.obtenerUsuarioActual();
            
            // Assert
            assertNotNull(resultado);
            assertEquals(usuarioId, resultado.getIdUsuario());
            assertEquals("Juan Pérez", resultado.getNombre());
            assertEquals("juan@test.com", resultado.getCorreo());
            verify(mockedService.constructed().get(0), times(1)).obtenerUsuario(usuarioId);
        }
    }

    @Test
    void testObtenerUsuarioActual_UsuarioIdNull() {
        // Arrange
        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class)) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(null);
            
            PerfilController controller = new PerfilController();
            
            // Act
            UsuarioDTO resultado = controller.obtenerUsuarioActual();
            
            // Assert
            assertNull(resultado);
            verify(mockedService.constructed().get(0), never()).obtenerUsuario(anyString());
        }
    }

    @Test
    void testActualizarPerfil_Exitoso() {
        // Arrange
        String usuarioId = "u12345678";
        String nuevoNombre = "Juan Carlos Pérez";
        String nuevoTelefono = "9876543210";
        
        UsuarioDTO usuarioActualizado = new UsuarioDTO();
        usuarioActualizado.setIdUsuario(usuarioId);
        usuarioActualizado.setNombre(nuevoNombre);
        usuarioActualizado.setTelefono(nuevoTelefono);

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.actualizarPerfil(usuarioId, nuevoNombre, nuevoTelefono))
                        .thenReturn(usuarioActualizado);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            PerfilController controller = new PerfilController();
            
            // Act
            UsuarioDTO resultado = controller.actualizarPerfil(nuevoNombre, nuevoTelefono);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(usuarioId, resultado.getIdUsuario());
            assertEquals(nuevoNombre, resultado.getNombre());
            assertEquals(nuevoTelefono, resultado.getTelefono());
            verify(mockedService.constructed().get(0), times(1))
                .actualizarPerfil(usuarioId, nuevoNombre, nuevoTelefono);
        }
    }

    @Test
    void testActualizarPerfil_UsuarioIdNull() {
        // Arrange
        String nuevoNombre = "Juan Carlos Pérez";
        String nuevoTelefono = "9876543210";

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class)) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(null);
            
            PerfilController controller = new PerfilController();
            
            // Act & Assert
            IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> controller.actualizarPerfil(nuevoNombre, nuevoTelefono));
            
            assertEquals("No hay usuario autenticado", exception.getMessage());
            verify(mockedService.constructed().get(0), never())
                .actualizarPerfil(anyString(), anyString(), anyString());
        }
    }

    @Test
    void testActualizarPerfil_UsuarioNoEncontrado() {
        // Arrange
        String usuarioId = "u99999999";
        String nuevoNombre = "Juan Carlos Pérez";
        String nuevoTelefono = "9876543210";

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.actualizarPerfil(usuarioId, nuevoNombre, nuevoTelefono))
                        .thenThrow(new IllegalArgumentException("Usuario no encontrado"));
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            PerfilController controller = new PerfilController();
            
            // Act & Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> controller.actualizarPerfil(nuevoNombre, nuevoTelefono));
            
            assertEquals("Usuario no encontrado", exception.getMessage());
        }
    }

    @Test
    void testActualizarPerfil_SoloNombre() {
        // Arrange
        String usuarioId = "u12345678";
        String nuevoNombre = "Juan Carlos Pérez";
        String telefono = "1234567890";
        
        UsuarioDTO usuarioActualizado = new UsuarioDTO();
        usuarioActualizado.setIdUsuario(usuarioId);
        usuarioActualizado.setNombre(nuevoNombre);
        usuarioActualizado.setTelefono(telefono);

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.actualizarPerfil(usuarioId, nuevoNombre, telefono))
                        .thenReturn(usuarioActualizado);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            PerfilController controller = new PerfilController();
            
            // Act
            UsuarioDTO resultado = controller.actualizarPerfil(nuevoNombre, telefono);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(nuevoNombre, resultado.getNombre());
        }
    }

    @Test
    void testActualizarPerfil_SoloTelefono() {
        // Arrange
        String usuarioId = "u12345678";
        String nombre = "Juan Pérez";
        String nuevoTelefono = "9876543210";
        
        UsuarioDTO usuarioActualizado = new UsuarioDTO();
        usuarioActualizado.setIdUsuario(usuarioId);
        usuarioActualizado.setNombre(nombre);
        usuarioActualizado.setTelefono(nuevoTelefono);

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<UsuarioService> mockedService = mockConstruction(UsuarioService.class,
                (mock, context) -> {
                    when(mock.actualizarPerfil(usuarioId, nombre, nuevoTelefono))
                        .thenReturn(usuarioActualizado);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            PerfilController controller = new PerfilController();
            
            // Act
            UsuarioDTO resultado = controller.actualizarPerfil(nombre, nuevoTelefono);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(nuevoTelefono, resultado.getTelefono());
        }
    }

    @Test
    void testConstructor() {
        // Arrange & Act
        PerfilController controller = new PerfilController();
        
        // Assert
        assertNotNull(controller);
    }
}

