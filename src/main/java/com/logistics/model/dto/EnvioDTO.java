package com.logistics.model.dto;

import com.logistics.model.enums.EstadoEnvio;
import java.time.LocalDateTime;
import java.util.List;

public class EnvioDTO {
    private String idEnvio;
    private DireccionDTO origen;
    private DireccionDTO destino;
    private double peso;
    private double costo;
    private EstadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private String idUsuario;
    private String nombreUsuario;
    private String idRepartidor;
    private String nombreRepartidor;
    private List<String> adicionales;

    public EnvioDTO() {}

    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }
    public DireccionDTO getOrigen() { return origen; }
    public void setOrigen(DireccionDTO origen) { this.origen = origen; }
    public DireccionDTO getDestino() { return destino; }
    public void setDestino(DireccionDTO destino) { this.destino = destino; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(String idRepartidor) { this.idRepartidor = idRepartidor; }
    public String getNombreRepartidor() { return nombreRepartidor; }
    public void setNombreRepartidor(String nombreRepartidor) { this.nombreRepartidor = nombreRepartidor; }
    public List<String> getAdicionales() { return adicionales; }
    public void setAdicionales(List<String> adicionales) { this.adicionales = adicionales; }
}

