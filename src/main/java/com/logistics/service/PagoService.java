package com.logistics.service;

import com.logistics.model.dto.PagoDTO;
import com.logistics.model.entities.Pago;
import com.logistics.patterns.behavioral.strategy.PaymentStrategy;
import com.logistics.patterns.behavioral.strategy.PaymentData;
import com.logistics.patterns.behavioral.strategy.PaymentResult;
import com.logistics.patterns.creational.factory.PagoFactory;
import com.logistics.repository.PagoRepository;
import java.util.UUID;
import java.util.stream.Collectors;

public class PagoService {
    private PagoRepository repository = new PagoRepository();
    private PagoFactory factory = new PagoFactory();

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

    public java.util.List<PagoDTO> obtenerPagosPorEnvio(String idEnvio) {
        return repository.findByEnvio(idEnvio).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

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

