package com.logistics.controller.usuario;

import com.logistics.model.dto.TarifaDTO;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Usuario;
import com.logistics.patterns.creational.singleton.InMemoryDatabase;
import com.logistics.patterns.structural.facade.EnvioFacade;
import com.logistics.repository.UsuarioRepository;
import com.logistics.service.TarifaService;
import com.logistics.util.NavigationManager;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la creación de nuevos envíos.
 * Gestiona la creación completa de envíos incluyendo validaciones, direcciones y pagos.
 */
public class CrearEnvioController {
    private EnvioFacade envioFacade;
    private UsuarioRepository usuarioRepository;
    private List<Direccion> todasLasDirecciones;
    private TarifaService tarifaService;

    /**
     * Constructor que inicializa los servicios necesarios y carga todas las direcciones disponibles.
     */
    public CrearEnvioController() {
        this.envioFacade = new EnvioFacade();
        this.usuarioRepository = new UsuarioRepository();
        this.todasLasDirecciones = new ArrayList<>();
        this.tarifaService = new TarifaService();
        cargarTodasLasDirecciones();
    }

    /**
     * Carga todas las direcciones de todos los usuarios del sistema en memoria.
     * Se ejecuta al inicializar el controlador para tener acceso rápido a las direcciones.
     */
    private void cargarTodasLasDirecciones() {
        InMemoryDatabase db = InMemoryDatabase.getInstance();
        db.getUsuarios().values().forEach(usuario -> {
            todasLasDirecciones.addAll(usuario.getDirecciones());
        });
    }

    /**
     * Carga las direcciones disponibles en un ComboBox de JavaFX.
     * Las direcciones se muestran en formato "Alias - Calle, Ciudad" y se ordenan alfabéticamente por ciudad.
     * 
     * @param combo El ComboBox de JavaFX donde se cargarán las direcciones
     */
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

    /**
     * Crea un nuevo envío completo en el sistema.
     * Valida las direcciones, verifica que origen y destino sean diferentes, y crea el envío con pago.
     * 
     * @param origenTexto Texto de la dirección de origen en formato "Alias - Calle, Ciudad"
     * @param destinoTexto Texto de la dirección de destino en formato "Alias - Calle, Ciudad"
     * @param peso El peso del paquete en kilogramos
     * @param servicios Lista de servicios adicionales seleccionados
     * @param metodoPago El método de pago seleccionado (TARJETA, PSE, etc.)
     * @return DTO del envío creado con toda su información
     * @throws IllegalArgumentException Si el usuario no existe, las direcciones son inválidas o origen y destino son iguales
     */
    public com.logistics.model.dto.EnvioDTO crearEnvio(String origenTexto, String destinoTexto, double peso, 
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

        return envioFacade.crearEnvioCompleto(origen, destino, peso, usuario, servicios, metodoPago);
    }
    
    /**
     * Obtiene el último pago asociado a un envío específico.
     * 
     * @param idEnvio El identificador único del envío
     * @return DTO del último pago realizado para el envío, o null si no hay pagos
     */
    public com.logistics.model.dto.PagoDTO obtenerPagoPorEnvio(String idEnvio) {
        com.logistics.service.PagoService pagoService = new com.logistics.service.PagoService();
        java.util.List<com.logistics.model.dto.PagoDTO> pagos = pagoService.obtenerPagosPorEnvio(idEnvio);
        return pagos.isEmpty() ? null : pagos.get(pagos.size() - 1); // Retornar el último pago
    }
    
    /**
     * Busca una dirección por su representación en texto.
     * 
     * @param texto El texto de la dirección en formato "Alias - Calle, Ciudad"
     * @return La dirección encontrada, o null si no existe
     */
    public Direccion obtenerDireccionPorTexto(String texto) {
        return todasLasDirecciones.stream()
            .filter(d -> {
                String dirTexto = String.format("%s - %s, %s", 
                    d.getAlias(), d.getCalle(), d.getCiudad());
                return dirTexto.equals(texto);
            })
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Calcula una tarifa estimada para un envío basándose en coordenadas.
     * Útil para mostrar una estimación antes de crear el envío definitivo.
     * 
     * @param latOrigen Latitud del punto de origen
     * @param lonOrigen Longitud del punto de origen
     * @param latDestino Latitud del punto de destino
     * @param lonDestino Longitud del punto de destino
     * @param peso El peso del paquete en kilogramos
     * @param serviciosAdicionales Lista de servicios adicionales seleccionados
     * @return DTO con el desglose de la tarifa estimada
     */
    public TarifaDTO calcularTarifaEstimada(double latOrigen, double lonOrigen,
                                           double latDestino, double lonDestino,
                                           double peso, List<String> serviciosAdicionales) {
        Direccion origen = new Direccion("temp", "Origen", "", "", latOrigen, lonOrigen);
        Direccion destino = new Direccion("temp", "Destino", "", "", latDestino, lonDestino);
        
        com.logistics.model.entities.Envio envio = new com.logistics.model.entities.Envio();
        envio.setOrigen(origen);
        envio.setDestino(destino);
        envio.setPeso(peso);
        
        return tarifaService.calcularTarifa(envio, serviciosAdicionales);
    }
}

