package com.logistics.service;

import com.logistics.model.dto.RepartidorDTO;
import com.logistics.model.entities.Repartidor;
import com.logistics.model.enums.DisponibilidadRepartidor;
import com.logistics.repository.RepartidorRepository;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepartidorService {
    private RepartidorRepository repository = new RepartidorRepository();

    public RepartidorDTO crearRepartidor(String nombre, String telefono, String vehiculo) {
        Repartidor repartidor = new Repartidor();
        repartidor.setIdRepartidor("r" + UUID.randomUUID().toString().substring(0, 8));
        repartidor.setNombre(nombre);
        repartidor.setTelefono(telefono);
        repartidor.setVehiculo(vehiculo);
        repartidor.setDisponibilidad(DisponibilidadRepartidor.DISPONIBLE);
        repository.save(repartidor);
        return toDTO(repartidor);
    }

    public RepartidorDTO actualizarRepartidor(String id, String nombre, String telefono, String vehiculo) {
        Repartidor repartidor = repository.findById(id);
        if (repartidor == null) {
            throw new IllegalArgumentException("Repartidor no encontrado");
        }
        repartidor.setNombre(nombre);
        repartidor.setTelefono(telefono);
        repartidor.setVehiculo(vehiculo);
        repository.save(repartidor);
        return toDTO(repartidor);
    }

    public void cambiarDisponibilidad(String id, DisponibilidadRepartidor disponibilidad) {
        Repartidor repartidor = repository.findById(id);
        if (repartidor == null) {
            throw new IllegalArgumentException("Repartidor no encontrado");
        }
        repartidor.setDisponibilidad(disponibilidad);
        repository.save(repartidor);
    }

    public java.util.List<RepartidorDTO> listarRepartidores() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public java.util.List<RepartidorDTO> listarDisponibles() {
        return repository.findByDisponibilidad(DisponibilidadRepartidor.DISPONIBLE).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void eliminarRepartidor(String id) {
        repository.delete(id);
    }

    public RepartidorDTO obtenerRepartidor(String id) {
        Repartidor repartidor = repository.findById(id);
        return repartidor != null ? toDTO(repartidor) : null;
    }

    private RepartidorDTO toDTO(Repartidor repartidor) {
        RepartidorDTO dto = new RepartidorDTO();
        dto.setIdRepartidor(repartidor.getIdRepartidor());
        dto.setNombre(repartidor.getNombre());
        dto.setTelefono(repartidor.getTelefono());
        dto.setVehiculo(repartidor.getVehiculo());
        dto.setDisponibilidad(repartidor.getDisponibilidad());
        dto.setEnviosAsignados(repartidor.getEnviosAsignados().size());
        return dto;
    }
}

