package com.logistics.service;

import com.logistics.model.dto.TarifaDTO;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Envio;
import com.logistics.patterns.structural.decorator.Adicional;
import com.logistics.patterns.structural.decorator.AdicionalBase;
import com.logistics.patterns.structural.decorator.FragilDecorator;
import com.logistics.patterns.structural.decorator.FirmaDecorator;
import com.logistics.patterns.structural.decorator.PrioridadDecorator;
import com.logistics.patterns.structural.decorator.SeguroDecorator;
import java.util.HashMap;
import java.util.Map;

public class TarifaService {
    // cálculo simple basado en distancia aproximada (mock)
    public double calcular(Direccion origen, Direccion destino, double peso){
        double base = 5000;
        double distanciaFactor = Math.hypot(origen.getLat()-destino.getLat(), origen.getLon()-destino.getLon()) * 10000;
        double pesoFactor = Math.max(1.0, peso) * 1000;
        return base + distanciaFactor + pesoFactor;
    }

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
        
        double costoTotal = adicional.aplicar(envio);
        envio.setCosto(costoTotal);
        
        TarifaDTO tarifaDTO = new TarifaDTO();
        tarifaDTO.setCostoBase(costoBase);
        tarifaDTO.setCostoTotal(costoTotal);
        tarifaDTO.setDesgloseServicios(desglose);
        
        return tarifaDTO;
    }
}
