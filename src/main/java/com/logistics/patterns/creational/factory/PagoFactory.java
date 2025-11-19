package com.logistics.patterns.creational.factory;

import com.logistics.patterns.behavioral.strategy.*;

/**
 * Factory para crear estrategias de pago.
 * Proporciona una forma centralizada de crear diferentes estrategias de pago según el tipo especificado.
 */
public class PagoFactory {
    /**
     * Crea una estrategia de pago según el tipo especificado.
     * 
     * @param tipo El tipo de pago (TARJETA, CARD, PSE, o cualquier otro para Mock)
     * @return La estrategia de pago correspondiente al tipo
     */
    public PaymentStrategy crearEstrategia(String tipo){
        if("TARJETA".equalsIgnoreCase(tipo) || "CARD".equalsIgnoreCase(tipo)) {
            return new CardPaymentStrategy();
        }
        if("PSE".equalsIgnoreCase(tipo)) {
            return new PsePaymentStrategy();
        }
        return new MockPaymentStrategy();
    }
    
    /**
     * Método estático para crear una estrategia de pago.
     * 
     * @param tipo El tipo de pago (TARJETA, CARD, PSE, o cualquier otro para Mock)
     * @return La estrategia de pago correspondiente al tipo
     */
    public static PaymentStrategy create(String tipo){
        return new PagoFactory().crearEstrategia(tipo);
    }
}
