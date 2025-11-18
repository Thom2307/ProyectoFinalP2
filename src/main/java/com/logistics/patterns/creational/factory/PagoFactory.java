package com.logistics.patterns.creational.factory;

import com.logistics.patterns.behavioral.strategy.*;

public class PagoFactory {
    public PaymentStrategy crearEstrategia(String tipo){
        if("TARJETA".equalsIgnoreCase(tipo) || "CARD".equalsIgnoreCase(tipo)) {
            return new CardPaymentStrategy();
        }
        if("PSE".equalsIgnoreCase(tipo)) {
            return new PsePaymentStrategy();
        }
        return new MockPaymentStrategy();
    }
    
    public static PaymentStrategy create(String tipo){
        return new PagoFactory().crearEstrategia(tipo);
    }
}
