package com.logistics.patterns.behavioral.state;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;

/**
 * Interfaz para el patrón State que representa los diferentes estados de un envío.
 * Cada estado implementa las transiciones permitidas según las reglas de negocio.
 */
public interface EnvioState {
    /**
     * Asigna un repartidor al envío.
     * 
     * @param e El envío al que se le asignará el repartidor
     * @param r El repartidor a asignar
     */
    void asignar(Envio e, Repartidor r);
    
    /**
     * Marca el envío como en ruta.
     * 
     * @param e El envío a marcar como en ruta
     */
    void marcarEnRuta(Envio e);
    
    /**
     * Marca el envío como entregado.
     * 
     * @param e El envío a marcar como entregado
     */
    void marcarEntregado(Envio e);
    
    /**
     * Cancela el envío.
     * 
     * @param e El envío a cancelar
     */
    void cancelar(Envio e);
    
    /**
     * Obtiene el nombre del estado.
     * 
     * @return El nombre del estado como String
     */
    String name();
}
