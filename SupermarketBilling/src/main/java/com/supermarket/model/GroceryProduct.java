package com.supermarket.model;

// INHERITANCE - GroceryProduct extends Product
public class GroceryProduct extends Product {
    private double weightKg;
    private boolean isOrganic;

    public GroceryProduct(String productId, String name, double price, int stock, double weightKg, boolean isOrganic) {
        super(productId, name, price, stock, "Grocery");
        this.weightKg = weightKg;
        this.isOrganic = isOrganic;
    }

    // POLYMORPHISM - Overriding abstract method
    @Override
    public double calculateDiscount() {
        // Organic products get 5% discount, others get 2%
        return isOrganic ? getPrice() * 0.05 : getPrice() * 0.02;
    }

    @Override
    public String getProductType() {
        return isOrganic ? "Organic Grocery" : "Grocery";
    }

    public double getWeightKg() { return weightKg; }
    public boolean isOrganic() { return isOrganic; }
}
