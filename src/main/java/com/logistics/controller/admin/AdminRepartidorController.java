package com.logistics.controller.admin;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.RepartidorDTO;
import com.logistics.model.enums.EstadoRepartidor;
import com.logistics.service.EnvioService;
import com.logistics.service.RepartidorService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para la gestión de repartidores desde la vista de administrador
 */
public class AdminRepartidorController {
    private RepartidorService repartidorService;
    private EnvioService envioService;

    public AdminRepartidorController() {
        this.repartidorService = new RepartidorService();
        this.envioService = new EnvioService();
    }

    // ==================== GESTIÓN CRUD ====================

    public RepartidorDTO crearRepartidorDesdeVista(RepartidorDTO dto) {
        return repartidorService.crearRepartidor(dto);
    }

    public RepartidorDTO actualizarRepartidorDesdeVista(RepartidorDTO dto) {
        return repartidorService.actualizarRepartidor(dto);
    }

    public void eliminarRepartidorDesdeVista(String idRepartidor) {
        if (!repartidorService.puedeEliminar(idRepartidor)) {
            throw new IllegalStateException("No se puede eliminar: el repartidor tiene envíos activos");
        }
        repartidorService.eliminarRepartidor(idRepartidor);
    }

    public RepartidorDTO cargarRepartidorEnFormulario(String idRepartidor) {
        return repartidorService.obtenerRepartidor(idRepartidor);
    }

    // ==================== VISUALIZACIÓN DE DATOS ====================

    public List<RepartidorDTO> cargarTablaRepartidores() {
        return repartidorService.listarTodosRepartidores();
    }

    public void actualizarTablaRepartidores() {
        // La tabla se actualiza automáticamente al llamar cargarTablaRepartidores()
    }

    public List<RepartidorDTO> cargarComboBoxRepartidores() {
        return repartidorService.obtenerRepartidoresActivos();
    }

    public List<String> cargarComboBoxZonas() {
        return List.of("Norte", "Sur", "Este", "Oeste", "Centro");
    }

    // ==================== FILTROS Y BÚSQUEDAS ====================

    public List<RepartidorDTO> filtrarRepartidoresPorEstado(EstadoRepartidor estado) {
        List<RepartidorDTO> todos = repartidorService.listarTodosRepartidores();
        return todos.stream()
                .filter(r -> r.getDisponibilidad() == estado)
                .collect(Collectors.toList());
    }

    public List<RepartidorDTO> filtrarRepartidoresPorZona(String zona) {
        List<RepartidorDTO> todos = repartidorService.listarTodosRepartidores();
        return todos.stream()
                .filter(r -> r.getZonaCobertura() != null && 
                            r.getZonaCobertura().equalsIgnoreCase(zona))
                .collect(Collectors.toList());
    }

