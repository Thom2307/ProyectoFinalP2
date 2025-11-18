package com.logistics.model.entities;

import com.logistics.patterns.behavioral.state.EnvioState;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Envio {
    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double costo;
    private EnvioState estado;
    private LocalDateTime fechaCreacion;
    private Usuario usuario;
    private Repartidor repartidor;
    private List<String> adicionales = new ArrayList<>();

    public Envio(){}

    public String getIdEnvio(){ return idEnvio; }
    public void setIdEnvio(String id){ this.idEnvio = id; }
    public Direccion getOrigen(){ return origen; }
    public void setOrigen(Direccion o){ this.origen = o; }
    public Direccion getDestino(){ return destino; }
    public void setDestino(Direccion d){ this.destino = d; }
    public double getPeso(){ return peso; }
    public void setPeso(double p){ this.peso = p; }
    public double getCosto(){ return costo; }
    public void setCosto(double c){ this.costo = c; }
    public EnvioState getEstado(){ return estado; }
    public void setEstado(EnvioState s){ this.estado = s; }
    public LocalDateTime getFechaCreacion(){ return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime f){ this.fechaCreacion = f; }
    public Usuario getUsuario(){ return usuario; }
    public void setUsuario(Usuario u){ this.usuario = u; }
    public Repartidor getRepartidor(){ return repartidor; }
    public void setRepartidor(Repartidor r){ this.repartidor = r; }
    public List<String> getAdicionales(){ return adicionales; }
}
