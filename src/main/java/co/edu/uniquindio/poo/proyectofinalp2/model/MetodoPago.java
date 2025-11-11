package model;

public class MetodoPago {
    private String idMetodoPago;
    private String tipo; // TARJETA, EFECTIVO, TRANSFERENCIA
    private String numeroTarjeta; // Solo si es tarjeta
    private String nombreTitular;
    private boolean activo;

    public MetodoPago(String idMetodoPago, String tipo, String numeroTarjeta, String nombreTitular) {
        this.idMetodoPago = idMetodoPago;
        this.tipo = tipo;
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.activo = true;
    }

    // Getters y Setters
    public String getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(String idMetodoPago) { this.idMetodoPago = idMetodoPago; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }
    public String getNombreTitular() { return nombreTitular; }
    public void setNombreTitular(String nombreTitular) { this.nombreTitular = nombreTitular; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return tipo + " - " + (numeroTarjeta != null ? "****" + numeroTarjeta.substring(Math.max(0, numeroTarjeta.length() - 4)) : nombreTitular);
    }
}
