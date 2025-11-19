package com.logistics.patterns.comportamiento.strategy;

public class PaymentResult {
    public boolean approved;
    public String transactionId;
    public PaymentResult(boolean a, String t){ this.approved = a; this.transactionId = t; }
}
