package com.logistics.repository;

import com.logistics.model.entities.Repartidor;
import com.logistics.model.enums.EstadoRepartidor;
import com.logistics.patterns.creacional.singleton.InMemoryDatabase;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RepartidorRepository {
    private InMemoryDatabase db = InMemoryDatabase.getInstance();

    // Métodos CRUD básicos
    public void crear(Repartidor repartidor) {
        db.getRepartidores().put(repartidor.getIdRepartidor(), repartidor);
    }

    public void actualizar(Repartidor repartidor) {
        db.getRepartidores().put(repartidor.getIdRepartidor(), repartidor);
    }

    public void eliminar(String idRepartidor) {
        db.getRepartidores().remove(idRepartidor);
    }

    public Repartidor buscarPorId(String idRepartidor) {
        return db.getRepartidores().get(idRepartidor);
    }

    public List<Repartidor> listarTodos() {
        return db.getRepartidores().values().stream().collect(Collectors.toList());
    }

    // Métodos específicos de búsqueda
    public List<Repartidor> buscarPorZona(String zona) {
        return db.getRepartidores().values().stream()
                .filter(r -> r.getZonaCobertura() != null && 
                            r.getZonaCobertura().equalsIgnoreCase(zona))
                .collect(Collectors.toList());
    }

    public List<Repartidor> buscarDisponibles() {
        return db.getRepartidores().values().stream()
                .filter(r -> r.getDisponibilidad() == EstadoRepartidor.ACTIVO)
                .collect(Collectors.toList());
    }

    public List<Repartidor> buscarPorDocumento(String documento) {
        return db.getRepartidores().values().stream()
                .filter(r -> r.getDocumento() != null && 
                            r.getDocumento().equals(documento))
                .collect(Collectors.toList());
    }

    public List<Repartidor> buscarPorNombre(String nombre) {
        String nombreLower = nombre.toLowerCase();
        return db.getRepartidores().values().stream()
                .filter(r -> r.getNombre() != null && 
                            r.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
    }

    public long contarPorEstado(EstadoRepartidor estado) {
        return db.getRepartidores().values().stream()
                .filter(r -> r.getDisponibilidad() == estado)
                .count();
    }

    public Repartidor obtenerRepartidorConMenosEnvios() {
        return db.getRepartidores().values().stream()
                .filter(r -> r.getDisponibilidad() == EstadoRepartidor.ACTIVO)
                .min(Comparator.comparingInt(r -> 
                    r.getEnviosAsignados() != null ? r.getEnviosAsignados().size() : 0))
                .orElse(null);
    }

    // Métodos de compatibilidad (mantener para código existente)
    public void save(Repartidor repartidor) {
        crear(repartidor);
    }

    public Repartidor findById(String id) {
        return buscarPorId(id);
    }

    public List<Repartidor> findAll() {
        return listarTodos();
    }

    public List<Repartidor> findByDisponibilidad(EstadoRepartidor disponibilidad) {
        return db.getRepartidores().values().stream()
                .filter(r -> r.getDisponibilidad() == disponibilidad)
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        eliminar(id);
    }
}

