package com.supermarket.interfaces;

// INTERFACE - Contract for discountable entities
public interface Discountable {
    double applyDiscount(double amount);
    String getDiscountDescription();
}
