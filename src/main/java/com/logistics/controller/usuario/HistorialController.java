package com.logistics.controller.usuario;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.service.EnvioService;
import com.logistics.util.NavigationManager;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para el historial de envíos del usuario.
 * Proporciona métodos para obtener y formatear el historial de envíos.
 */
public class HistorialController {
    private EnvioService envioService;

    /**
     * Constructor que inicializa el servicio de envíos.
     */
    public HistorialController() {
        this.envioService = new EnvioService();
    }

    /**
     * Obtiene todos los envíos del usuario actual.
     * 
     * @return Lista con todos los envíos del usuario, o lista vacía si no hay usuario autenticado
     */
    public List<EnvioDTO> obtenerEnviosUsuario() {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        if (usuarioId == null) {
            return java.util.Collections.emptyList();
        }
        return envioService.filtrarPorUsuarioYFecha(usuarioId, null, null);
    }

    /**
     * Formatea una fecha y hora en formato legible.
     * 
     * @param fecha La fecha y hora a formatear
     * @return Fecha formateada como "dd/MM/yyyy HH:mm", o "N/A" si la fecha es null
     */
    public String formatearFecha(java.time.LocalDateTime fecha) {
        if (fecha == null) return "N/A";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formatter);
    }

    /**
     * Formatea un costo monetario con formato de moneda.
     * 
     * @param costo El costo a formatear
     * @return Costo formateado con símbolo de dólar y separadores de miles (ej: "$1,234.56")
     */
    public String formatearCosto(double costo) {
        return String.format("$%,.2f", costo);
    }
}

