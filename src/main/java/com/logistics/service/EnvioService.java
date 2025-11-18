package com.logistics.service;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.DireccionDTO;
import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.repository.EnvioRepository;
import com.logistics.repository.RepartidorRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EnvioService {
    private EnvioRepository repository = new EnvioRepository();
    private RepartidorRepository repartidorRepository = new RepartidorRepository();

    public void save(Envio envio) {
        repository.save(envio);
        // Guardar en archivo .txt
        com.logistics.util.EnvioFileManager.guardarEnvio(envio);
    }

    public EnvioDTO obtenerEnvio(String idEnvio) {
        Envio envio = repository.findById(idEnvio);
        return envio != null ? toDTO(envio) : null;
    }

    public void asignarRepartidor(String idEnvio, String idRepartidor) {
        Envio envio = repository.findById(idEnvio);
        Repartidor repartidor = repartidorRepository.findById(idRepartidor);
        if (envio == null || repartidor == null) {
            throw new IllegalArgumentException("Envío o repartidor no encontrado");
        }
        envio.getEstado().asignar(envio, repartidor);
        envio.setRepartidor(repartidor);
        repartidor.getEnviosAsignados().add(envio);
        if (repartidor.getDisponibilidad() == com.logistics.model.enums.DisponibilidadRepartidor.DISPONIBLE) {
            repartidor.setDisponibilidad(com.logistics.model.enums.DisponibilidadRepartidor.EN_RUTA);
        }
        repository.save(envio);
        repartidorRepository.save(repartidor);
    }

    public void cambiarEstado(String idEnvio, String accion) {
        Envio envio = repository.findById(idEnvio);
        if (envio == null) {
            throw new IllegalArgumentException("Envío no encontrado");
        }
        switch (accion.toUpperCase()) {
            case "EN_RUTA":
                envio.getEstado().marcarEnRuta(envio);
                break;
            case "ENTREGADO":
                envio.getEstado().marcarEntregado(envio);
                break;
            case "CANCELAR":
                envio.getEstado().cancelar(envio);
                break;
            case "ASIGNADO":
                // Cambiar a estado ASIGNADO desde SOLICITADO
                if (envio.getEstado().name().equals("SOLICITADO")) {
                    envio.setEstado(new com.logistics.patterns.behavioral.state.AsignadoState());
                } else {
                    throw new IllegalArgumentException("Solo se puede asignar un envío desde estado SOLICITADO");
                }
                break;
            default:
                throw new IllegalArgumentException("Acción no válida: " + accion);
        }
        repository.save(envio);
        // Actualizar en archivo .txt
        com.logistics.util.EnvioFileManager.guardarEnvio(envio);
    }

    public void reportarIncidencia(String idEnvio, String descripcion) {
        Envio envio = repository.findById(idEnvio);
        if (envio == null) {
            throw new IllegalArgumentException("Envío no encontrado");
        }
        // En una implementación real, se guardaría la incidencia
        System.out.println("Incidencia reportada para envío " + idEnvio + ": " + descripcion);
    }

    public List<EnvioDTO> filtrarPorUsuarioYFecha(String idUsuario, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return repository.findAll().stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().getIdUsuario().equals(idUsuario))
                .filter(e -> fechaInicio == null || e.getFechaCreacion().isAfter(fechaInicio))
                .filter(e -> fechaFin == null || e.getFechaCreacion().isBefore(fechaFin))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EnvioDTO> listarEnvios() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<EnvioDTO> listarEnviosSinAsignar() {
        return repository.findAll().stream()
                .filter(e -> e.getRepartidor() == null)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private EnvioDTO toDTO(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setIdEnvio(envio.getIdEnvio());
        dto.setOrigen(toDireccionDTO(envio.getOrigen()));
        dto.setDestino(toDireccionDTO(envio.getDestino()));
        dto.setPeso(envio.getPeso());
        dto.setCosto(envio.getCosto());
        dto.setEstado(convertirEstado(envio.getEstado()));
        dto.setFechaCreacion(envio.getFechaCreacion());
        if (envio.getUsuario() != null) {
            dto.setIdUsuario(envio.getUsuario().getIdUsuario());
            dto.setNombreUsuario(envio.getUsuario().getNombre());
        }
        if (envio.getRepartidor() != null) {
            dto.setIdRepartidor(envio.getRepartidor().getIdRepartidor());
            dto.setNombreRepartidor(envio.getRepartidor().getNombre());
        }
        dto.setAdicionales(envio.getAdicionales());
        return dto;
    }

    private DireccionDTO toDireccionDTO(com.logistics.model.entities.Direccion direccion) {
        if (direccion == null) return null;
        DireccionDTO dto = new DireccionDTO();
        dto.setIdDireccion(direccion.getIdDireccion());
        dto.setAlias(direccion.getAlias());
        dto.setCalle(direccion.getCalle());
        dto.setCiudad(direccion.getCiudad());
        dto.setLat(direccion.getLat());
        dto.setLon(direccion.getLon());
        return dto;
    }

    private EstadoEnvio convertirEstado(com.logistics.patterns.behavioral.state.EnvioState estado) {
        if (estado == null) return EstadoEnvio.SOLICITADO;
        String name = estado.name();
        try {
            return EstadoEnvio.valueOf(name);
        } catch (IllegalArgumentException e) {
            return EstadoEnvio.SOLICITADO;
        }
    }
}
