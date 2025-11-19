package com.logistics.model.entities;

import com.logistics.model.enums.EstadoRepartidor;
import com.logistics.model.enums.Ruta;
import java.util.ArrayList;
import java.util.List;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private String vehiculo;
    private EstadoRepartidor disponibilidad;
    private String zonaCobertura;
    private Ruta ruta;
    private List<Envio> enviosAsignados = new ArrayList<>();

    public Repartidor() {}

    public Repartidor(String id, String nombre, String telefono, String vehiculo) {
        this.idRepartidor = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.vehiculo = vehiculo;
        this.disponibilidad = EstadoRepartidor.ACTIVO;
    }

    public Repartidor(String id, String nombre, String documento, String telefono, String vehiculo, String zonaCobertura) {
        this.idRepartidor = id;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.vehiculo = vehiculo;
        this.zonaCobertura = zonaCobertura;
        this.disponibilidad = EstadoRepartidor.ACTIVO;
    }

    // Getters y Setters
    public String getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(String id) { this.idRepartidor = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }
    
    public EstadoRepartidor getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(EstadoRepartidor disponibilidad) { this.disponibilidad = disponibilidad; }
    
    public String getZonaCobertura() { return zonaCobertura; }
    public void setZonaCobertura(String zonaCobertura) { this.zonaCobertura = zonaCobertura; }
    
    public Ruta getRuta() { return ruta; }
    public void setRuta(Ruta ruta) { this.ruta = ruta; }
    
    public List<Envio> getEnviosAsignados() { return enviosAsignados; }
    
    // Métodos de negocio
    public boolean estaDisponible() {
        return disponibilidad == EstadoRepartidor.ACTIVO;
    }
    
    public boolean perteneceAZona(String zona) {
        return zonaCobertura != null && zonaCobertura.equalsIgnoreCase(zona);
    }
    
    public boolean puedeAtenderMunicipio(String municipio) {
        if (ruta == null) return false;
        return ruta.contieneMunicipio(municipio);
    }
}

