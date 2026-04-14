package com.supermarket.interfaces;

// INTERFACE - Defines contract for taxable entities
public interface Taxable {
    double TAX_RATE_GST_5  = 0.05;
    double TAX_RATE_GST_12 = 0.12;
    double TAX_RATE_GST_18 = 0.18;

    double calculateTax();
    double getTaxRate();
    String getTaxCategory();
}
