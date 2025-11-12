package patterns.estructural;

import model.*;
import service.*;
import java.util.List;

/**
 * Facade: Proporciona una interfaz simplificada al sistema complejo de logística
 * Oculta la complejidad de múltiples servicios detrás de una interfaz simple
 */
public class SistemaLogisticaFacade {
    private EnvioService envioService;
    private UsuarioService usuarioService;
    private PagoService pagoService;
    private TarifaService tarifaService;

    public SistemaLogisticaFacade() {
        this.envioService = new EnvioService();
        this.usuarioService = new UsuarioService();
        this.pagoService = new PagoService();
        this.tarifaService = new TarifaService();
    }

    // Método simplificado para crear un envío completo
    public Envio crearEnvioCompleto(Usuario usuario, Direccion origen, Direccion destino,
                                   double peso, double volumen, String prioridad,
                                   List<ServicioAdicional> servicios, MetodoPago metodoPago) {
        // 1. Calcular tarifa
        double costo = tarifaService.cotizarTarifa(origen, destino, peso, volumen, prioridad);
        
        // 2. Agregar costos de servicios adicionales
        for (ServicioAdicional servicio : servicios) {
            costo += servicio.getCostoAdicional();
        }
        
        // 3. Crear envío
        Envio envio = envioService.crearEnvio(usuario, origen, destino, peso, volumen, costo);
        
        // 4. Agregar servicios adicionales
        for (ServicioAdicional servicio : servicios) {
            envio.agregarServicioAdicional(servicio);
        }
        
        // 5. Procesar pago
        Pago pago = pagoService.procesarPago(envio, metodoPago, costo);
        envio.setPago(pago);
        
        return envio;
    }

    public List<Envio> consultarEnviosUsuario(Usuario usuario) {
        return envioService.listarPorUsuario(usuario);
    }

    public boolean rastrearEnvio(String idEnvio) {
        Envio envio = envioService.buscarPorId(idEnvio);
        return envio != null;
    }
}