    public List<RepartidorDTO> buscarRepartidorPorNombre(String nombre) {
        List<RepartidorDTO> todos = repartidorService.listarTodosRepartidores();
        String nombreLower = nombre.toLowerCase();
        return todos.stream()
                .filter(r -> r.getNombre() != null && 
                            r.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
    }

    public List<RepartidorDTO> buscarRepartidorPorDocumento(String documento) {
        List<RepartidorDTO> todos = repartidorService.listarTodosRepartidores();
        return todos.stream()
                .filter(r -> r.getDocumento() != null && 
                            r.getDocumento().equals(documento))
                .collect(Collectors.toList());
    }

    // ==================== GESTIÓN DE DISPONIBILIDAD ====================

    public void cambiarDisponibilidadRepartidor(String idRepartidor, EstadoRepartidor estado) {
        repartidorService.cambiarDisponibilidad(idRepartidor, estado);
    }

    public void activarRepartidorDesdeVista(String idRepartidor) {
        repartidorService.activarRepartidor(idRepartidor);
    }

    public void desactivarRepartidorDesdeVista(String idRepartidor) {
        repartidorService.desactivarRepartidor(idRepartidor);
    }

    // ==================== ASIGNACIÓN DE ENVÍOS ====================

    public List<EnvioDTO> mostrarEnviosDisponiblesParaAsignar() {
        return envioService.listarEnviosSinAsignar();
    }

    public List<RepartidorDTO> mostrarRepartidoresDisponibles() {
        return repartidorService.obtenerRepartidoresActivos();
    }

    public void asignarEnvioARepartidor(String idEnvio, String idRepartidor) {
        // Validar que el repartidor puede asignar
        if (!repartidorService.puedeAsignarEnvio(idRepartidor)) {
            throw new IllegalStateException("El repartidor no está disponible para asignación");
        }

        // Obtener envío para verificar municipio
        EnvioDTO envio = envioService.obtenerEnvio(idEnvio);
        if (envio == null) {
            throw new IllegalArgumentException("Envío no encontrado");
        }

        // Obtener repartidor para verificar ruta
        RepartidorDTO repartidor = repartidorService.obtenerRepartidor(idRepartidor);
        if (repartidor == null) {
            throw new IllegalArgumentException("Repartidor no encontrado");
        }

        // La verificación de ruta se hace en el servicio
        repartidorService.asignarEnvio(idRepartidor, idEnvio);
    }

    /**
     * Obtiene repartidores disponibles que pueden atender un municipio específico
     */
    public List<RepartidorDTO> obtenerRepartidoresDisponiblesPorMunicipio(String municipio) {
        return repartidorService.obtenerRepartidoresActivos().stream()
                .filter(r -> {
                    if (r.getRuta() == null) return false;
                    return r.getRuta().contieneMunicipio(municipio);
                })
                .collect(java.util.stream.Collectors.toList());
    }

    public void reasignarEnvio(String idEnvio, String nuevoIdRepartidor) {
        // Obtener envío actual
        EnvioDTO envio = envioService.obtenerEnvio(idEnvio);
        if (envio == null) {
            throw new IllegalArgumentException("Envío no encontrado");
        }

        // Si tiene repartidor actual, liberarlo
        if (envio.getIdRepartidor() != null && !envio.getIdRepartidor().isEmpty()) {
            repartidorService.liberarEnvio(envio.getIdRepartidor(), idEnvio);
        }

        // Asignar al nuevo repartidor
        asignarEnvioARepartidor(idEnvio, nuevoIdRepartidor);
    }

    public void liberarEnvioDeRepartidor(String idEnvio) {
        EnvioDTO envio = envioService.obtenerEnvio(idEnvio);
        if (envio == null || envio.getIdRepartidor() == null) {
            throw new IllegalArgumentException("Envío no encontrado o no tiene repartidor asignado");
        }

        repartidorService.liberarEnvio(envio.getIdRepartidor(), idEnvio);
    }

    // ==================== CONSULTAS ====================

    public RepartidorDTO verDetalleRepartidor(String idRepartidor) {
        return repartidorService.obtenerRepartidor(idRepartidor);
    }

    public List<EnvioDTO> verEnviosDeRepartidor(String idRepartidor) {
        return repartidorService.consultarEnviosAsignados(idRepartidor);
    }

    // ==================== VALIDACIONES ====================

    public boolean validarCamposFormulario(RepartidorDTO dto) {
        try {
            repartidorService.validarDatosRepartidor(dto);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String obtenerMensajeErrorValidacion(RepartidorDTO dto) {
        try {
            repartidorService.validarDatosRepartidor(dto);
            return null;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================

    public RepartidorDTO obtenerRepartidorOptimoParaZona(String zona) {
        return repartidorService.obtenerRepartidorOptimo(zona);
    }

    public int contarEnviosPorRepartidor(String idRepartidor) {
        return repartidorService.contarEnviosPorRepartidor(idRepartidor);
    }

    public double calcularTasaEntrega(String idRepartidor) {
        return repartidorService.calcularTasaEntrega(idRepartidor);
    }
}

