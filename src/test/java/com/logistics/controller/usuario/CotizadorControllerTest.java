package com.logistics.controller.usuario;

import com.logistics.model.dto.TarifaDTO;
import com.logistics.service.TarifaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotizadorControllerTest {

    @Test
    void testCalcularTarifa_ConServiciosAdicionales() {
        // Arrange
        double latOrigen = 19.4326;
        double lonOrigen = -99.1332;
        double latDestino = 20.6597;
        double lonDestino = -103.3496;
        double peso = 5.5;
        List<String> serviciosAdicionales = new ArrayList<>();
        serviciosAdicionales.add("SEGURO");
        serviciosAdicionales.add("EMPAQUE_ESPECIAL");

        TarifaDTO tarifaEsperada = new TarifaDTO();
        tarifaEsperada.setCostoBase(100.0);
        tarifaEsperada.setCostoTotal(150.0);
        Map<String, Double> desglose = new HashMap<>();
        desglose.put("SEGURO", 30.0);
        desglose.put("EMPAQUE_ESPECIAL", 20.0);
        tarifaEsperada.setDesgloseServicios(desglose);

        try (MockedConstruction<TarifaService> mockedService = mockConstruction(TarifaService.class,
                (mock, context) -> {
                    when(mock.calcularTarifa(any(), anyList())).thenReturn(tarifaEsperada);
                })) {
            
            CotizadorController controller = new CotizadorController();
            
            // Act
            TarifaDTO resultado = controller.calcularTarifa(
                latOrigen, lonOrigen, latDestino, lonDestino, peso, serviciosAdicionales);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(100.0, resultado.getCostoBase());
            assertEquals(150.0, resultado.getCostoTotal());
            assertNotNull(resultado.getDesgloseServicios());
            assertEquals(2, resultado.getDesgloseServicios().size());
            verify(mockedService.constructed().get(0), times(1))
                .calcularTarifa(any(), eq(serviciosAdicionales));
        }
    }

    @Test
    void testCalcularTarifa_SinServiciosAdicionales() {
        // Arrange
        double latOrigen = 19.4326;
        double lonOrigen = -99.1332;
        double latDestino = 20.6597;
        double lonDestino = -103.3496;
        double peso = 3.0;
        List<String> serviciosAdicionales = new ArrayList<>();

        TarifaDTO tarifaEsperada = new TarifaDTO();
        tarifaEsperada.setCostoBase(80.0);
        tarifaEsperada.setCostoTotal(80.0);
        tarifaEsperada.setDesgloseServicios(new HashMap<>());

        try (MockedConstruction<TarifaService> mockedService = mockConstruction(TarifaService.class,
                (mock, context) -> {
                    when(mock.calcularTarifa(any(), anyList())).thenReturn(tarifaEsperada);
                })) {
            
            CotizadorController controller = new CotizadorController();
            
            // Act
            TarifaDTO resultado = controller.calcularTarifa(
                latOrigen, lonOrigen, latDestino, lonDestino, peso, serviciosAdicionales);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(80.0, resultado.getCostoBase());
            assertEquals(80.0, resultado.getCostoTotal());
            assertTrue(resultado.getDesgloseServicios().isEmpty());
        }
    }

    @Test
    void testCalcularTarifa_PesoCero() {
        // Arrange
        double latOrigen = 19.4326;
        double lonOrigen = -99.1332;
        double latDestino = 20.6597;
        double lonDestino = -103.3496;
        double peso = 0.0;
        List<String> serviciosAdicionales = new ArrayList<>();

        TarifaDTO tarifaEsperada = new TarifaDTO();
        tarifaEsperada.setCostoBase(0.0);
        tarifaEsperada.setCostoTotal(0.0);

        try (MockedConstruction<TarifaService> mockedService = mockConstruction(TarifaService.class,
                (mock, context) -> {
                    when(mock.calcularTarifa(any(), anyList())).thenReturn(tarifaEsperada);
                })) {
            
            CotizadorController controller = new CotizadorController();
            
            // Act
            TarifaDTO resultado = controller.calcularTarifa(
                latOrigen, lonOrigen, latDestino, lonDestino, peso, serviciosAdicionales);
            
            // Assert
            assertNotNull(resultado);
            assertEquals(0.0, resultado.getCostoBase());
        }
    }

    @Test
    void testCalcularTarifa_PesoNegativo() {
        // Arrange
        double latOrigen = 19.4326;
        double lonOrigen = -99.1332;
        double latDestino = 20.6597;
        double lonDestino = -103.3496;
        double peso = -5.0;
        List<String> serviciosAdicionales = new ArrayList<>();

        TarifaDTO tarifaEsperada = new TarifaDTO();
        tarifaEsperada.setCostoBase(0.0);
        tarifaEsperada.setCostoTotal(0.0);

        try (MockedConstruction<TarifaService> mockedService = mockConstruction(TarifaService.class,
                (mock, context) -> {
                    when(mock.calcularTarifa(any(), anyList())).thenReturn(tarifaEsperada);
                })) {
            
            CotizadorController controller = new CotizadorController();
            
            // Act
            TarifaDTO resultado = controller.calcularTarifa(
                latOrigen, lonOrigen, latDestino, lonDestino, peso, serviciosAdicionales);
            
            // Assert
            assertNotNull(resultado);
            verify(mockedService.constructed().get(0), times(1))
                .calcularTarifa(any(), anyList());
        }
    }

    @Test
    void testCalcularTarifa_CoordenadasIguales() {
        // Arrange
        double lat = 19.4326;
        double lon = -99.1332;
        double peso = 2.0;
        List<String> serviciosAdicionales = new ArrayList<>();

        TarifaDTO tarifaEsperada = new TarifaDTO();
        tarifaEsperada.setCostoBase(0.0);
        tarifaEsperada.setCostoTotal(0.0);

        try (MockedConstruction<TarifaService> mockedService = mockConstruction(TarifaService.class,
                (mock, context) -> {
                    when(mock.calcularTarifa(any(), anyList())).thenReturn(tarifaEsperada);
                })) {
            
            CotizadorController controller = new CotizadorController();
            
            // Act
            TarifaDTO resultado = controller.calcularTarifa(
                lat, lon, lat, lon, peso, serviciosAdicionales);
            
            // Assert
            assertNotNull(resultado);
            verify(mockedService.constructed().get(0), times(1))
                .calcularTarifa(any(), anyList());
        }
    }

    @Test
    void testConstructor() {
        // Arrange & Act
        CotizadorController controller = new CotizadorController();
        
        // Assert
        assertNotNull(controller);
    }
}

