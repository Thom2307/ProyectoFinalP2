package com.logistics.patterns.estructural.decorator;

import com.logistics.model.entities.Envio;

/**
 * Interfaz para el patrón Decorator que representa servicios adicionales para envíos.
 * Permite agregar funcionalidad adicional (costos extra) a un envío de forma dinámica.
 */
public interface Adicional {
    /**
     * Aplica el servicio adicional al envío y calcula el costo adicional.
     * 
     * @param envio El envío al que se le aplicará el servicio adicional
     * @return El costo adicional generado por este servicio
     */
    double aplicar(Envio envio);
    
    /**
     * Obtiene la descripción del servicio adicional.
     * 
     * @return La descripción del servicio adicional
     */
    String descripcion();
}
