package service;

import model.*;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PagoService {
    private List<Pago> pagos;

    public PagoService() {
        this.pagos = new ArrayList<>();
    }

    public Pago procesarPago(Envio envio, MetodoPago metodoPago, double monto) {
        // Simulación de procesamiento de pago
        String resultado = simularProcesamientoPago(metodoPago, monto);
        
        String idPago = UUID.randomUUID().toString();
        Pago pago = new Pago(idPago, envio, monto, metodoPago, resultado);
        pagos.add(pago);
        envio.setPago(pago);
        
        return pago;
    }

    private String simularProcesamientoPago(MetodoPago metodoPago, double monto) {
        // Simulación: 90% de aprobación
        if (Math.random() > 0.1) {
            return "APROBADO";
        } else {
            return "RECHAZADO";
        }
    }

    public Pago buscarPorId(String idPago) {
        return pagos.stream()
            .filter(p -> p.getIdPago().equals(idPago))
            .findFirst()
            .orElse(null);
    }

    public List<Pago> listarPorEnvio(Envio envio) {
        return pagos.stream()
            .filter(p -> p.getEnvio().getIdEnvio().equals(envio.getIdEnvio()))
            .collect(Collectors.toList());
    }

    public List<Pago> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pagos.stream()
            .filter(p -> !p.getFecha().isBefore(fechaInicio) && !p.getFecha().isAfter(fechaFin))
            .collect(Collectors.toList());
    }

    public List<Pago> listarTodos() {
        return new ArrayList<>(pagos);
    }

    public double calcularIngresosPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return listarPorRangoFechas(fechaInicio, fechaFin).stream()
            .filter(p -> "APROBADO".equals(p.getResultado()))
            .mapToDouble(Pago::getMonto)
            .sum();
    }
}


