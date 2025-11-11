package model;

import java.util.ArrayList;
import java.util.List;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private String estado; // ACTIVO, INACTIVO, EN_RUTA
    private String zonaCobertura;
    private List<Envio> enviosAsignados;

    public Repartidor(String idRepartidor, String nombre, String documento, String telefono, String zonaCobertura) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.zonaCobertura = zonaCobertura;
        this.estado = "INACTIVO";
        this.enviosAsignados = new ArrayList<>();
    }

    // Getters y Setters
    public String getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(String idRepartidor) { this.idRepartidor = idRepartidor; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getZonaCobertura() { return zonaCobertura; }
    public void setZonaCobertura(String zonaCobertura) { this.zonaCobertura = zonaCobertura; }
    public List<Envio> getEnviosAsignados() { return enviosAsignados; }
    public void setEnviosAsignados(List<Envio> enviosAsignados) { this.enviosAsignados = enviosAsignados; }

    @Override
    public String toString() {
        return nombre + " (" + documento + ") - " + estado + " - Zona: " + zonaCobertura;
    }
}


