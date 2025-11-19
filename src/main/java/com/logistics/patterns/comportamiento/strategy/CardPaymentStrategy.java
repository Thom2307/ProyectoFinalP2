package com.logistics.patterns.comportamiento.strategy;

import java.util.UUID;

public class CardPaymentStrategy implements PaymentStrategy {
    public PaymentResult pay(PaymentData data){
        // Simulate card payment approval
        return new PaymentResult(true, "CARD-"+UUID.randomUUID().toString());
    }
}
