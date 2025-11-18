package com.logistics.controller.admin;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.service.EnvioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionEstadosControllerTest {

    @Test
    void testObtenerTodosLosEnvios() {
        // Arrange
        List<EnvioDTO> enviosEsperados = new ArrayList<>();
        EnvioDTO envio1 = new EnvioDTO();
        envio1.setIdEnvio("e12345678");
        envio1.setEstado(EstadoEnvio.SOLICITADO);
        enviosEsperados.add(envio1);
        
        EnvioDTO envio2 = new EnvioDTO();
        envio2.setIdEnvio("e87654321");
        envio2.setEstado(EstadoEnvio.ASIGNADO);
        enviosEsperados.add(envio2);

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.listarEnvios()).thenReturn(enviosEsperados);
                })) {
            
            GestionEstadosController controller = new GestionEstadosController();
            
            // Act
            List<EnvioDTO> resultado = controller.obtenerTodosLosEnvios();
            
            // Assert
            assertNotNull(resultado);
            assertEquals(2, resultado.size());
            assertEquals("e12345678", resultado.get(0).getIdEnvio());
            verify(mockedService.constructed().get(0), times(1)).listarEnvios();
        }
    }

    @Test
    void testObtenerTodosLosEnvios_Vacio() {
        // Arrange
        List<EnvioDTO> enviosVacios = new ArrayList<>();

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.listarEnvios()).thenReturn(enviosVacios);
                })) {
            
            GestionEstadosController controller = new GestionEstadosController();
            
            // Act
            List<EnvioDTO> resultado = controller.obtenerTodosLosEnvios();
            
            // Assert
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    @Test
    void testObtenerEnvio_Exitoso() {
        // Arrange
        String idEnvio = "e12345678";
        EnvioDTO envioEsperado = new EnvioDTO();
        envioEsperado.setIdEnvio(idEnvio);
        envioEsperado.setEstado(EstadoEnvio.ASIGNADO);

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.obtenerEnvio(idEnvio)).thenReturn(envioEsperado);
                })) {
            
            GestionEstadosController controller = new GestionEstadosController();
            
            // Act
            EnvioDTO resultado = controller.obtenerEnvio(idEnvio);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(idEnvio, resultado.getIdEnvio());
            assertEquals(EstadoEnvio.ASIGNADO, resultado.getEstado());
            verify(mockedService.constructed().get(0), times(1)).obtenerEnvio(idEnvio);
        }
    }

    @Test
    void testObtenerEnvio_NoEncontrado() {
        // Arrange
        String idEnvio = "e99999999";

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.obtenerEnvio(idEnvio)).thenReturn(null);
                })) {
            
            GestionEstadosController controller = new GestionEstadosController();
            
            // Act
            EnvioDTO resultado = controller.obtenerEnvio(idEnvio);
            
            // Assert
            assertNull(resultado);
        }
    }

    @Test
    void testCambiarEstado() {
        // Arrange
        String idEnvio = "e12345678";
        String accion = "ASIGNAR";

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class)) {
            
            GestionEstadosController controller = new GestionEstadosController();
            
            // Act
            assertDoesNotThrow(() -> controller.cambiarEstado(idEnvio, accion));
            
            // Verify
            verify(mockedService.constructed().get(0), times(1)).cambiarEstado(idEnvio, accion);
        }
    }

    @Test
    void testReportarIncidencia() {
        // Arrange
        String idEnvio = "e12345678";
        String descripcion = "Paquete dañado";

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class)) {
            
            GestionEstadosController controller = new GestionEstadosController();
            
            // Act
            assertDoesNotThrow(() -> controller.reportarIncidencia(idEnvio, descripcion));
            
            // Verify
            verify(mockedService.constructed().get(0), times(1)).reportarIncidencia(idEnvio, descripcion);
        }
    }

    @Test
    void testPuedeCambiarEstado_SOLICITADO_ASIGNAR() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act
        boolean resultado = controller.puedeCambiarEstado(EstadoEnvio.SOLICITADO, "ASIGNAR");
        
        // Assert
        assertTrue(resultado);
    }

    @Test
    void testPuedeCambiarEstado_SOLICITADO_CANCELAR() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act
        boolean resultado = controller.puedeCambiarEstado(EstadoEnvio.SOLICITADO, "CANCELAR");
        
        // Assert
        assertTrue(resultado);
    }

    @Test
    void testPuedeCambiarEstado_SOLICITADO_ENTREGADO() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act
        boolean resultado = controller.puedeCambiarEstado(EstadoEnvio.SOLICITADO, "ENTREGADO");
        
        // Assert
        assertFalse(resultado);
    }

    @Test
    void testPuedeCambiarEstado_ASIGNADO_EN_RUTA() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act
        boolean resultado = controller.puedeCambiarEstado(EstadoEnvio.ASIGNADO, "EN_RUTA");
        
        // Assert
        assertTrue(resultado);
    }

    @Test
    void testPuedeCambiarEstado_ASIGNADO_CANCELAR() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act
        boolean resultado = controller.puedeCambiarEstado(EstadoEnvio.ASIGNADO, "CANCELAR");
        
        // Assert
        assertTrue(resultado);
    }

    @Test
    void testPuedeCambiarEstado_EN_RUTA_ENTREGADO() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act
        boolean resultado = controller.puedeCambiarEstado(EstadoEnvio.EN_RUTA, "ENTREGADO");
        
        // Assert
        assertTrue(resultado);
    }

    @Test
    void testPuedeCambiarEstado_EN_RUTA_ASIGNAR() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act
        boolean resultado = controller.puedeCambiarEstado(EstadoEnvio.EN_RUTA, "ASIGNAR");
        
        // Assert
        assertFalse(resultado);
    }

    @Test
    void testPuedeCambiarEstado_ENTREGADO_CualquierAccion() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act & Assert
        assertFalse(controller.puedeCambiarEstado(EstadoEnvio.ENTREGADO, "ASIGNAR"));
        assertFalse(controller.puedeCambiarEstado(EstadoEnvio.ENTREGADO, "EN_RUTA"));
        assertFalse(controller.puedeCambiarEstado(EstadoEnvio.ENTREGADO, "ENTREGADO"));
    }

    @Test
    void testPuedeCambiarEstado_CANCELADO_CualquierAccion() {
        // Arrange
        GestionEstadosController controller = new GestionEstadosController();
        
        // Act & Assert
        assertFalse(controller.puedeCambiarEstado(EstadoEnvio.CANCELADO, "ASIGNAR"));
        assertFalse(controller.puedeCambiarEstado(EstadoEnvio.CANCELADO, "EN_RUTA"));
        assertFalse(controller.puedeCambiarEstado(EstadoEnvio.CANCELADO, "ENTREGADO"));
    }
}

