package model;

import java.time.LocalDateTime;

public class Pago {
    private String idPago;
    private Envio envio;
    private double monto;
    private LocalDateTime fecha;
    private MetodoPago metodoPago;
    private String resultado; // APROBADO, RECHAZADO

    public Pago(String idPago, Envio envio, double monto, MetodoPago metodoPago, String resultado) {
        this.idPago = idPago;
        this.envio = envio;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.metodoPago = metodoPago;
        this.resultado = resultado;
    }

    // Getters y Setters
    public String getIdPago() { return idPago; }
    public void setIdPago(String idPago) { this.idPago = idPago; }
    public Envio getEnvio() { return envio; }
    public void setEnvio(Envio envio) { this.envio = envio; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    @Override
    public String toString() {
        return "Pago #" + idPago + " - $" + monto + " - " + resultado;
    }
}
