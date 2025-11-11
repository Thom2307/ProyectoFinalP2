package model;

import java.time.LocalDateTime;

public class Incidencia {
    private String idIncidencia;
    private Envio envio;
    private String tipo; // RETRASO, PAQUETE_DANADO, DIRECCION_INCORRECTA, RECHAZO_ENTREGA
    private String descripcion;
    private LocalDateTime fecha;
    private String estado; // PENDIENTE, RESUELTA

    public Incidencia(String idIncidencia, Envio envio, String tipo, String descripcion) {
        this.idIncidencia = idIncidencia;
        this.envio = envio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    // Getters y Setters
    public String getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(String idIncidencia) { this.idIncidencia = idIncidencia; }
    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return tipo + " - " + descripcion + " (" + estado + ")";
    }
}
