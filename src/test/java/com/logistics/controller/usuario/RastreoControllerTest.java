package com.logistics.controller.usuario;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.service.EnvioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RastreoControllerTest {

    @Test
    void testBuscarEnvio_Exitoso() {
        // Arrange
        String idEnvio = "e12345678";
        EnvioDTO envioEsperado = new EnvioDTO();
        envioEsperado.setIdEnvio(idEnvio);
        envioEsperado.setEstado(EstadoEnvio.EN_RUTA);
        envioEsperado.setCosto(150.0);
        envioEsperado.setFechaCreacion(LocalDateTime.now());

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.obtenerEnvio(idEnvio)).thenReturn(envioEsperado);
                })) {
            
            RastreoController controller = new RastreoController();
            
            // Act
            EnvioDTO resultado = controller.buscarEnvio(idEnvio);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(idEnvio, resultado.getIdEnvio());
            assertEquals(EstadoEnvio.EN_RUTA, resultado.getEstado());
            assertEquals(150.0, resultado.getCosto());
            verify(mockedService.constructed().get(0), times(1)).obtenerEnvio(idEnvio);
        }
    }

    @Test
    void testBuscarEnvio_NoEncontrado() {
        // Arrange
        String idEnvio = "e99999999";

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.obtenerEnvio(idEnvio)).thenReturn(null);
                })) {
            
            RastreoController controller = new RastreoController();
            
            // Act
            EnvioDTO resultado = controller.buscarEnvio(idEnvio);
            
            // Assert
            assertNull(resultado);
            verify(mockedService.constructed().get(0), times(1)).obtenerEnvio(idEnvio);
        }
    }

    @Test
    void testBuscarEnvio_DiferentesEstados() {
        // Arrange
        String idEnvio1 = "e1";
        String idEnvio2 = "e2";
        String idEnvio3 = "e3";
        
        EnvioDTO envio1 = new EnvioDTO();
        envio1.setIdEnvio(idEnvio1);
        envio1.setEstado(EstadoEnvio.SOLICITADO);
        
        EnvioDTO envio2 = new EnvioDTO();
        envio2.setIdEnvio(idEnvio2);
        envio2.setEstado(EstadoEnvio.ASIGNADO);
        
        EnvioDTO envio3 = new EnvioDTO();
        envio3.setIdEnvio(idEnvio3);
        envio3.setEstado(EstadoEnvio.ENTREGADO);

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.obtenerEnvio(idEnvio1)).thenReturn(envio1);
                    when(mock.obtenerEnvio(idEnvio2)).thenReturn(envio2);
                    when(mock.obtenerEnvio(idEnvio3)).thenReturn(envio3);
                })) {
            
            RastreoController controller = new RastreoController();
            
            // Act
            EnvioDTO resultado1 = controller.buscarEnvio(idEnvio1);
            EnvioDTO resultado2 = controller.buscarEnvio(idEnvio2);
            EnvioDTO resultado3 = controller.buscarEnvio(idEnvio3);
            
            // Assert
            assertEquals(EstadoEnvio.SOLICITADO, resultado1.getEstado());
            assertEquals(EstadoEnvio.ASIGNADO, resultado2.getEstado());
            assertEquals(EstadoEnvio.ENTREGADO, resultado3.getEstado());
        }
    }

    @Test
    void testBuscarEnvio_ConDatosCompletos() {
        // Arrange
        String idEnvio = "e12345678";
        EnvioDTO envioEsperado = new EnvioDTO();
        envioEsperado.setIdEnvio(idEnvio);
        envioEsperado.setEstado(EstadoEnvio.EN_RUTA);
        envioEsperado.setCosto(250.75);
        envioEsperado.setPeso(5.5);
        envioEsperado.setFechaCreacion(LocalDateTime.of(2024, 1, 15, 10, 30));
        envioEsperado.setIdUsuario("u12345678");
        envioEsperado.setNombreUsuario("Juan Pérez");
        envioEsperado.setIdRepartidor("r12345678");
        envioEsperado.setNombreRepartidor("Carlos Repartidor");

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.obtenerEnvio(idEnvio)).thenReturn(envioEsperado);
                })) {
            
            RastreoController controller = new RastreoController();
            
            // Act
            EnvioDTO resultado = controller.buscarEnvio(idEnvio);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(idEnvio, resultado.getIdEnvio());
            assertEquals(250.75, resultado.getCosto());
            assertEquals(5.5, resultado.getPeso());
            assertEquals("u12345678", resultado.getIdUsuario());
            assertEquals("Juan Pérez", resultado.getNombreUsuario());
            assertEquals("r12345678", resultado.getIdRepartidor());
            assertEquals("Carlos Repartidor", resultado.getNombreRepartidor());
        }
    }

    @Test
    void testBuscarEnvio_IdVacio() {
        // Arrange
        String idEnvio = "";

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.obtenerEnvio(idEnvio)).thenReturn(null);
                })) {
            
            RastreoController controller = new RastreoController();
            
            // Act
            EnvioDTO resultado = controller.buscarEnvio(idEnvio);
            
            // Assert
            assertNull(resultado);
        }
    }

    @Test
    void testBuscarEnvio_IdNull() {
        // Arrange
        String idEnvio = null;

        try (MockedConstruction<EnvioService> mockedService = mockConstruction(EnvioService.class,
                (mock, context) -> {
                    when(mock.obtenerEnvio(idEnvio)).thenReturn(null);
                })) {
            
            RastreoController controller = new RastreoController();
            
            // Act
            EnvioDTO resultado = controller.buscarEnvio(idEnvio);
            
            // Assert
            assertNull(resultado);
        }
    }

    @Test
    void testConstructor() {
        // Arrange & Act
        RastreoController controller = new RastreoController();
        
        // Assert
        assertNotNull(controller);
    }
}

