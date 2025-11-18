package com.logistics.controller.usuario;

import com.logistics.service.EnvioService;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.util.NavigationManager;
import java.util.List;

public class DashboardUsuarioController {
    private EnvioService envioService;

    public DashboardUsuarioController() {
        this.envioService = new EnvioService();
    }

    public int obtenerEnviosActivos() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        List<com.logistics.model.dto.EnvioDTO> envios = envioService.filtrarPorUsuarioYFecha(usuarioId, null, null);
        return (int) envios.stream()
            .filter(e -> e.getEstado() != EstadoEnvio.ENTREGADO && e.getEstado() != EstadoEnvio.CANCELADO)
            .count();
    }

    public int obtenerTotalEnvios() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        List<com.logistics.model.dto.EnvioDTO> envios = envioService.filtrarPorUsuarioYFecha(usuarioId, null, null);
        return envios.size();
    }

    public int obtenerEnTransito() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        List<com.logistics.model.dto.EnvioDTO> envios = envioService.filtrarPorUsuarioYFecha(usuarioId, null, null);
        return (int) envios.stream()
            .filter(e -> e.getEstado() == EstadoEnvio.EN_RUTA || e.getEstado() == EstadoEnvio.ASIGNADO)
            .count();
    }
}

