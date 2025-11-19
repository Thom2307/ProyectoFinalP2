package com.logistics.model.dto;

import com.logistics.model.enums.DisponibilidadRepartidor;

public class RepartidorDTO {
    private String idRepartidor;
    private String nombre;
    private String telefono;
    private String vehiculo;
    private DisponibilidadRepartidor disponibilidad;
    private int enviosAsignados;

    public RepartidorDTO() {}

    public String getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(String idRepartidor) { this.idRepartidor = idRepartidor; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }
    public DisponibilidadRepartidor getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(DisponibilidadRepartidor disponibilidad) { this.disponibilidad = disponibilidad; }
    public int getEnviosAsignados() { return enviosAsignados; }
    public void setEnviosAsignados(int enviosAsignados) { this.enviosAsignados = enviosAsignados; }
}

