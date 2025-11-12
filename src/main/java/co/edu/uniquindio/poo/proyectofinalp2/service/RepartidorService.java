package service;

import model.*;
import java.util.*;
import java.util.stream.Collectors;

public class RepartidorService {
    private List<Repartidor> repartidores;

    public RepartidorService() {
        this.repartidores = new ArrayList<>();
    }

    public Repartidor crearRepartidor(String idRepartidor, String nombre, String documento, 
                                     String telefono, String zonaCobertura) {
        Repartidor repartidor = new Repartidor(idRepartidor, nombre, documento, telefono, zonaCobertura);
        repartidores.add(repartidor);
        return repartidor;
    }

    public Repartidor buscarPorId(String idRepartidor) {
        return repartidores.stream()
            .filter(r -> r.getIdRepartidor().equals(idRepartidor))
            .findFirst()
            .orElse(null);
    }

    public void actualizarRepartidor(Repartidor repartidor, String nombre, String telefono, String zonaCobertura) {
        repartidor.setNombre(nombre);
        repartidor.setTelefono(telefono);
        repartidor.setZonaCobertura(zonaCobertura);
    }

    public void eliminarRepartidor(String idRepartidor) {
        repartidores.removeIf(r -> r.getIdRepartidor().equals(idRepartidor));
    }

    public List<Repartidor> listarTodos() {
        return new ArrayList<>(repartidores);
    }

    public List<Repartidor> listarDisponibles() {
        return repartidores.stream()
            .filter(r -> "ACTIVO".equals(r.getEstado()))
            .collect(Collectors.toList());
    }

    public void cambiarDisponibilidad(Repartidor repartidor, String nuevoEstado) {
        repartidor.setEstado(nuevoEstado);
    }

    public List<Envio> consultarEnviosAsignados(Repartidor repartidor) {
        return repartidor.getEnviosAsignados();
    }

    public Repartidor buscarPorZona(String zona) {
        return repartidores.stream()
            .filter(r -> r.getZonaCobertura().equals(zona) && "ACTIVO".equals(r.getEstado()))
            .findFirst()
            .orElse(null);
    }
}


