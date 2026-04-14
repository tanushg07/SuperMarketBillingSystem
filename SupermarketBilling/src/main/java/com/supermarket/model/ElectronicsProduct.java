package com.supermarket.model;

// INHERITANCE - ElectronicsProduct extends Product
public class ElectronicsProduct extends Product {
    private int warrantyMonths;
    private String brand;

    public ElectronicsProduct(String productId, String name, double price, int stock, int warrantyMonths, String brand) {
        super(productId, name, price, stock, "Electronics");
        this.warrantyMonths = warrantyMonths;
        this.brand = brand;
    }

    // POLYMORPHISM - Electronics get flat 10% discount
    @Override
    public double calculateDiscount() {
        return getPrice() * 0.10;
    }

    @Override
    public String getProductType() {
        return "Electronics (" + brand + ")";
    }

    public int getWarrantyMonths() { return warrantyMonths; }
    public String getBrand() { return brand; }
}
