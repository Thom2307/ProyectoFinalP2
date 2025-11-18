package com.logistics.controller.usuario;

import com.logistics.model.dto.TarifaDTO;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Envio;
import com.logistics.service.TarifaService;
import java.util.List;

/**
 * Controlador para el cálculo de cotizaciones de envíos.
 * Permite calcular el costo de un envío basado en origen, destino, peso y servicios adicionales.
 */
public class CotizadorController {
    private TarifaService tarifaService;

    /**
     * Constructor que inicializa el servicio de tarifas.
     */
    public CotizadorController() {
        this.tarifaService = new TarifaService();
    }

    /**
     * Calcula la tarifa de un envío basándose en las coordenadas de origen y destino,
     * el peso del paquete y los servicios adicionales seleccionados.
     * 
     * @param latOrigen Latitud del punto de origen
     * @param lonOrigen Longitud del punto de origen
     * @param latDestino Latitud del punto de destino
     * @param lonDestino Longitud del punto de destino
     * @param peso El peso del paquete en kilogramos
     * @param serviciosAdicionales Lista de servicios adicionales (SEGURO, EMPAQUE_ESPECIAL, etc.)
     * @return DTO con el desglose de la tarifa calculada (costo base, servicios adicionales, total)
     */
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

