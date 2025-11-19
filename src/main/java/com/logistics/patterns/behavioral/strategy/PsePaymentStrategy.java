package com.logistics.patterns.behavioral.strategy;

import java.util.UUID;

public class PsePaymentStrategy implements PaymentStrategy {
    public PaymentResult pay(PaymentData data){
        // Simulate PSE payment
        return new PaymentResult(true, "PSE-"+UUID.randomUUID().toString());
    }
}
