package com.logistics.controller.admin;

import com.logistics.model.enums.EstadoEnvio;
import com.logistics.service.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardAdminControllerTest {

    @Test
    void testObtenerMetricas() {
        // Arrange
        Map<String, Object> metricasEsperadas = new HashMap<>();
        metricasEsperadas.put("totalEnvios", 100L);
        metricasEsperadas.put("totalUsuarios", 50L);
        metricasEsperadas.put("ingresosTotales", 50000.0);

        try (MockedConstruction<ReporteService> mockedService = mockConstruction(ReporteService.class,
                (mock, context) -> {
                    when(mock.obtenerMetricasGenerales()).thenReturn(metricasEsperadas);
                })) {
            
            DashboardAdminController controller = new DashboardAdminController();
            
            // Act
            Map<String, Object> resultado = controller.obtenerMetricas();
            
            // Assert
            assertNotNull(resultado);
            assertEquals(metricasEsperadas, resultado);
            assertEquals(100L, resultado.get("totalEnvios"));
            verify(mockedService.constructed().get(0), times(1)).obtenerMetricasGenerales();
        }
    }

    @Test
    void testObtenerMetricas_Vacio() {
        // Arrange
        Map<String, Object> metricasVacias = new HashMap<>();

        try (MockedConstruction<ReporteService> mockedService = mockConstruction(ReporteService.class,
                (mock, context) -> {
                    when(mock.obtenerMetricasGenerales()).thenReturn(metricasVacias);
                })) {
            
            DashboardAdminController controller = new DashboardAdminController();
            
            // Act
            Map<String, Object> resultado = controller.obtenerMetricas();
            
            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    @Test
    void testObtenerDistribucionEstados() {
        // Arrange
        Map<EstadoEnvio, Long> distribucionEsperada = new HashMap<>();
        distribucionEsperada.put(EstadoEnvio.SOLICITADO, 10L);
        distribucionEsperada.put(EstadoEnvio.ASIGNADO, 20L);
        distribucionEsperada.put(EstadoEnvio.EN_RUTA, 15L);
        distribucionEsperada.put(EstadoEnvio.ENTREGADO, 50L);
        distribucionEsperada.put(EstadoEnvio.CANCELADO, 5L);

        try (MockedConstruction<ReporteService> mockedService = mockConstruction(ReporteService.class,
                (mock, context) -> {
                    when(mock.obtenerDistribucionEstados()).thenReturn(distribucionEsperada);
                })) {
            
            DashboardAdminController controller = new DashboardAdminController();
            
            // Act
            Map<EstadoEnvio, Long> resultado = controller.obtenerDistribucionEstados();
            
            // Assert
            assertNotNull(resultado);
            assertEquals(distribucionEsperada, resultado);
            assertEquals(10L, resultado.get(EstadoEnvio.SOLICITADO));
            assertEquals(50L, resultado.get(EstadoEnvio.ENTREGADO));
            verify(mockedService.constructed().get(0), times(1)).obtenerDistribucionEstados();
        }
    }

    @Test
    void testObtenerDistribucionEstados_Vacio() {
        // Arrange
        Map<EstadoEnvio, Long> distribucionVacia = new HashMap<>();

        try (MockedConstruction<ReporteService> mockedService = mockConstruction(ReporteService.class,
                (mock, context) -> {
                    when(mock.obtenerDistribucionEstados()).thenReturn(distribucionVacia);
                })) {
            
            DashboardAdminController controller = new DashboardAdminController();
            
            // Act
            Map<EstadoEnvio, Long> resultado = controller.obtenerDistribucionEstados();
            
            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    @Test
    void testObtenerServiciosAdicionales() {
        // Arrange
        Map<String, Long> serviciosEsperados = new HashMap<>();
        serviciosEsperados.put("SEGURO", 30L);
        serviciosEsperados.put("EMPAQUE_ESPECIAL", 20L);
        serviciosEsperados.put("ENTREGA_EXPRES", 15L);

        try (MockedConstruction<ReporteService> mockedService = mockConstruction(ReporteService.class,
                (mock, context) -> {
                    when(mock.obtenerServiciosAdicionalesMasUsados()).thenReturn(serviciosEsperados);
                })) {
            
            DashboardAdminController controller = new DashboardAdminController();
            
            // Act
            Map<String, Long> resultado = controller.obtenerServiciosAdicionales();
            
            // Assert
            assertNotNull(resultado);
            assertEquals(serviciosEsperados, resultado);
            assertEquals(30L, resultado.get("SEGURO"));
            assertEquals(20L, resultado.get("EMPAQUE_ESPECIAL"));
            verify(mockedService.constructed().get(0), times(1)).obtenerServiciosAdicionalesMasUsados();
        }
    }

    @Test
    void testObtenerServiciosAdicionales_Vacio() {
        // Arrange
        Map<String, Long> serviciosVacios = new HashMap<>();

        try (MockedConstruction<ReporteService> mockedService = mockConstruction(ReporteService.class,
                (mock, context) -> {
                    when(mock.obtenerServiciosAdicionalesMasUsados()).thenReturn(serviciosVacios);
                })) {
            
            DashboardAdminController controller = new DashboardAdminController();
            
            // Act
            Map<String, Long> resultado = controller.obtenerServiciosAdicionales();
            
            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    @Test
    void testConstructor() {
        // Arrange & Act
        DashboardAdminController controller = new DashboardAdminController();
        
        // Assert
        assertNotNull(controller);
    }
}

