package com.logistics.controller.admin;

import com.logistics.model.enums.EstadoEnvio;
import com.logistics.service.ReporteService;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para el dashboard del administrador.
 * Proporciona métricas y estadísticas generales del sistema.
 */
public class DashboardAdminController {
    private ReporteService reporteService;

    /**
     * Constructor que inicializa el servicio de reportes.
     */
    public DashboardAdminController() {
        this.reporteService = new ReporteService();
    }

    /**
     * Obtiene las métricas generales del sistema.
     * Incluye información como total de envíos, usuarios e ingresos.
     * 
     * @return Mapa con las métricas generales del sistema
     */
    public Map<String, Object> obtenerMetricas() {
        return reporteService.obtenerMetricasGenerales();
    }

    /**
     * Obtiene la distribución de envíos por estado.
     * Muestra cuántos envíos hay en cada estado (SOLICITADO, ASIGNADO, EN_RUTA, etc.).
     * 
     * @return Mapa que relaciona cada estado con la cantidad de envíos en ese estado
     */
    public Map<EstadoEnvio, Long> obtenerDistribucionEstados() {
        return reporteService.obtenerDistribucionEstados();
    }

    /**
     * Obtiene los servicios adicionales más utilizados.
     * Muestra qué servicios adicionales (seguro, empaque especial, etc.) son más populares.
     * 
     * @return Mapa que relaciona cada servicio adicional con la cantidad de veces que se ha usado
     */
    public Map<String, Long> obtenerServiciosAdicionales() {
        return reporteService.obtenerServiciosAdicionalesMasUsados();
    }
}

