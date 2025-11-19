package com.logistics.service;

import com.logistics.model.dto.PagoDTO;
import com.logistics.model.entities.Pago;
import com.logistics.patterns.comportamiento.strategy.PaymentStrategy;
import com.logistics.patterns.comportamiento.strategy.PaymentData;
import com.logistics.patterns.comportamiento.strategy.PaymentResult;
import com.logistics.patterns.creacional.factory.PagoFactory;
import com.logistics.repository.PagoRepository;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de pagos.
 * Procesa pagos usando el patrón Strategy para diferentes métodos de pago.
 */
public class PagoService {
    private PagoRepository repository = new PagoRepository();
    private PagoFactory factory = new PagoFactory();

    /**
     * Procesa un pago para un envío usando el método de pago especificado.
     * Utiliza el patrón Strategy para procesar el pago según el método seleccionado.
     * 
     * @param idEnvio El identificador único del envío
     * @param monto El monto a pagar
     * @param metodoPago El método de pago (TARJETA, PSE, etc.)
     * @return DTO con la información del pago procesado
     */
    public PagoDTO procesarPago(String idEnvio, double monto, String metodoPago) {
        PaymentStrategy strategy = factory.crearEstrategia(metodoPago);
        PaymentData data = new PaymentData();
        data.idEnvio = idEnvio;
        data.monto = monto;
        data.details = "Pago de envío " + idEnvio;

        PaymentResult result = strategy.pay(data);

        Pago pago = new Pago();
        pago.setIdPago("p" + UUID.randomUUID().toString().substring(0, 8));
        pago.setIdEnvio(idEnvio);
        pago.setMonto(monto);
        pago.setMetodoPago(metodoPago);
        pago.setResultado(result);

        repository.save(pago);
        return toDTO(pago);
    }

    /**
     * Obtiene todos los pagos asociados a un envío específico.
     * 
     * @param idEnvio El identificador único del envío
     * @return Lista de DTOs con todos los pagos del envío
     */
    public java.util.List<PagoDTO> obtenerPagosPorEnvio(String idEnvio) {
        return repository.findByEnvio(idEnvio).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Pago a su DTO correspondiente.
     * 
     * @param pago La entidad Pago a convertir
     * @return DTO con la información del pago
     */
    private PagoDTO toDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(pago.getIdPago());
        dto.setIdEnvio(pago.getIdEnvio());
        dto.setMonto(pago.getMonto());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setAprobado(pago.getResultado() != null && pago.getResultado().approved);
        dto.setTransactionId(pago.getResultado() != null ? pago.getResultado().transactionId : null);
        dto.setFechaPago(pago.getFechaPago());
        return dto;
    }
}

