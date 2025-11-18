package com.logistics.controller.usuario;

import com.logistics.model.dto.TarifaDTO;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Envio;
import com.logistics.service.TarifaService;
import java.util.List;

public class CotizadorController {
    private TarifaService tarifaService;

    public CotizadorController() {
        this.tarifaService = new TarifaService();
    }

    public TarifaDTO calcularTarifa(double latOrigen, double lonOrigen, 
                                   double latDestino, double lonDestino,
                                   double peso, List<String> serviciosAdicionales) {
        Direccion origen = new Direccion("temp", "Origen", "", "", latOrigen, lonOrigen);
        Direccion destino = new Direccion("temp", "Destino", "", "", latDestino, lonDestino);
        
        Envio envio = new Envio();
        envio.setOrigen(origen);
        envio.setDestino(destino);
        envio.setPeso(peso);
        
        return tarifaService.calcularTarifa(envio, serviciosAdicionales);
    }
}

