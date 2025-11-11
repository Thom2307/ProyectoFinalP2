package model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Envio {
    private String idEnvio;
    private Usuario usuario;
    private Repartidor repartidor;
    private Direccion origen;
    private Direccion destino;
    private double peso; // en kg
    private double volumen; // en m³
    private double costo;
    private String estado; // SOLICITADO, ASIGNADO, EN_RUTA, ENTREGADO, INCIDENCIA
    private LocalDateTime fechaCreacion;
    private LocalDate fechaEstimadaEntrega;
    private List<ServicioAdicional> serviciosAdicionales;
    private Pago pago;
    private List<Incidencia> incidencias;

    public Envio(String idEnvio, Usuario usuario, Direccion origen, Direccion destino, 
                 double peso, double volumen, double costo) {
        this.idEnvio = idEnvio;
        this.usuario = usuario;
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.volumen = volumen;
        this.costo = costo;
        this.estado = "SOLICITADO";
        this.fechaCreacion = LocalDateTime.now();
        this.fechaEstimadaEntrega = LocalDate.now().plusDays(1);
        this.serviciosAdicionales = new ArrayList<>();
        this.incidencias = new ArrayList<>();
    }

    // Getters y Setters
    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Repartidor getRepartidor() { return repartidor; }
    public void setRepartidor(Repartidor repartidor) { this.repartidor = repartidor; }
    public Direccion getOrigen() { return origen; }
    public void setOrigen(Direccion origen) { this.origen = origen; }
    public Direccion getDestino() { return destino; }
    public void setDestino(Direccion destino) { this.destino = destino; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getVolumen() { return volumen; }
    public void setVolumen(double volumen) { this.volumen = volumen; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDate getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDate fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }
    public List<ServicioAdicional> getServiciosAdicionales() { return serviciosAdicionales; }
    public void setServiciosAdicionales(List<ServicioAdicional> serviciosAdicionales) { this.serviciosAdicionales = serviciosAdicionales; }
    public Pago getPago() { return pago; }
    public void setPago(Pago pago) { this.pago = pago; }
    public List<Incidencia> getIncidencias() { return incidencias; }
    public void setIncidencias(List<Incidencia> incidencias) { this.incidencias = incidencias; }

    // Métodos de utilidad
    public String getUsuarioNombre() { 
        return usuario != null ? usuario.getNombreCompleto() : "N/A"; 
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {
        this.serviciosAdicionales.add(servicio);
        this.costo += servicio.getCostoAdicional();
    }

    public void agregarIncidencia(Incidencia incidencia) {
        this.incidencias.add(incidencia);
        if (this.estado.equals("EN_RUTA") || this.estado.equals("ASIGNADO")) {
            this.estado = "INCIDENCIA";
        }
    }

    public boolean puedeCancelar() {
        return estado.equals("SOLICITADO");
    }
}
