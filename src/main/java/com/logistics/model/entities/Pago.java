package com.logistics.model.entities;

import com.logistics.patterns.behavioral.strategy.PaymentResult;
import java.time.LocalDateTime;

public class Pago {
    private String idPago;
    private String idEnvio;
    private double monto;
    private String metodoPago;
    private PaymentResult resultado;
    private LocalDateTime fechaPago;

    public Pago() {}

    public Pago(String idPago, String idEnvio, double monto, String metodoPago) {
        this.idPago = idPago;
        this.idEnvio = idEnvio;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.fechaPago = LocalDateTime.now();
    }

    public String getIdPago() { return idPago; }
    public void setIdPago(String idPago) { this.idPago = idPago; }
    public String getIdEnvio() { return idEnvio; }
    public void setIdEnvio(String idEnvio) { this.idEnvio = idEnvio; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public PaymentResult getResultado() { return resultado; }
    public void setResultado(PaymentResult resultado) { this.resultado = resultado; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}

