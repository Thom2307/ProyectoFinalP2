package com.logistics.controller.usuario;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.service.EnvioService;

public class RastreoController {
    private EnvioService envioService;

    public RastreoController() {
        this.envioService = new EnvioService();
    }

    public EnvioDTO buscarEnvio(String idEnvio) {
        return envioService.obtenerEnvio(idEnvio);
    }
}

