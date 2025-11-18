package com.logistics.controller.usuario;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.service.EnvioService;

/**
 * Controlador para el rastreo de envíos.
 * Permite buscar y obtener información de un envío específico por su identificador.
 */
public class RastreoController {
    private EnvioService envioService;

    /**
     * Constructor que inicializa el servicio de envíos.
     */
    public RastreoController() {
        this.envioService = new EnvioService();
    }

    /**
     * Busca un envío por su identificador único.
     * 
     * @param idEnvio El identificador único del envío a buscar
     * @return DTO con la información del envío encontrado, o null si no existe
     */
    public EnvioDTO buscarEnvio(String idEnvio) {
        return envioService.obtenerEnvio(idEnvio);
    }
}

