package com.logistics.repository;

import com.logistics.model.entities.Pago;
import com.logistics.patterns.creational.singleton.InMemoryDatabase;
import java.util.List;
import java.util.stream.Collectors;

public class PagoRepository {
    private InMemoryDatabase db = InMemoryDatabase.getInstance();

    public void save(Pago pago) {
        db.getPagos().put(pago.getIdPago(), pago);
    }

    public Pago findById(String id) {
        return db.getPagos().get(id);
    }

    public List<Pago> findByEnvio(String idEnvio) {
        return db.getPagos().values().stream()
                .filter(p -> p.getIdEnvio().equals(idEnvio))
                .collect(Collectors.toList());
    }

    public List<Pago> findAll() {
        return db.getPagos().values().stream().collect(Collectors.toList());
    }
}

