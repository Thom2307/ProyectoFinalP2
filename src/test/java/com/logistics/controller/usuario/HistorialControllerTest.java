package com.logistics.controller.usuario;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.service.EnvioService;
import com.logistics.util.NavigationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistorialControllerTest {

    @Test
    void testObtenerEnviosUsuario_Exitoso() {
        // Arrange
        String usuarioId = "u12345678";
        List<EnvioDTO> enviosEsperados = new ArrayList<>();
        
        EnvioDTO envio1 = new EnvioDTO();
        envio1.setIdEnvio("e1");
        envio1.setCosto(150.0);
        envio1.setFechaCreacion(LocalDateTime.now());
        enviosEsperados.add(envio1);
        
        EnvioDTO envio2 = new EnvioDTO();
        envio2.setIdEnvio("e2");
        envio2.setCosto(200.0);
        envio2.setFechaCreacion(LocalDateTime.now().minusDays(1));
        enviosEsperados.add(envio2);

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.filtrarPorUsuarioYFecha(usuarioId, null, null)).thenReturn(enviosEsperados);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            HistorialController controller = new HistorialController();
            
            // Act
            List<EnvioDTO> resultado = controller.obtenerEnviosUsuario();
            
            // Assert
            assertNotNull(resultado);
            assertEquals(2, resultado.size());
            assertEquals("e1", resultado.get(0).getIdEnvio());
            verify(mockedService.constructed().get(0), times(1))
                .filtrarPorUsuarioYFecha(usuarioId, null, null);
        }
    }

    @Test
    void testObtenerEnviosUsuario_UsuarioIdNull() {
        // Arrange
        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class)) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(null);
            
            HistorialController controller = new HistorialController();
            
            // Act
            List<EnvioDTO> resultado = controller.obtenerEnviosUsuario();
            
            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(mockedService.constructed().get(0), never())
                .filtrarPorUsuarioYFecha(anyString(), any(), any());
        }
    }

    @Test
    void testObtenerEnviosUsuario_Vacio() {
        // Arrange
        String usuarioId = "u12345678";
        List<EnvioDTO> enviosVacios = new ArrayList<>();

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.filtrarPorUsuarioYFecha(usuarioId, null, null)).thenReturn(enviosVacios);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            HistorialController controller = new HistorialController();
            
            // Act
            List<EnvioDTO> resultado = controller.obtenerEnviosUsuario();
            
            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    @Test
    void testFormatearFecha_Exitoso() {
        // Arrange
        HistorialController controller = new HistorialController();
        LocalDateTime fecha = LocalDateTime.of(2024, 1, 15, 14, 30);
        
        // Act
        String resultado = controller.formatearFecha(fecha);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("15/01/2024 14:30", resultado);
    }

    @Test
    void testFormatearFecha_Null() {
        // Arrange
        HistorialController controller = new HistorialController();
        
        // Act
        String resultado = controller.formatearFecha(null);
        
        // Assert
        assertEquals("N/A", resultado);
    }

    @Test
    void testFormatearFecha_DiferentesFormatos() {
        // Arrange
        HistorialController controller = new HistorialController();
        
        // Act & Assert
        LocalDateTime fecha1 = LocalDateTime.of(2024, 12, 31, 23, 59);
        assertEquals("31/12/2024 23:59", controller.formatearFecha(fecha1));
        
        LocalDateTime fecha2 = LocalDateTime.of(2024, 1, 1, 0, 0);
        assertEquals("01/01/2024 00:00", controller.formatearFecha(fecha2));
        
        LocalDateTime fecha3 = LocalDateTime.of(2024, 6, 15, 10, 5);
        assertEquals("15/06/2024 10:05", controller.formatearFecha(fecha3));
    }

    @Test
    void testFormatearCosto_Exitoso() {
        // Arrange
        HistorialController controller = new HistorialController();
        double costo = 1234.56;
        
        // Act
        String resultado = controller.formatearCosto(costo);
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.startsWith("$"));
        assertTrue(resultado.contains("1,234.56"));
    }

    @Test
    void testFormatearCosto_Cero() {
        // Arrange
        HistorialController controller = new HistorialController();
        
        // Act
        String resultado = controller.formatearCosto(0.0);
        
        // Assert
        assertEquals("$0.00", resultado);
    }

    @Test
    void testFormatearCosto_Negativo() {
        // Arrange
        HistorialController controller = new HistorialController();
        
        // Act
        String resultado = controller.formatearCosto(-100.50);
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.contains("-"));
    }

    @Test
    void testFormatearCosto_Grandes() {
        // Arrange
        HistorialController controller = new HistorialController();
        
        // Act
        String resultado1 = controller.formatearCosto(1000.0);
        String resultado2 = controller.formatearCosto(10000.0);
        String resultado3 = controller.formatearCosto(100000.0);
        
        // Assert
        assertTrue(resultado1.contains("1,000"));
        assertTrue(resultado2.contains("10,000"));
        assertTrue(resultado3.contains("100,000"));
    }

    @Test
    void testFormatearCosto_Decimales() {
        // Arrange
        HistorialController controller = new HistorialController();
        
        // Act
        String resultado1 = controller.formatearCosto(99.99);
        String resultado2 = controller.formatearCosto(0.01);
        String resultado3 = controller.formatearCosto(123.456);
        
        // Assert
        assertNotNull(resultado1);
        assertNotNull(resultado2);
        assertNotNull(resultado3);
    }
}

