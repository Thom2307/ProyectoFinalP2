package com.logistics.model.dto;

import java.util.Map;

public class TarifaDTO {
    private double costoBase;
    private double costoTotal;
    private Map<String, Double> desgloseServicios;

    public TarifaDTO() {}

    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }
    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }
    public Map<String, Double> getDesgloseServicios() { return desgloseServicios; }
    public void setDesgloseServicios(Map<String, Double> desgloseServicios) { this.desgloseServicios = desgloseServicios; }
}

