package com.logistics.patterns.comportamiento.strategy;

/**
 * Interfaz para el patrón Strategy que define diferentes estrategias de pago.
 * Cada implementación procesa el pago de manera diferente según el método seleccionado.
 */
public interface PaymentStrategy {
    /**
     * Procesa un pago usando la estrategia específica.
     * 
     * @param data Los datos del pago a procesar
     * @return El resultado del procesamiento del pago
     */
    PaymentResult pay(PaymentData data);
}
