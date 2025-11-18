package com.logistics.controller.admin;

import com.logistics.model.enums.EstadoEnvio;
import com.logistics.service.ReporteService;
import java.util.HashMap;
import java.util.Map;

public class DashboardAdminController {
    private ReporteService reporteService;

    public DashboardAdminController() {
        this.reporteService = new ReporteService();
    }

    public Map<String, Object> obtenerMetricas() {
        return reporteService.obtenerMetricasGenerales();
    }

    public Map<EstadoEnvio, Long> obtenerDistribucionEstados() {
        return reporteService.obtenerDistribucionEstados();
    }

    public Map<String, Long> obtenerServiciosAdicionales() {
        return reporteService.obtenerServiciosAdicionalesMasUsados();
    }
}

