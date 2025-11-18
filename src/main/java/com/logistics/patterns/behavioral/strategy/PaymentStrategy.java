package com.logistics.patterns.behavioral.strategy;

public interface PaymentStrategy {
    PaymentResult pay(PaymentData data);
}
