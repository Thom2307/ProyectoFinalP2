package com.logistics.service;

import com.logistics.model.dto.RepartidorDTO;
import com.logistics.model.entities.Repartidor;
import com.logistics.model.enums.DisponibilidadRepartidor;
import com.logistics.repository.RepartidorRepository;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de repartidores.
 * Proporciona operaciones CRUD para repartidores y gestión de su disponibilidad.
 */
public class RepartidorService {
    private RepartidorRepository repository = new RepartidorRepository();

    /**
     * Crea un nuevo repartidor en el sistema.
     * El repartidor se crea con estado DISPONIBLE por defecto.
     * 
     * @param nombre El nombre completo del repartidor
     * @param telefono El número de teléfono del repartidor
     * @param vehiculo El tipo de vehículo que utiliza el repartidor
     * @return DTO con la información del repartidor creado
     */
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

    /**
     * Actualiza la información de un repartidor existente.
     * 
     * @param id El identificador único del repartidor
     * @param nombre El nuevo nombre del repartidor
     * @param telefono El nuevo teléfono del repartidor
     * @param vehiculo El nuevo tipo de vehículo
     * @return DTO con la información actualizada del repartidor
     * @throws IllegalArgumentException Si el repartidor no existe
     */
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

    /**
     * Cambia la disponibilidad de un repartidor.
     * 
     * @param id El identificador único del repartidor
     * @param disponibilidad La nueva disponibilidad (DISPONIBLE, EN_RUTA, NO_DISPONIBLE)
     * @throws IllegalArgumentException Si el repartidor no existe
     */
    public void cambiarDisponibilidad(String id, DisponibilidadRepartidor disponibilidad) {
        Repartidor repartidor = repository.findById(id);
        if (repartidor == null) {
            throw new IllegalArgumentException("Repartidor no encontrado");
        }
        repartidor.setDisponibilidad(disponibilidad);
        repository.save(repartidor);
    }

    /**
     * Obtiene una lista de todos los repartidores registrados en el sistema.
     * 
     * @return Lista de DTOs con la información de todos los repartidores
     */
    public java.util.List<RepartidorDTO> listarRepartidores() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de todos los repartidores disponibles.
     * Útil para asignar envíos a repartidores que pueden realizar entregas.
     * 
     * @return Lista de DTOs de repartidores con disponibilidad DISPONIBLE
     */
    public java.util.List<RepartidorDTO> listarDisponibles() {
        return repository.findByDisponibilidad(DisponibilidadRepartidor.DISPONIBLE).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Elimina un repartidor del sistema.
     * 
     * @param id El identificador único del repartidor a eliminar
     */
    public void eliminarRepartidor(String id) {
        repository.delete(id);
    }

    /**
     * Obtiene la información de un repartidor por su identificador.
     * 
     * @param id El identificador único del repartidor
     * @return DTO con la información del repartidor, o null si no existe
     */
    public RepartidorDTO obtenerRepartidor(String id) {
        Repartidor repartidor = repository.findById(id);
        return repartidor != null ? toDTO(repartidor) : null;
    }

    /**
     * Convierte una entidad Repartidor a su DTO correspondiente.
     * 
     * @param repartidor La entidad Repartidor a convertir
     * @return DTO con la información del repartidor
     */
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

