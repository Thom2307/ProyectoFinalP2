package com.logistics.controller.admin;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.service.EnvioService;
import java.util.List;

public class GestionEstadosController {
    private EnvioService envioService;

    public GestionEstadosController() {
        this.envioService = new EnvioService();
    }

    public List<EnvioDTO> obtenerTodosLosEnvios() {
        return envioService.listarEnvios();
    }
    
    public EnvioDTO obtenerEnvio(String idEnvio) {
        return envioService.obtenerEnvio(idEnvio);
    }

    public void cambiarEstado(String idEnvio, String accion) {
        envioService.cambiarEstado(idEnvio, accion);
    }

    public void reportarIncidencia(String idEnvio, String descripcion) {
        envioService.reportarIncidencia(idEnvio, descripcion);
    }

    public boolean puedeCambiarEstado(EstadoEnvio estadoActual, String nuevaAccion) {
        switch (estadoActual) {
            case SOLICITADO:
                return nuevaAccion.equals("ASIGNAR") || nuevaAccion.equals("CANCELAR");
            case ASIGNADO:
                return nuevaAccion.equals("EN_RUTA") || nuevaAccion.equals("CANCELAR");
            case EN_RUTA:
                return nuevaAccion.equals("ENTREGADO");
            case ENTREGADO:
            case CANCELADO:
                return false;
            default:
                return false;
        }
    }
}

