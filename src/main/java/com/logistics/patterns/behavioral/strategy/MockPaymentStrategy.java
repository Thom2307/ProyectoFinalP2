package com.logistics.patterns.behavioral.strategy;

import java.util.UUID;

public class MockPaymentStrategy implements PaymentStrategy {
    public PaymentResult pay(PaymentData data){
        return new PaymentResult(true, "MOCK-"+UUID.randomUUID().toString());
    }
}
