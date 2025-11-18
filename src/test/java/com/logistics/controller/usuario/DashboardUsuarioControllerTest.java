package com.logistics.controller.usuario;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.service.EnvioService;
import com.logistics.util.NavigationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardUsuarioControllerTest {

    @Test
    void testObtenerEnviosActivos() {
        // Arrange
        NavigationManager navigationManager = mock(NavigationManager.class);
        
        String usuarioId = "u12345678";
        List<EnvioDTO> envios = new ArrayList<>();
        
        EnvioDTO envio1 = new EnvioDTO();
        envio1.setIdEnvio("e1");
        envio1.setEstado(EstadoEnvio.SOLICITADO);
        envios.add(envio1);
        
        EnvioDTO envio2 = new EnvioDTO();
        envio2.setIdEnvio("e2");
        envio2.setEstado(EstadoEnvio.ASIGNADO);
        envios.add(envio2);
        
        EnvioDTO envio3 = new EnvioDTO();
        envio3.setIdEnvio("e3");
        envio3.setEstado(EstadoEnvio.ENTREGADO);
        envios.add(envio3);

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.filtrarPorUsuarioYFecha(usuarioId, null, null)).thenReturn(envios);
                })) {
            
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            DashboardUsuarioController controller = new DashboardUsuarioController();
            
            // Act
            int resultado = controller.obtenerEnviosActivos();
            
            // Assert
            assertEquals(2, resultado); // Solo SOLICITADO y ASIGNADO son activos
        }
    }

    @Test
    void testObtenerEnviosActivos_SinEnvios() {
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
            
            DashboardUsuarioController controller = new DashboardUsuarioController();
            
            // Act
            int resultado = controller.obtenerEnviosActivos();
            
            // Assert
            assertEquals(0, resultado);
        }
    }

    @Test
    void testObtenerEnviosActivos_TodosEntregados() {
        // Arrange
        String usuarioId = "u12345678";
        List<EnvioDTO> envios = new ArrayList<>();
        
        EnvioDTO envio1 = new EnvioDTO();
        envio1.setIdEnvio("e1");
        envio1.setEstado(EstadoEnvio.ENTREGADO);
        envios.add(envio1);
        
        EnvioDTO envio2 = new EnvioDTO();
        envio2.setIdEnvio("e2");
        envio2.setEstado(EstadoEnvio.CANCELADO);
        envios.add(envio2);

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.filtrarPorUsuarioYFecha(usuarioId, null, null)).thenReturn(envios);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            DashboardUsuarioController controller = new DashboardUsuarioController();
            
            // Act
            int resultado = controller.obtenerEnviosActivos();
            
            // Assert
            assertEquals(0, resultado);
        }
    }

    @Test
    void testObtenerTotalEnvios() {
        // Arrange
        String usuarioId = "u12345678";
        List<EnvioDTO> envios = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            EnvioDTO envio = new EnvioDTO();
            envio.setIdEnvio("e" + i);
            envio.setEstado(EstadoEnvio.SOLICITADO);
            envios.add(envio);
        }

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.filtrarPorUsuarioYFecha(usuarioId, null, null)).thenReturn(envios);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            DashboardUsuarioController controller = new DashboardUsuarioController();
            
            // Act
            int resultado = controller.obtenerTotalEnvios();
            
            // Assert
            assertEquals(5, resultado);
        }
    }

    @Test
    void testObtenerTotalEnvios_Vacio() {
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
            
            DashboardUsuarioController controller = new DashboardUsuarioController();
            
            // Act
            int resultado = controller.obtenerTotalEnvios();
            
            // Assert
            assertEquals(0, resultado);
        }
    }

    @Test
    void testObtenerEnTransito() {
        // Arrange
        String usuarioId = "u12345678";
        List<EnvioDTO> envios = new ArrayList<>();
        
        EnvioDTO envio1 = new EnvioDTO();
        envio1.setIdEnvio("e1");
        envio1.setEstado(EstadoEnvio.ASIGNADO);
        envios.add(envio1);
        
        EnvioDTO envio2 = new EnvioDTO();
        envio2.setIdEnvio("e2");
        envio2.setEstado(EstadoEnvio.EN_RUTA);
        envios.add(envio2);
        
        EnvioDTO envio3 = new EnvioDTO();
        envio3.setIdEnvio("e3");
        envio3.setEstado(EstadoEnvio.SOLICITADO);
        envios.add(envio3);

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.filtrarPorUsuarioYFecha(usuarioId, null, null)).thenReturn(envios);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            DashboardUsuarioController controller = new DashboardUsuarioController();
            
            // Act
            int resultado = controller.obtenerEnTransito();
            
            // Assert
            assertEquals(2, resultado); // Solo ASIGNADO y EN_RUTA
        }
    }

    @Test
    void testObtenerEnTransito_Vacio() {
        // Arrange
        String usuarioId = "u12345678";
        List<EnvioDTO> envios = new ArrayList<>();
        
        EnvioDTO envio1 = new EnvioDTO();
        envio1.setIdEnvio("e1");
        envio1.setEstado(EstadoEnvio.SOLICITADO);
        envios.add(envio1);

        try (MockedStatic<NavigationManager> mockedNav = mockStatic(NavigationManager.class);
             MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.filtrarPorUsuarioYFecha(usuarioId, null, null)).thenReturn(envios);
                })) {
            
            NavigationManager navigationManager = mock(NavigationManager.class);
            mockedNav.when(NavigationManager::getInstance).thenReturn(navigationManager);
            when(navigationManager.getUsuarioActualId()).thenReturn(usuarioId);
            
            DashboardUsuarioController controller = new DashboardUsuarioController();
            
            // Act
            int resultado = controller.obtenerEnTransito();
            
            // Assert
            assertEquals(0, resultado);
        }
    }
}

