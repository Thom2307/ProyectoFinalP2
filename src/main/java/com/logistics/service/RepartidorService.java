package com.logistics.service;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.RepartidorDTO;
import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;
import com.logistics.model.enums.EstadoEnvio;
import com.logistics.model.enums.EstadoRepartidor;
import com.logistics.repository.EnvioRepository;
import com.logistics.repository.RepartidorRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de repartidores.
 * Proporciona operaciones CRUD para repartidores y gestión de su disponibilidad.
 */
public class RepartidorService {
    private RepartidorRepository repository = new RepartidorRepository();
    private EnvioRepository envioRepository = new EnvioRepository();
    private static final int MAX_ENVIOS_POR_REPARTIDOR = 5; // Límite configurable

    // ==================== MÉTODOS DE GESTIÓN ====================

    public RepartidorDTO crearRepartidor(RepartidorDTO dto) {
        // Validar datos
        validarDatosRepartidor(dto);
        
        // Validar documento único
        if (!validarDocumentoUnico(dto.getDocumento())) {
            throw new IllegalArgumentException("El documento ya está registrado en el sistema");
        }

        Repartidor repartidor = dto.toEntity();
        if (repartidor.getIdRepartidor() == null || repartidor.getIdRepartidor().isEmpty()) {
            repartidor.setIdRepartidor("REP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (repartidor.getDisponibilidad() == null) {
            repartidor.setDisponibilidad(EstadoRepartidor.ACTIVO);
        }

        repository.crear(repartidor);
        return RepartidorDTO.fromEntity(repartidor);
    }

    public RepartidorDTO actualizarRepartidor(RepartidorDTO dto) {
        if (dto.getIdRepartidor() == null || dto.getIdRepartidor().isEmpty()) {
            throw new IllegalArgumentException("ID de repartidor requerido para actualizar");
        }

        Repartidor existente = repository.buscarPorId(dto.getIdRepartidor());
        if (existente == null) {
            throw new IllegalArgumentException("Repartidor no encontrado");
        }

        // Validar documento único (excepto si es el mismo repartidor)
        if (!existente.getDocumento().equals(dto.getDocumento())) {
            if (!validarDocumentoUnico(dto.getDocumento())) {
                throw new IllegalArgumentException("El documento ya está registrado en el sistema");
            }
        }

        validarDatosRepartidor(dto);

        // Actualizar campos
        existente.setNombre(dto.getNombre());
        existente.setDocumento(dto.getDocumento());
        existente.setTelefono(dto.getTelefono());
        existente.setVehiculo(dto.getVehiculo());
        existente.setZonaCobertura(dto.getZonaCobertura());
        if (dto.getDisponibilidad() != null) {
            existente.setDisponibilidad(dto.getDisponibilidad());
        }

        repository.actualizar(existente);
        return RepartidorDTO.fromEntity(existente);
    }

    public void eliminarRepartidor(String idRepartidor) {
        if (!puedeEliminar(idRepartidor)) {
            throw new IllegalStateException("No se puede eliminar: el repartidor tiene envíos activos");
        }

        repository.eliminar(idRepartidor);
    }

    public RepartidorDTO obtenerRepartidor(String idRepartidor) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        return repartidor != null ? RepartidorDTO.fromEntity(repartidor) : null;
    }

