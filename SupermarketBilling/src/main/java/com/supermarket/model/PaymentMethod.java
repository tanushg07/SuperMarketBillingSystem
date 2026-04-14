package com.supermarket.model;

// ABSTRACTION - Abstract PaymentMethod
public abstract class PaymentMethod {
    protected String transactionId;

    // ABSTRACTION - Subclasses define how payment is processed
    public abstract boolean processPayment(double amount);
    public abstract String getMethodName();

    public String getTransactionId() { return transactionId; }

    // STATIC FACTORY METHOD - Design Pattern
    public static PaymentMethod of(String type) {
        return switch (type.toUpperCase()) {
            case "CASH"   -> new CashPayment();
            case "CARD"   -> new CardPayment("**** **** **** 4242");
            case "UPI"    -> new UPIPayment("customer@upi");
            default       -> throw new IllegalArgumentException("Unknown payment type: " + type);
        };
    }
}

// INHERITANCE - CashPayment extends PaymentMethod
class CashPayment extends PaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        this.transactionId = "CASH-" + System.currentTimeMillis();
        System.out.println("💵 Cash payment of ₹" + String.format("%.2f", amount) + " received.");
        return true;
    }

    @Override
    public String getMethodName() { return "Cash"; }
}

// INHERITANCE - CardPayment extends PaymentMethod
class CardPayment extends PaymentMethod {
    private String maskedCardNumber;

    public CardPayment(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    @Override
    public boolean processPayment(double amount) {
        this.transactionId = "CARD-" + System.currentTimeMillis();
        System.out.println("💳 Card payment of ₹" + String.format("%.2f", amount) + " processed for card: " + maskedCardNumber);
        return true;
    }

    @Override
    public String getMethodName() { return "Credit/Debit Card (" + maskedCardNumber + ")"; }
}

// INHERITANCE - UPIPayment extends PaymentMethod
class UPIPayment extends PaymentMethod {
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount) {
        this.transactionId = "UPI-" + System.currentTimeMillis();
        System.out.println("📱 UPI payment of ₹" + String.format("%.2f", amount) + " processed via: " + upiId);
        return true;
    }

    @Override
    public String getMethodName() { return "UPI (" + upiId + ")"; }
}
