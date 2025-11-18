package com.logistics.patterns.behavioral.strategy;

public class PaymentResult {
    public boolean approved;
    public String transactionId;
    public PaymentResult(boolean a, String t){ this.approved = a; this.transactionId = t; }
}
