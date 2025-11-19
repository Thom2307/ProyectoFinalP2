package com.logistics.service;

import com.logistics.model.dto.TarifaDTO;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Envio;
import com.logistics.patterns.estructural.decorator.Adicional;
import com.logistics.patterns.estructural.decorator.AdicionalBase;
import com.logistics.patterns.estructural.decorator.FragilDecorator;
import com.logistics.patterns.estructural.decorator.FirmaDecorator;
import com.logistics.patterns.estructural.decorator.PrioridadDecorator;
import com.logistics.patterns.estructural.decorator.SeguroDecorator;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para el cálculo de tarifas de envíos.
 * Calcula el costo base basado en distancia y peso, y aplica servicios adicionales usando el patrón Decorator.
 */
public class TarifaService {
    /**
     * Calcula el costo base de un envío basándose en la distancia y el peso.
     * Utiliza una fórmula simplificada basada en coordenadas geográficas.
     * 
     * @param origen La dirección de origen
     * @param destino La dirección de destino
     * @param peso El peso del paquete en kilogramos
     * @return El costo base calculado
     */
    public double calcular(Direccion origen, Direccion destino, double peso){
        double base = 5000;
        double distanciaFactor = Math.hypot(origen.getLat()-destino.getLat(), origen.getLon()-destino.getLon()) * 10000;
        double pesoFactor = Math.max(1.0, peso) * 1000;
        return base + distanciaFactor + pesoFactor;
    }

    /**
     * Calcula la tarifa completa de un envío incluyendo servicios adicionales.
     * Aplica el patrón Decorator para agregar costos de servicios adicionales al costo base.
     * 
     * @param envio La entidad Envio con origen, destino y peso
     * @param serviciosAdicionales Lista de servicios adicionales (SEGURO, FRAGIL, FIRMA, PRIORIDAD)
     * @return DTO con el desglose completo de la tarifa (costo base, servicios adicionales, total)
     */
    public TarifaDTO calcularTarifa(Envio envio, java.util.List<String> serviciosAdicionales) {
        double costoBase = calcular(envio.getOrigen(), envio.getDestino(), envio.getPeso());
        envio.setCosto(costoBase);
        
        Adicional adicional = new AdicionalBase();
        Map<String, Double> desglose = new HashMap<>();
        desglose.put("Base", costoBase);
        
        if (serviciosAdicionales != null) {
            for (String servicio : serviciosAdicionales) {
                double costoAntes = adicional.aplicar(envio);
                switch (servicio.toUpperCase()) {
                    case "SEGURO":
                        adicional = new SeguroDecorator(adicional);
                        break;
                    case "FRAGIL":
                        adicional = new FragilDecorator(adicional);
                        break;
                    case "FIRMA":
                        adicional = new FirmaDecorator(adicional);
                        break;
                    case "PRIORIDAD":
                        adicional = new PrioridadDecorator(adicional);
                        break;
                }
                double costoDespues = adicional.aplicar(envio);
                desglose.put(servicio, costoDespues - costoAntes);
            }
        }
        
        // El costo total debe ser: costo base + todos los incrementos de servicios adicionales
        double incrementosAdicionales = adicional.aplicar(envio);
        double costoTotal = costoBase + incrementosAdicionales;
        envio.setCosto(costoTotal);
        
        TarifaDTO tarifaDTO = new TarifaDTO();
        tarifaDTO.setCostoBase(costoBase);
        tarifaDTO.setCostoTotal(costoTotal);
        tarifaDTO.setDesgloseServicios(desglose);
        
        return tarifaDTO;
    }
}