    public List<RepartidorDTO> listarTodosRepartidores() {
        return repository.listarTodos().stream()
                .map(RepartidorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // ==================== MÉTODOS DE DISPONIBILIDAD ====================

    public void cambiarDisponibilidad(String idRepartidor, EstadoRepartidor nuevoEstado) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        if (repartidor == null) {
            throw new IllegalArgumentException("Repartidor no encontrado");
        }

        // Si se cambia a INACTIVO y tiene envíos EN_RUTA, liberarlos
        if (nuevoEstado == EstadoRepartidor.INACTIVO) {
            List<Envio> enviosEnRuta = repartidor.getEnviosAsignados().stream()
                    .filter(e -> e.getEstado() != null && 
                            (e.getEstado().name().equals("EN_RUTA") || 
                             e.getEstado().name().equals("ASIGNADO")))
                    .collect(Collectors.toList());
            
            if (!enviosEnRuta.isEmpty()) {
                // Liberar envíos
                for (Envio envio : enviosEnRuta) {
                    envio.setRepartidor(null);
                    repartidor.getEnviosAsignados().remove(envio);
                    envioRepository.save(envio);
                }
            }
        }

        repartidor.setDisponibilidad(nuevoEstado);
        repository.actualizar(repartidor);
    }

    public void activarRepartidor(String idRepartidor) {
        cambiarDisponibilidad(idRepartidor, EstadoRepartidor.ACTIVO);
    }

    public void desactivarRepartidor(String idRepartidor) {
        cambiarDisponibilidad(idRepartidor, EstadoRepartidor.INACTIVO);
    }

    public void marcarEnRuta(String idRepartidor) {
        cambiarDisponibilidad(idRepartidor, EstadoRepartidor.EN_RUTA);
    }

    // ==================== MÉTODOS DE CONSULTA ====================

    public List<EnvioDTO> consultarEnviosAsignados(String idRepartidor) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        if (repartidor == null) {
            throw new IllegalArgumentException("Repartidor no encontrado");
        }

        return repartidor.getEnviosAsignados().stream()
                .map(this::envioToDTO)
                .collect(Collectors.toList());
    }

    public RepartidorDTO buscarDisponiblePorZona(String zona) {
        List<Repartidor> disponibles = repository.buscarPorZona(zona).stream()
                .filter(r -> r.getDisponibilidad() == EstadoRepartidor.ACTIVO)
                .collect(Collectors.toList());

        if (disponibles.isEmpty()) {
            return null;
        }

        // Retornar el que tiene menos envíos asignados
        Repartidor optimo = disponibles.stream()
                .min((r1, r2) -> Integer.compare(
                    r1.getEnviosAsignados() != null ? r1.getEnviosAsignados().size() : 0,
                    r2.getEnviosAsignados() != null ? r2.getEnviosAsignados().size() : 0))
                .orElse(disponibles.get(0));

        return RepartidorDTO.fromEntity(optimo);
    }

    /**
     * Busca un repartidor disponible que pueda atender un municipio específico
     */
    public RepartidorDTO buscarDisponiblePorMunicipio(String municipio) {
        List<Repartidor> disponibles = repository.buscarDisponibles().stream()
                .filter(r -> r.puedeAtenderMunicipio(municipio))
                .collect(Collectors.toList());

        if (disponibles.isEmpty()) {
            return null;
        }

        // Retornar el que tiene menos envíos asignados
        Repartidor optimo = disponibles.stream()
                .min((r1, r2) -> Integer.compare(
                    r1.getEnviosAsignados() != null ? r1.getEnviosAsignados().size() : 0,
                    r2.getEnviosAsignados() != null ? r2.getEnviosAsignados().size() : 0))
                .orElse(disponibles.get(0));

        return RepartidorDTO.fromEntity(optimo);
    }

