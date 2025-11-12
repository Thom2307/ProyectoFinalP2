package service;

import model.*;
import dto.TarifaDTO;

public class TarifaService {
    private TarifaStrategy estrategiaTarifa;

    public TarifaService() {
        this.estrategiaTarifa = new TarifaBase();
    }

    public double cotizarTarifa(Direccion origen, Direccion destino, double peso, double volumen, String prioridad) {
        Tarifa tarifa = new Tarifa(estrategiaTarifa);
        return tarifa.calcularCosto(origen, destino, peso, volumen, prioridad);
    }

    public TarifaDTO desglosarTarifa(Direccion origen, Direccion destino, double peso, double volumen, String prioridad) {
        Tarifa tarifa = new Tarifa(estrategiaTarifa);
        tarifa.desglosarTarifa(origen, destino, peso, volumen, prioridad);
        
        TarifaDTO dto = new TarifaDTO();
        dto.setCostoBase(tarifa.getCostoBase());
        dto.setCostoDistancia(tarifa.getCostoDistancia());
        dto.setCostoPeso(tarifa.getCostoPeso());
        dto.setCostoVolumen(tarifa.getCostoVolumen());
        dto.setRecargoPrioridad(tarifa.getRecargoPrioridad());
        dto.setCostoTotal(tarifa.getCostoBase() + tarifa.getCostoDistancia() + 
                          tarifa.getCostoPeso() + tarifa.getCostoVolumen() + 
                          tarifa.getRecargoPrioridad());
        dto.setPrioridad(prioridad);
        
        return dto;
    }

    public void setEstrategiaTarifa(TarifaStrategy estrategia) {
        this.estrategiaTarifa = estrategia;
    }
}


