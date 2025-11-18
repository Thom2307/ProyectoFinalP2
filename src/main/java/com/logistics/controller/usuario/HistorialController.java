package com.logistics.controller.usuario;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.service.EnvioService;
import com.logistics.util.NavigationManager;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistorialController {
    private EnvioService envioService;

    public HistorialController() {
        this.envioService = new EnvioService();
    }

    public List<EnvioDTO> obtenerEnviosUsuario() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        if (usuarioId == null) {
            return java.util.Collections.emptyList();
        }
        return envioService.filtrarPorUsuarioYFecha(usuarioId, null, null);
    }

    public String formatearFecha(java.time.LocalDateTime fecha) {
        if (fecha == null) return "N/A";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formatter);
    }

    public String formatearCosto(double costo) {
        return String.format("$%,.2f", costo);
    }
}

