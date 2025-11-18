package com.logistics.patterns.structural.facade;

import com.logistics.model.dto.EnvioDTO;
import com.logistics.model.dto.TarifaDTO;
import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Usuario;
import com.logistics.patterns.creational.builder.EnvioBuilder;
import com.logistics.service.TarifaService;
import com.logistics.service.NotificationService;
import com.logistics.service.EnvioService;
import com.logistics.service.PagoService;
import java.util.List;
import java.util.UUID;

public class EnvioFacade {
    private TarifaService tarifaService = new TarifaService();
    private EnvioService envioService = new EnvioService();
    private NotificationService notificationService = new NotificationService();
    private PagoService pagoService = new PagoService();

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
