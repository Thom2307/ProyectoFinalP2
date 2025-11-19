package com.logistics.model.dto;

import com.logistics.model.enums.EstadoEnvio;
import java.time.LocalDateTime;

public class EnvioTableItem {
    private String idEnvio;
    private EstadoEnvio estado;
    private LocalDateTime fecha;
    private double costo;
    private String origen;
    private String destino;
    private String nombreUsuario;
    private String idUsuario;

    public EnvioTableItem() {}

    public EnvioTableItem(String idEnvio, EstadoEnvio estado, LocalDateTime fecha, double costo, String origen, String destino) {
        this.idEnvio = idEnvio;
        this.estado = estado;
        this.fecha = fecha;
        this.costo = costo;
        this.origen = origen;
        this.destino = destino;
    }
    
    public EnvioTableItem(String idEnvio, EstadoEnvio estado, LocalDateTime fecha, double costo, String origen, String destino, String nombreUsuario) {
        this.idEnvio = idEnvio;
        this.estado = estado;
        this.fecha = fecha;
        this.costo = costo;
        this.origen = origen;
        this.destino = destino;
        this.nombreUsuario = nombreUsuario;
    }
    
    public EnvioTableItem(String idEnvio, EstadoEnvio estado, LocalDateTime fecha, double costo, String origen, String destino, String nombreUsuario, String idUsuario) {
        this.idEnvio = idEnvio;
        this.estado = estado;
        this.fecha = fecha;
        this.costo = costo;
        this.origen = origen;
        this.destino = destino;
        this.nombreUsuario = nombreUsuario;
        this.idUsuario = idUsuario;
    }

    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }
    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    
    public String getFechaFormateada() {
        if (fecha == null) return "N/A";
        return fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    public String getCostoFormateado() {
        return String.format("$%,.2f", costo);
    }
}

