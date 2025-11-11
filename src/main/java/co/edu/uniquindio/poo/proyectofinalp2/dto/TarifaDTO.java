package dto;

public class TarifaDTO {
    private double costoBase;
    private double costoDistancia;
    private double costoPeso;
    private double costoVolumen;
    private double recargoPrioridad;
    private double costoTotal;
    private String prioridad;

    public TarifaDTO() {}

    // Getters y Setters
    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }
    public double getCostoDistancia() { return costoDistancia; }
    public void setCostoDistancia(double costoDistancia) { this.costoDistancia = costoDistancia; }
    public double getCostoPeso() { return costoPeso; }
    public void setCostoPeso(double costoPeso) { this.costoPeso = costoPeso; }
    public double getCostoVolumen() { return costoVolumen; }
    public void setCostoVolumen(double costoVolumen) { this.costoVolumen = costoVolumen; }
    public double getRecargoPrioridad() { return recargoPrioridad; }
    public void setRecargoPrioridad(double recargoPrioridad) { this.recargoPrioridad = recargoPrioridad; }
    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
}


