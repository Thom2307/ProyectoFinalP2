package co.edu.uniquindio.poo.proyectofinalp2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Envio {
    public enum EstadoEnvio {
        SOLICITADO, ASIGNADO, EN_RUTA, ENTREGADO, INCIDENCIA, CANCELADO
    }

    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double volumen;
    private double costo;
    private EstadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private Usuario usuario;
    private Repartidor repartidor;
    private List<String> serviciosAdicionales;

    public Envio(Direccion origen, Direccion destino, double peso, double volumen, Usuario usuario) {
        this.idEnvio = UUID.randomUUID().toString();
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.volumen = volumen;
        this.usuario = usuario;
        this.estado = EstadoEnvio.SOLICITADO;
        this.fechaCreacion = LocalDateTime.now();
        this.serviciosAdicionales = new ArrayList<>();
    }

    // Getters y setters
    public String getIdEnvio() {
        return idEnvio;
    }
    public Direccion getOrigen() {
        return origen;
    }
    public void setOrigen(Direccion origen) {
        this.origen = origen;
    }
    public Direccion getDestino() {
        return destino;
    }
    public void setDestino(Direccion destino) { this.destino = destino; }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public double getVolumen() {
        return volumen;
    }
    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }
    public double getCosto() {
        return costo;
    }
    public void setCosto(double costo) {
        this.costo = costo;
    }
    public EstadoEnvio getEstado() {
        return estado;
    }
    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public LocalDateTime getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Repartidor getRepartidor() {
        return repartidor;
    }
    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }
    public List<String> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    // Métodos para servicios adicionales
    public void agregarServicioAdicional(String servicio) {
        serviciosAdicionales.add(servicio);
    }
    public void eliminarServicioAdicional(String servicio) {
        serviciosAdicionales.remove(servicio);
    }
}
