package co.edu.uniquindio.poo.proyectofinalp2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repartidor {
    public enum EstadoDisponibilidad {
        ACTIVO, INACTIVO, EN_RUTA
    }

    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoDisponibilidad estado;
    private String zonaCobertura;
    private List<Envio> enviosAsignados;

    public Repartidor(String nombre, String documento, String telefono, String zonaCobertura) {
        this.idRepartidor = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.estado = EstadoDisponibilidad.INACTIVO;
        this.zonaCobertura = zonaCobertura;
        this.enviosAsignados = new ArrayList<>();
    }

    // Getters y setters
    public String getIdRepartidor() {
        return idRepartidor;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public EstadoDisponibilidad getEstado() {
        return estado;
    }
    public void setEstado(EstadoDisponibilidad estado) {
        this.estado = estado;
    }
    public String getZonaCobertura() {
        return zonaCobertura;
    }
    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
    }
    public List<Envio> getEnviosAsignados() {
        return enviosAsignados;
    }

    // Métodos para gestionar envíos
    public void asignarEnvio(Envio envio) {
        enviosAsignados.add(envio);
    }
    public void eliminarEnvio(Envio envio) {
        enviosAsignados.remove(envio);
    }
}
