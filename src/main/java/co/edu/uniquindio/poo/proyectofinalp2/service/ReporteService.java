package service;

import model.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.*;
import java.util.stream.Collectors;

public class ReporteService {
    private EnvioService envioService;
    private PagoService pagoService;

    public ReporteService(EnvioService envioService, PagoService pagoService) {
        this.envioService = envioService;
        this.pagoService = pagoService;
    }

    public void generarReporteCSV(String tipoReporte, LocalDate fechaInicio, LocalDate fechaFin, String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            switch (tipoReporte) {
                case "ENTREGAS":
                    generarReporteEntregasCSV(writer, fechaInicio, fechaFin);
                    break;
                case "TIEMPOS":
                    generarReporteTiemposCSV(writer, fechaInicio, fechaFin);
                    break;
                case "INGRESOS":
                    generarReporteIngresosCSV(writer, fechaInicio, fechaFin);
                    break;
                case "INCIDENCIAS":
                    generarReporteIncidenciasCSV(writer, fechaInicio, fechaFin);
                    break;
            }
        } catch (IOException e) {
            System.err.println("Error al generar reporte CSV: " + e.getMessage());
        }
    }

    private void generarReporteEntregasCSV(PrintWriter writer, LocalDate fechaInicio, LocalDate fechaFin) {
        writer.println("ID Envío,Fecha Creación,Estado,Usuario,Costo,Zona");
        List<Envio> envios = envioService.filtrarPorFecha(fechaInicio, fechaFin);
        for (Envio envio : envios) {
            writer.println(String.format("%s,%s,%s,%s,%.2f,%s",
                envio.getIdEnvio(),
                envio.getFechaCreacion(),
                envio.getEstado(),
                envio.getUsuarioNombre(),
                envio.getCosto(),
                envio.getDestino() != null ? envio.getDestino().getCiudad() : "N/A"
            ));
        }
    }

    private void generarReporteTiemposCSV(PrintWriter writer, LocalDate fechaInicio, LocalDate fechaFin) {
        writer.println("Zona,Tiempo Promedio (días),Cantidad Envíos");
        Map<String, List<Envio>> enviosPorZona = envioService.filtrarPorFecha(fechaInicio, fechaFin).stream()
            .filter(e -> "ENTREGADO".equals(e.getEstado()))
            .collect(Collectors.groupingBy(e -> 
                e.getDestino() != null ? e.getDestino().getCiudad() : "N/A"
            ));
        
        for (Map.Entry<String, List<Envio>> entry : enviosPorZona.entrySet()) {
            double tiempoPromedio = entry.getValue().stream()
                .mapToLong(e -> java.time.temporal.ChronoUnit.DAYS.between(
                    e.getFechaCreacion().toLocalDate(), 
                    e.getFechaEstimadaEntrega()
                ))
                .average()
                .orElse(0.0);
            writer.println(String.format("%s,%.2f,%d", entry.getKey(), tiempoPromedio, entry.getValue().size()));
        }
    }

    private void generarReporteIngresosCSV(PrintWriter writer, LocalDate fechaInicio, LocalDate fechaFin) {
        writer.println("Servicio Adicional,Ingresos,Cantidad");
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);
        
        Map<String, Double> ingresosPorServicio = new HashMap<>();
        Map<String, Integer> cantidadPorServicio = new HashMap<>();
        
        List<Pago> pagos = pagoService.listarPorRangoFechas(inicio, fin);
        for (Pago pago : pagos) {
            if ("APROBADO".equals(pago.getResultado())) {
                for (ServicioAdicional servicio : pago.getEnvio().getServiciosAdicionales()) {
                    ingresosPorServicio.merge(servicio.getTipo(), servicio.getCostoAdicional(), Double::sum);
                    cantidadPorServicio.merge(servicio.getTipo(), 1, Integer::sum);
                }
            }
        }
        
        for (Map.Entry<String, Double> entry : ingresosPorServicio.entrySet()) {
            writer.println(String.format("%s,%.2f,%d",
                entry.getKey(),
                entry.getValue(),
                cantidadPorServicio.getOrDefault(entry.getKey(), 0)
            ));
        }
    }

    private void generarReporteIncidenciasCSV(PrintWriter writer, LocalDate fechaInicio, LocalDate fechaFin) {
        writer.println("Tipo Incidencia,Zona,Cantidad,Estado");
        List<Envio> envios = envioService.filtrarPorFecha(fechaInicio, fechaFin);
        Map<String, Integer> incidenciasPorTipo = new HashMap<>();
        Map<String, String> zonasPorTipo = new HashMap<>();
        
        for (Envio envio : envios) {
            for (Incidencia incidencia : envio.getIncidencias()) {
                incidenciasPorTipo.merge(incidencia.getTipo(), 1, Integer::sum);
                if (envio.getDestino() != null) {
                    zonasPorTipo.put(incidencia.getTipo(), envio.getDestino().getCiudad());
                }
            }
        }
        
        for (Map.Entry<String, Integer> entry : incidenciasPorTipo.entrySet()) {
            writer.println(String.format("%s,%s,%d,%s",
                entry.getKey(),
                zonasPorTipo.getOrDefault(entry.getKey(), "N/A"),
                entry.getValue(),
                "PENDIENTE"
            ));
        }
    }

    public void generarReportePDF(String tipoReporte, LocalDate fechaInicio, LocalDate fechaFin, String rutaArchivo) {
        // Implementación simplificada - en producción usaría Apache PDFBox
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo.replace(".pdf", ".txt")))) {
            writer.println("=== REPORTE " + tipoReporte + " ===");
            writer.println("Período: " + fechaInicio + " a " + fechaFin);
            writer.println();
            
            // Generar contenido del reporte (versión texto por simplicidad)
            generarReporteCSV(tipoReporte, fechaInicio, fechaFin, rutaArchivo.replace(".pdf", "_temp.csv"));
            
            writer.println("Reporte generado exitosamente.");
            writer.println("Nota: Para PDF completo, se requiere Apache PDFBox.");
        } catch (IOException e) {
            System.err.println("Error al generar reporte PDF: " + e.getMessage());
        }
    }

    public Map<String, Object> obtenerMetricas(LocalDate fechaInicio, LocalDate fechaFin) {
        Map<String, Object> metricas = new HashMap<>();
        
        List<Envio> envios = envioService.filtrarPorFecha(fechaInicio, fechaFin);
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);
        
        metricas.put("tiempoPromedioEntrega", envioService.calcularTiempoPromedioEntrega());
        metricas.put("totalEnvios", envios.size());
        metricas.put("enviosEntregados", envios.stream().filter(e -> "ENTREGADO".equals(e.getEstado())).count());
        metricas.put("ingresosTotales", pagoService.calcularIngresosPorPeriodo(inicio, fin));
        metricas.put("incidenciasTotales", envios.stream().mapToInt(e -> e.getIncidencias().size()).sum());
        
        return metricas;
    }
}


