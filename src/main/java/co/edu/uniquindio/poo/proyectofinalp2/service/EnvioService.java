package service;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;
import model.*;
import patterns.creacional.EnvioBuilder;

public class EnvioService {
    private List<Envio> envios;

    public EnvioService() {
        this.envios = new ArrayList<>();
    }

    public Envio crearEnvio(Usuario usuario, Direccion origen, Direccion destino, 
                           double peso, double volumen, double costo) {
        String idEnvio = UUID.randomUUID().toString();
        Envio envio = new Envio(idEnvio, usuario, origen, destino, peso, volumen, costo);
        envios.add(envio);
        usuario.getEnvios().add(envio);
        return envio;
    }

    public Envio crearEnvioConBuilder(EnvioBuilder builder) {
        Envio envio = builder.build();
        envios.add(envio);
        if (envio.getUsuario() != null) {
            envio.getUsuario().getEnvios().add(envio);
        }
        return envio;
    }

    public Envio buscarPorId(String idEnvio) {
        return envios.stream()
            .filter(e -> e.getIdEnvio().equals(idEnvio))
            .findFirst()
            .orElse(null);
    }

    public List<Envio> listar() {
        return new ArrayList<>(envios);
    }

    public List<Envio> listarPorUsuario(Usuario usuario) {
        return envios.stream()
            .filter(e -> e.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()))
            .collect(Collectors.toList());
    }

    public List<Envio> filtrarPorEstado(String estado) {
        return envios.stream()
            .filter(e -> e.getEstado().equals(estado))
            .collect(Collectors.toList());
    }

    public List<Envio> filtrarPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return envios.stream()
            .filter(e -> {
                LocalDate fechaCreacion = e.getFechaCreacion().toLocalDate();
                return !fechaCreacion.isBefore(fechaInicio) && !fechaCreacion.isAfter(fechaFin);
            })
            .collect(Collectors.toList());
    }

    public List<Envio> filtrarPorZona(String zona) {
        return envios.stream()
            .filter(e -> {
                if (e.getRepartidor() != null) {
                    return e.getRepartidor().getZonaCobertura().equals(zona);
                }
                return false;
            })
            .collect(Collectors.toList());
    }

    public void actualizarEstado(Envio envio, String nuevoEstado) {
        envio.setEstado(nuevoEstado);
    }

    public void asignarRepartidor(Envio envio, Repartidor repartidor) {
        envio.setRepartidor(repartidor);
        envio.setEstado("ASIGNADO");
        repartidor.getEnviosAsignados().add(envio);
        repartidor.setEstado("EN_RUTA");
    }

    public void reasignarRepartidor(Envio envio, Repartidor nuevoRepartidor) {
        Repartidor repartidorAnterior = envio.getRepartidor();
        if (repartidorAnterior != null) {
            repartidorAnterior.getEnviosAsignados().remove(envio);
        }
        asignarRepartidor(envio, nuevoRepartidor);
    }

    public boolean cancelarEnvio(Envio envio) {
        if (envio.puedeCancelar()) {
            envio.setEstado("CANCELADO");
            return true;
        }
        return false;
    }

    public void registrarIncidencia(Envio envio, String tipo, String descripcion) {
        String idIncidencia = UUID.randomUUID().toString();
        Incidencia incidencia = new Incidencia(idIncidencia, envio, tipo, descripcion);
        envio.agregarIncidencia(incidencia);
    }

    public double calcularTiempoPromedioEntrega() {
        return envios.stream()
            .filter(e -> "ENTREGADO".equals(e.getEstado()))
            .mapToLong(e -> {
                if (e.getFechaCreacion() != null && e.getFechaEstimadaEntrega() != null) {
                    return java.time.temporal.ChronoUnit.DAYS.between(
                        e.getFechaCreacion().toLocalDate(), 
                        e.getFechaEstimadaEntrega()
                    );
                }
                return 0;
            })
            .average()
            .orElse(0.0);
    }
}
