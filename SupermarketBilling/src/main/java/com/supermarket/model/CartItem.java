package com.supermarket.model;

import com.supermarket.interfaces.Taxable;

// ENCAPSULATION - CartItem holds product + quantity
public class CartItem implements Taxable {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.product = product;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return product.getFinalPrice() * quantity;
    }

    // INTERFACE IMPLEMENTATION - Taxable
    @Override
    public double calculateTax() {
        return getSubtotal() * getTaxRate();
    }

    @Override
    public double getTaxRate() {
        String category = product.getCategory();
        return switch (category) {
            case "Electronics" -> TAX_RATE_GST_18;
            case "Grocery"     -> TAX_RATE_GST_5;
            case "Dairy"       -> TAX_RATE_GST_5;
            default            -> TAX_RATE_GST_12;
        };
    }

    @Override
    public String getTaxCategory() {
        String category = product.getCategory();
        return switch (category) {
            case "Electronics" -> "GST 18%";
            case "Grocery"     -> "GST 5%";
            case "Dairy"       -> "GST 5%";
            default            -> "GST 12%";
        };
    }

    public double getTotalWithTax() {
        return getSubtotal() + calculateTax();
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return String.format("%-30s x%2d  ₹%8.2f  [Tax: ₹%.2f]",
                product.getName(), quantity, getSubtotal(), calculateTax());
    }
}
