package com.logistics.controller.usuario;

import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Usuario;
import com.logistics.patterns.creational.singleton.InMemoryDatabase;
import com.logistics.patterns.structural.facade.EnvioFacade;
import com.logistics.repository.UsuarioRepository;
import com.logistics.util.NavigationManager;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import java.util.List;

public class CrearEnvioController {
    private EnvioFacade envioFacade;
    private UsuarioRepository usuarioRepository;
    private List<Direccion> todasLasDirecciones;

    public CrearEnvioController() {
        this.envioFacade = new EnvioFacade();
        this.usuarioRepository = new UsuarioRepository();
        this.todasLasDirecciones = new ArrayList<>();
        cargarTodasLasDirecciones();
    }

    private void cargarTodasLasDirecciones() {
        InMemoryDatabase db = InMemoryDatabase.getInstance();
        db.getUsuarios().values().forEach(usuario -> {
            todasLasDirecciones.addAll(usuario.getDirecciones());
        });
    }

    public void cargarDirecciones(ComboBox<String> combo) {
        combo.getItems().clear();
        todasLasDirecciones.forEach(direccion -> {
            // Formato: "Alias - Calle, Ciudad"
            String texto = String.format("%s - %s, %s", 
                direccion.getAlias(), 
                direccion.getCalle(), 
                direccion.getCiudad());
            combo.getItems().add(texto);
        });
        
        // Ordenar alfabéticamente por ciudad
        combo.getItems().sort((a, b) -> {
            String ciudadA = a.substring(a.lastIndexOf(",") + 2);
            String ciudadB = b.substring(b.lastIndexOf(",") + 2);
            return ciudadA.compareTo(ciudadB);
        });
    }

    public void crearEnvio(String origenTexto, String destinoTexto, double peso, 
                          List<String> servicios, String metodoPago) {
        String usuarioId = NavigationManager.getInstance().getUsuarioActualId();
        Usuario usuario = usuarioRepository.findById(usuarioId);
        
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        // Buscar dirección origen por el texto completo
        Direccion origen = todasLasDirecciones.stream()
            .filter(d -> {
                String texto = String.format("%s - %s, %s", 
                    d.getAlias(), d.getCalle(), d.getCiudad());
                return texto.equals(origenTexto);
            })
            .findFirst()
            .orElse(null);
        
        // Buscar dirección destino por el texto completo
        Direccion destino = todasLasDirecciones.stream()
            .filter(d -> {
                String texto = String.format("%s - %s, %s", 
                    d.getAlias(), d.getCalle(), d.getCiudad());
                return texto.equals(destinoTexto);
            })
            .findFirst()
            .orElse(null);

        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Direcciones no válidas. Por favor seleccione direcciones válidas.");
        }

        if (origen.equals(destino)) {
            throw new IllegalArgumentException("El origen y destino no pueden ser la misma dirección.");
        }

        envioFacade.crearEnvioCompleto(origen, destino, peso, usuario, servicios, metodoPago);
    }
}