    public List<RepartidorDTO> obtenerRepartidoresActivos() {
        return repository.findByDisponibilidad(EstadoRepartidor.ACTIVO).stream()
                .map(RepartidorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<RepartidorDTO> obtenerRepartidoresInactivos() {
        return repository.findByDisponibilidad(EstadoRepartidor.INACTIVO).stream()
                .map(RepartidorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<RepartidorDTO> obtenerRepartidoresEnRuta() {
        return repository.findByDisponibilidad(EstadoRepartidor.EN_RUTA).stream()
                .map(RepartidorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // ==================== MÉTODOS DE VALIDACIÓN ====================

    public void validarDatosRepartidor(RepartidorDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty() || dto.getNombre().length() < 3) {
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
        }
        if (!dto.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras");
        }
        if (dto.getDocumento() == null || dto.getDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
        if (!dto.getDocumento().matches("^[0-9]+$")) {
            throw new IllegalArgumentException("El documento debe contener solo números");
        }
        if (dto.getTelefono() == null || dto.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        if (!dto.getTelefono().matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("El teléfono debe tener 10 dígitos");
        }
        if (dto.getZonaCobertura() == null || dto.getZonaCobertura().trim().isEmpty()) {
            throw new IllegalArgumentException("La zona de cobertura es obligatoria");
        }
    }

    public boolean validarDocumentoUnico(String documento) {
        List<Repartidor> existentes = repository.buscarPorDocumento(documento);
        return existentes.isEmpty();
    }

    public boolean puedeEliminar(String idRepartidor) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        if (repartidor == null) {
            return false;
        }

        // Verificar si tiene envíos activos (ASIGNADO o EN_RUTA)
        return repartidor.getEnviosAsignados().stream()
                .noneMatch(e -> {
                    String estado = e.getEstado() != null ? e.getEstado().name() : "";
                    return estado.equals("ASIGNADO") || estado.equals("EN_RUTA");
                });
    }

    public boolean puedeAsignarEnvio(String idRepartidor) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        if (repartidor == null) {
            return false;
        }

        // Debe estar ACTIVO y no tener más del límite de envíos
        return repartidor.getDisponibilidad() == EstadoRepartidor.ACTIVO &&
               (repartidor.getEnviosAsignados() == null || 
                repartidor.getEnviosAsignados().size() < MAX_ENVIOS_POR_REPARTIDOR);
    }

    // ==================== MÉTODOS DE ASIGNACIÓN ====================

    public void asignarEnvio(String idRepartidor, String idEnvio) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        Envio envio = envioRepository.findById(idEnvio);

        if (repartidor == null) {
            throw new IllegalArgumentException("Repartidor no encontrado");
        }
        if (envio == null) {
            throw new IllegalArgumentException("Envío no encontrado");
        }
        if (!puedeAsignarEnvio(idRepartidor)) {
            throw new IllegalStateException("El repartidor no está disponible para asignación");
        }
        if (envio.getEstado() == null || !envio.getEstado().name().equals("SOLICITADO")) {
            throw new IllegalStateException("Solo se pueden asignar envíos en estado SOLICITADO");
        }

        // Verificar que el municipio del destino esté en la ruta del repartidor
        if (envio.getDestino() != null && envio.getDestino().getCiudad() != null) {
            String municipioDestino = envio.getDestino().getCiudad();
            if (repartidor.getRuta() == null) {
                throw new IllegalStateException("El repartidor no tiene una ruta asignada");
            }
            if (!repartidor.puedeAtenderMunicipio(municipioDestino)) {
                throw new IllegalStateException(
                    "El repartidor no puede atender el municipio " + municipioDestino + 
                    ". Su ruta asignada es: " + repartidor.getRuta().getNombre()
                );
            }
        }

        // Asignar envío
        envio.setRepartidor(repartidor);
        repartidor.getEnviosAsignados().add(envio);
        
        // Cambiar estado del envío usando el patrón State
        envio.getEstado().asignar(envio, repartidor);

        // Actualizar disponibilidad del repartidor
        if (repartidor.getDisponibilidad() == EstadoRepartidor.ACTIVO) {
            repartidor.setDisponibilidad(EstadoRepartidor.EN_RUTA);
        }

        envioRepository.save(envio);
        repository.actualizar(repartidor);
    }

    public void liberarEnvio(String idRepartidor, String idEnvio) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        Envio envio = envioRepository.findById(idEnvio);

        if (repartidor == null || envio == null) {
            throw new IllegalArgumentException("Repartidor o envío no encontrado");
        }

        repartidor.getEnviosAsignados().remove(envio);
        envio.setRepartidor(null);

        // Si no tiene más envíos, volver a ACTIVO
        if (repartidor.getEnviosAsignados().isEmpty() && 
            repartidor.getDisponibilidad() == EstadoRepartidor.EN_RUTA) {
            repartidor.setDisponibilidad(EstadoRepartidor.ACTIVO);
        }

        envioRepository.save(envio);
        repository.actualizar(repartidor);
    }

    public RepartidorDTO obtenerRepartidorOptimo(String zona) {
        return buscarDisponiblePorZona(zona);
    }

    /**
     * Obtiene el repartidor óptimo para un municipio específico
     */
    public RepartidorDTO obtenerRepartidorOptimoPorMunicipio(String municipio) {
        return buscarDisponiblePorMunicipio(municipio);
    }

    // ==================== MÉTODOS DE ESTADÍSTICAS ====================

    public int contarEnviosPorRepartidor(String idRepartidor) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        return repartidor != null && repartidor.getEnviosAsignados() != null ? 
               repartidor.getEnviosAsignados().size() : 0;
    }

    public double calcularTasaEntrega(String idRepartidor) {
        Repartidor repartidor = repository.buscarPorId(idRepartidor);
        if (repartidor == null || repartidor.getEnviosAsignados().isEmpty()) {
            return 0.0;
        }

        long entregados = repartidor.getEnviosAsignados().stream()
                .filter(e -> e.getEstado() != null && e.getEstado().name().equals("ENTREGADO"))
                .count();

        return (double) entregados / repartidor.getEnviosAsignados().size() * 100.0;
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private EnvioDTO envioToDTO(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setIdEnvio(envio.getIdEnvio());
        if (envio.getOrigen() != null) {
            dto.setOrigen(convertirDireccion(envio.getOrigen()));
        }
        if (envio.getDestino() != null) {
            dto.setDestino(convertirDireccion(envio.getDestino()));
        }
        dto.setPeso(envio.getPeso());
        dto.setCosto(envio.getCosto());
        if (envio.getEstado() != null) {
            try {
                dto.setEstado(EstadoEnvio.valueOf(envio.getEstado().name()));
            } catch (IllegalArgumentException e) {
                dto.setEstado(EstadoEnvio.SOLICITADO);
            }
        }
        dto.setFechaCreacion(envio.getFechaCreacion());
        if (envio.getUsuario() != null) {
            dto.setIdUsuario(envio.getUsuario().getIdUsuario());
            dto.setNombreUsuario(envio.getUsuario().getNombre());
        }
        return dto;
    }

    private com.logistics.model.dto.DireccionDTO convertirDireccion(com.logistics.model.entities.Direccion direccion) {
        if (direccion == null) return null;
        com.logistics.model.dto.DireccionDTO dto = new com.logistics.model.dto.DireccionDTO();
        dto.setIdDireccion(direccion.getIdDireccion());
        dto.setAlias(direccion.getAlias());
        dto.setCalle(direccion.getCalle());
        dto.setCiudad(direccion.getCiudad());
        dto.setLat(direccion.getLat());
        dto.setLon(direccion.getLon());
        return dto;
    }

    // ==================== MÉTODOS DE COMPATIBILIDAD ====================

    public RepartidorDTO crearRepartidor(String nombre, String telefono, String vehiculo) {
        RepartidorDTO dto = new RepartidorDTO();
        dto.setNombre(nombre);
        dto.setTelefono(telefono);
        dto.setVehiculo(vehiculo);
        dto.setDisponibilidad(EstadoRepartidor.ACTIVO);
        dto.setZonaCobertura("Centro"); // Valor por defecto
        dto.setDocumento("0000000000"); // Valor por defecto temporal
        return crearRepartidor(dto);
    }

    public List<RepartidorDTO> listarRepartidores() {
        return listarTodosRepartidores();
    }

    public List<RepartidorDTO> listarDisponibles() {
        return obtenerRepartidoresActivos();
    }

    // Método de compatibilidad ya implementado arriba
}
