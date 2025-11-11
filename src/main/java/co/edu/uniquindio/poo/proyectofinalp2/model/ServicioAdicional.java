package model;

public class ServicioAdicional {
    private String idServicio;
    private String tipo; // SEGURO, FRAGIL, FIRMA_REQUERIDA, PRIORIDAD
    private String descripcion;
    private double costoAdicional;

    public ServicioAdicional(String idServicio, String tipo, String descripcion, double costoAdicional) {
        this.idServicio = idServicio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.costoAdicional = costoAdicional;
    }

    // Getters y Setters
    public String getIdServicio() { return idServicio; }
    public void setIdServicio(String idServicio) { this.idServicio = idServicio; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getCostoAdicional() { return costoAdicional; }
    public void setCostoAdicional(double costoAdicional) { this.costoAdicional = costoAdicional; }

    @Override
    public String toString() {
        return tipo + " - " + descripcion + " (+$" + costoAdicional + ")";
    }
}
