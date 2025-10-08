package co.edu.uniquindio.poo.proyectofinalp2.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Pago {
    public enum ResultadoPago {
        APROBADO, RECHAZADO
    }

    private String idPago;
    private double monto;
    private LocalDateTime fecha;
    private String metodoPago; // Simulado
    private ResultadoPago resultado;

    public Pago(double monto, String metodoPago, ResultadoPago resultado) {
        this.idPago = UUID.randomUUID().toString();
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.metodoPago = metodoPago;
        this.resultado = resultado;
    }

    // Getters y setters
    public String getIdPago() {
        return idPago;
    }

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public ResultadoPago getResultado() {
        return resultado;
    }
    public void setResultado(ResultadoPago resultado) {
        this.resultado = resultado;
    }
}
