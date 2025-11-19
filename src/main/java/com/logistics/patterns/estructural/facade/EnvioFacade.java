package com.logistics.patterns.estructural.facade;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.TarifaDTO;
import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Usuario;
import com.logistics.patterns.creacional.builder.EnvioBuilder;
import com.logistics.service.TarifaService;
import com.logistics.service.NotificationService;
import com.logistics.service.EnvioService;
import com.logistics.service.PagoService;
import java.util.List;
import java.util.UUID;

/**
 * Facade para la creación completa de envíos.
 * Simplifica el proceso de creación de envíos coordinando múltiples servicios y patrones de diseño.
 * Utiliza Builder, Decorator, Strategy y Observer patterns.
 */
public class EnvioFacade {
    private TarifaService tarifaService = new TarifaService();
    private EnvioService envioService = new EnvioService();
    private NotificationService notificationService = new NotificationService();
    private PagoService pagoService = new PagoService();

    /**
     * Crea un envío completo en el sistema.
     * Coordina la construcción del envío, cálculo de tarifa, procesamiento de pago y notificación.
     * 
     * @param origen La dirección de origen del envío
     * @param destino La dirección de destino del envío
     * @param peso El peso del paquete en kilogramos
     * @param usuario El usuario que solicita el envío
     * @param serviciosAdicionales Lista de servicios adicionales seleccionados
     * @param metodoPago El método de pago a utilizar
     * @return DTO con la información del envío creado
     */
    public EnvioDTO crearEnvioCompleto(
            Direccion origen, Direccion destino, double peso, Usuario usuario,
            List<String> serviciosAdicionales, String metodoPago) {
        
        // Builder pattern para construir el envío
        Envio envio = new EnvioBuilder()
                .withId("env" + UUID.randomUUID().toString().substring(0, 8))
                .from(origen)
                .to(destino)
                .weight(peso)
                .forUser(usuario)
                .build();
        
        // Decorator pattern para calcular tarifa con servicios adicionales
        TarifaDTO tarifa = tarifaService.calcularTarifa(envio, serviciosAdicionales);
        if (serviciosAdicionales != null) {
            envio.getAdicionales().addAll(serviciosAdicionales);
        }
        
        // Guardar envío
        envioService.save(envio);
        
        // Strategy pattern para procesar pago
        com.logistics.model.dto.PagoDTO pagoDTO = pagoService.procesarPago(envio.getIdEnvio(), tarifa.getCostoTotal(), metodoPago);
        
        // Observer pattern para notificar
        notificationService.notifyUsuario(usuario, "Envío creado con id: " + envio.getIdEnvio());
        
        EnvioDTO envioDTO = envioService.obtenerEnvio(envio.getIdEnvio());
        // Almacenar información del pago en el DTO para mostrarla después
        return envioDTO;
    }
}
