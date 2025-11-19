package com.logistics.controller.usuario;

import com.logistics.service.EnvioService;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.util.NavigationManager;
import java.util.List;

/**
 * Controlador para el dashboard del usuario.
 * Proporciona estadísticas y métricas de los envíos del usuario actual.
 */
public class DashboardUsuarioController {
    private EnvioService envioService;

    /**
     * Constructor que inicializa el servicio de envíos.
     */
    public DashboardUsuarioController() {
        this.envioService = new EnvioService();
    }

    /**
     * Obtiene el número de envíos activos del usuario actual.
     * Se consideran activos los envíos que no están ENTREGADOS ni CANCELADOS.
     * 
     * @return Cantidad de envíos activos del usuario
     */
    public int obtenerEnviosActivos() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        List<com.logistics.model.dto.EnvioDTO> envios = envioService.filtrarPorUsuarioYFecha(usuarioId, null, null);
        return (int) envios.stream()
            .filter(e -> e.getEstado() != EstadoEnvio.ENTREGADO && e.getEstado() != EstadoEnvio.CANCELADO)
            .count();
    }

    /**
     * Obtiene el total de envíos del usuario actual.
     * Incluye todos los envíos independientemente de su estado.
     * 
     * @return Total de envíos del usuario
     */
    public int obtenerTotalEnvios() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        List<com.logistics.model.dto.EnvioDTO> envios = envioService.filtrarPorUsuarioYFecha(usuarioId, null, null);
        return envios.size();
    }

    /**
     * Obtiene el número de envíos en tránsito del usuario actual.
     * Se consideran en tránsito los envíos con estado ASIGNADO o EN_RUTA.
     * 
     * @return Cantidad de envíos en tránsito
     */
    public int obtenerEnTransito() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        List<com.logistics.model.dto.EnvioDTO> envios = envioService.filtrarPorUsuarioYFecha(usuarioId, null, null);
        return (int) envios.stream()
            .filter(e -> e.getEstado() == EstadoEnvio.EN_RUTA || e.getEstado() == EstadoEnvio.ASIGNADO)
            .count();
    }
}

