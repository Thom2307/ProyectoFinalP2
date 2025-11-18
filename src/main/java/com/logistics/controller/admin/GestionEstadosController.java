package com.logistics.controller.admin;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.service.EnvioService;
import java.util.List;

/**
 * Controlador para la gestión de estados de envíos.
 * Permite a los administradores cambiar estados de envíos y reportar incidencias.
 */
public class GestionEstadosController {
    private EnvioService envioService;

    /**
     * Constructor que inicializa el servicio de envíos.
     */
    public GestionEstadosController() {
        this.envioService = new EnvioService();
    }

    /**
     * Obtiene todos los envíos del sistema.
     * 
     * @return Lista con todos los envíos registrados
     */
    public List<EnvioDTO> obtenerTodosLosEnvios() {
        return envioService.listarEnvios();
    }
    
    /**
     * Obtiene un envío específico por su identificador.
     * 
     * @param idEnvio El identificador único del envío
     * @return El DTO del envío encontrado, o null si no existe
     */
    public EnvioDTO obtenerEnvio(String idEnvio) {
        return envioService.obtenerEnvio(idEnvio);
    }

    /**
     * Cambia el estado de un envío según la acción especificada.
     * Las acciones válidas son: "ASIGNAR", "EN_RUTA", "ENTREGADO", "CANCELAR".
     * 
     * @param idEnvio El identificador único del envío
     * @param accion La acción a realizar (ASIGNAR, EN_RUTA, ENTREGADO, CANCELAR)
     */
    public void cambiarEstado(String idEnvio, String accion) {
        envioService.cambiarEstado(idEnvio, accion);
    }

    /**
     * Reporta una incidencia en un envío.
     * Permite registrar problemas o eventos especiales relacionados con un envío.
     * 
     * @param idEnvio El identificador único del envío
     * @param descripcion La descripción de la incidencia reportada
     */
    public void reportarIncidencia(String idEnvio, String descripcion) {
        envioService.reportarIncidencia(idEnvio, descripcion);
    }

    /**
     * Verifica si es posible cambiar de un estado a otro según la acción especificada.
     * Valida las transiciones de estado permitidas en el sistema.
     * 
     * @param estadoActual El estado actual del envío
     * @param nuevaAccion La acción que se desea realizar
     * @return true si la transición es válida, false en caso contrario
     */
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

