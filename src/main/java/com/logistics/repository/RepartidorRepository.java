package com.logistics.repository;

import com.logistics.model.entities.Repartidor;
import com.logistics.model.enums.DisponibilidadRepartidor;
import com.logistics.patterns.creational.singleton.InMemoryDatabase;
import java.util.List;
import java.util.stream.Collectors;

public class RepartidorRepository {
    private InMemoryDatabase db = InMemoryDatabase.getInstance();

    public void save(Repartidor repartidor) {
        db.getRepartidores().put(repartidor.getIdRepartidor(), repartidor);
    }

    public Repartidor findById(String id) {
        return db.getRepartidores().get(id);
    }

    public List<Repartidor> findAll() {
        return db.getRepartidores().values().stream().collect(Collectors.toList());
    }

    public List<Repartidor> findByDisponibilidad(DisponibilidadRepartidor disponibilidad) {
        return db.getRepartidores().values().stream()
                .filter(r -> r.getDisponibilidad() == disponibilidad)
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        db.getRepartidores().remove(id);
    }
}

