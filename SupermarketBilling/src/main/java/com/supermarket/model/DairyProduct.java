package com.supermarket.model;

import java.time.LocalDate;

// INHERITANCE - DairyProduct extends Product
public class DairyProduct extends Product {
    private LocalDate expiryDate;
    private boolean isRefrigerated;

    public DairyProduct(String productId, String name, double price, int stock, LocalDate expiryDate, boolean isRefrigerated) {
        super(productId, name, price, stock, "Dairy");
        this.expiryDate = expiryDate;
        this.isRefrigerated = isRefrigerated;
    }

    // POLYMORPHISM - Near-expiry items get bigger discount
    @Override
    public double calculateDiscount() {
        long daysToExpiry = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        if (daysToExpiry <= 2) return getPrice() * 0.30; // 30% off near expiry
        if (daysToExpiry <= 5) return getPrice() * 0.15; // 15% off soon expiring
        return getPrice() * 0.03;                          // 3% regular discount
    }

    @Override
    public String getProductType() {
        return "Dairy" + (isRefrigerated ? " (Refrigerated)" : "");
    }

    public LocalDate getExpiryDate() { return expiryDate; }
    public boolean isRefrigerated() { return isRefrigerated; }
    public boolean isExpired() { return LocalDate.now().isAfter(expiryDate); }
}
