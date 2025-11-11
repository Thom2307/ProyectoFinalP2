package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EnvioDTO {
    private String idEnvio;
    private String usuarioNombre;
    private String origen;
    private String destino;
    private double peso;
    private double volumen;
    private double costo;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDate fechaEstimadaEntrega;
    private String repartidorNombre;
    private int cantidadServiciosAdicionales;
    private boolean tienePago;

    public EnvioDTO() {}

    // Getters y Setters
    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }
    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getVolumen() { return volumen; }
    public void setVolumen(double volumen) { this.volumen = volumen; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDate getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }
    public String getRepartidorNombre() { return repartidorNombre; }
    public void setRepartidorNombre(String repartidorNombre) { this.repartidorNombre = repartidorNombre; }
    public int getCantidadServiciosAdicionales() { return cantidadServiciosAdicionales; }
    public void setCantidadServiciosAdicionales(int cantidadServiciosAdicionales) { this.cantidadServiciosAdicionales = cantidadServiciosAdicionales; }
    public boolean isTienePago() { return tienePago; }
    public void setTienePago(boolean tienePago) { this.tienePago = tienePago; }
}


