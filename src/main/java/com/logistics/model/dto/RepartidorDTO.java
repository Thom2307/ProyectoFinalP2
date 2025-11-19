package com.logistics.model.dto;

import com.logistics.model.entities.Repartidor;
import com.logistics.model.enums.EstadoRepartidor;
import com.logistics.model.enums.Ruta;

public class RepartidorDTO {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private String vehiculo;
    private EstadoRepartidor disponibilidad;
    private String disponibilidadString; // Para la vista
    private String zonaCobertura;
    private Ruta ruta;
    private String rutaString; // Para la vista
    private int cantidadEnviosAsignados;

    public RepartidorDTO() {}

    // Getters y Setters
    public String getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(String idRepartidor) { this.idRepartidor = idRepartidor; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }
    
    public EstadoRepartidor getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(EstadoRepartidor disponibilidad) { 
        this.disponibilidad = disponibilidad;
        this.disponibilidadString = disponibilidad != null ? disponibilidad.toString() : "";
    }
    
    public String getDisponibilidadString() { 
        return disponibilidadString != null ? disponibilidadString : 
               (disponibilidad != null ? disponibilidad.toString() : "");
    }
    public void setDisponibilidadString(String disponibilidadString) { 
        this.disponibilidadString = disponibilidadString;
    }
    
    public String getZonaCobertura() { return zonaCobertura; }
    public void setZonaCobertura(String zonaCobertura) { this.zonaCobertura = zonaCobertura; }
    
    public Ruta getRuta() { return ruta; }
    public void setRuta(Ruta ruta) { 
        this.ruta = ruta;
        this.rutaString = ruta != null ? ruta.getNombre() : "";
    }
    
    public String getRutaString() {
        return rutaString != null ? rutaString : 
               (ruta != null ? ruta.getNombre() : "");
    }
    public void setRutaString(String rutaString) {
        this.rutaString = rutaString;
    }
    
    public int getCantidadEnviosAsignados() { return cantidadEnviosAsignados; }
    public void setCantidadEnviosAsignados(int cantidadEnviosAsignados) { 
        this.cantidadEnviosAsignados = cantidadEnviosAsignados; 
    }
    
    // Métodos de conversión
    /**
     * Convierte el DTO a una entidad Repartidor
     */
    public Repartidor toEntity() {
        Repartidor repartidor = new Repartidor();
        repartidor.setIdRepartidor(this.idRepartidor);
        repartidor.setNombre(this.nombre);
        repartidor.setDocumento(this.documento);
        repartidor.setTelefono(this.telefono);
        repartidor.setVehiculo(this.vehiculo);
        repartidor.setDisponibilidad(this.disponibilidad);
        repartidor.setZonaCobertura(this.zonaCobertura);
        repartidor.setRuta(this.ruta);
        return repartidor;
    }
    
    /**
     * Crea un DTO desde una entidad Repartidor
     */
    public static RepartidorDTO fromEntity(Repartidor repartidor) {
        if (repartidor == null) return null;
        
        RepartidorDTO dto = new RepartidorDTO();
        dto.setIdRepartidor(repartidor.getIdRepartidor());
        dto.setNombre(repartidor.getNombre());
        dto.setDocumento(repartidor.getDocumento());
        dto.setTelefono(repartidor.getTelefono());
        dto.setVehiculo(repartidor.getVehiculo());
        dto.setDisponibilidad(repartidor.getDisponibilidad());
        dto.setZonaCobertura(repartidor.getZonaCobertura());
        dto.setRuta(repartidor.getRuta());
        dto.setCantidadEnviosAsignados(
            repartidor.getEnviosAsignados() != null ? 
            repartidor.getEnviosAsignados().size() : 0
        );
        return dto;
    }
}

