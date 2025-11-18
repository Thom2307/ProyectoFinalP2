package com.logistics.service;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.repository.EnvioRepository;
import com.logistics.repository.PagoRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteService {
    private EnvioRepository envioRepository = new EnvioRepository();
    private PagoRepository pagoRepository = new PagoRepository();
    private EnvioService envioService = new EnvioService();

    public Map<String, Object> obtenerMetricasGenerales() {
        Map<String, Object> metricas = new HashMap<>();
        List<com.logistics.model.entities.Envio> envios = envioRepository.findAll();
        
        metricas.put("totalEnvios", envios.size());
        metricas.put("enviosSolicitados", contarPorEstado(envios, EstadoEnvio.SOLICITADO));
        metricas.put("enviosAsignados", contarPorEstado(envios, EstadoEnvio.ASIGNADO));
        metricas.put("enviosEnRuta", contarPorEstado(envios, EstadoEnvio.EN_RUTA));
        metricas.put("enviosEntregados", contarPorEstado(envios, EstadoEnvio.ENTREGADO));
        
        double ingresos = pagoRepository.findAll().stream()
                .filter(p -> p.getResultado() != null && p.getResultado().approved)
                .mapToDouble(p -> p.getMonto())
                .sum();
        metricas.put("ingresosTotales", ingresos);
        
        long entregados = contarPorEstado(envios, EstadoEnvio.ENTREGADO);
        metricas.put("tiempoPromedio", entregados > 0 ? 2.5 : 0.0); // Mock
        
        return metricas;
    }

    public Map<EstadoEnvio, Long> obtenerDistribucionEstados() {
        return envioRepository.findAll().stream()
                .map(e -> envioService.obtenerEnvio(e.getIdEnvio()))
                .filter(e -> e != null)
                .collect(Collectors.groupingBy(EnvioDTO::getEstado, Collectors.counting()));
    }

    public Map<String, Long> obtenerEnviosPorDia(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return envioRepository.findAll().stream()
                .filter(e -> fechaInicio == null || e.getFechaCreacion().isAfter(fechaInicio))
                .filter(e -> fechaFin == null || e.getFechaCreacion().isBefore(fechaFin))
                .collect(Collectors.groupingBy(
                    e -> e.getFechaCreacion().toLocalDate().toString(),
                    Collectors.counting()
                ));
    }

    public Map<String, Long> obtenerServiciosAdicionalesMasUsados() {
        Map<String, Long> servicios = new HashMap<>();
        envioRepository.findAll().forEach(envio -> {
            envio.getAdicionales().forEach(servicio -> {
                servicios.put(servicio, servicios.getOrDefault(servicio, 0L) + 1);
            });
        });
        return servicios;
    }

    public Map<String, Long> obtenerIncidenciasPorZona() {
        // Mock - en implementación real se consultaría una tabla de incidencias
        Map<String, Long> incidencias = new HashMap<>();
        incidencias.put("Norte", 5L);
        incidencias.put("Sur", 3L);
        incidencias.put("Centro", 2L);
        incidencias.put("Oriente", 4L);
        return incidencias;
    }

    public void generarReporteCSV(String tipoReporte, LocalDateTime fechaInicio, LocalDateTime fechaFin, String rutaArchivo) {
        // Implementación básica - usaría Apache Commons CSV
        System.out.println("Generando reporte CSV: " + tipoReporte);
        // En implementación real, se escribiría el archivo CSV
    }

    public void generarReportePDF(String tipoReporte, LocalDateTime fechaInicio, LocalDateTime fechaFin, String rutaArchivo) {
        // Implementación básica - usaría iText o similar
        System.out.println("Generando reporte PDF: " + tipoReporte);
        // En implementación real, se generaría el PDF
    }

    private long contarPorEstado(List<com.logistics.model.entities.Envio> envios, EstadoEnvio estado) {
        return envios.stream()
                .map(e -> envioService.obtenerEnvio(e.getIdEnvio()))
                .filter(e -> e != null && e.getEstado() == estado)
                .count();
    }
}

