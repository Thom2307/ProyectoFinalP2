package com.logistics.model.entities;

import com.logistics.model.enums.DisponibilidadRepartidor;
import java.util.ArrayList;
import java.util.List;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String telefono;
    private String vehiculo;
    private DisponibilidadRepartidor disponibilidad;
    private List<Envio> enviosAsignados = new ArrayList<>();

    public Repartidor() {}

    public Repartidor(String id, String nombre, String telefono, String vehiculo) {
        this.idRepartidor = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.vehiculo = vehiculo;
        this.disponibilidad = DisponibilidadRepartidor.DISPONIBLE;
    }

    public String getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(String id) { this.idRepartidor = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }
    public DisponibilidadRepartidor getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(DisponibilidadRepartidor disponibilidad) { this.disponibilidad = disponibilidad; }
    public List<Envio> getEnviosAsignados() { return enviosAsignados; }
}

