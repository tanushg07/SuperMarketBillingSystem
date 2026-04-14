package com.supermarket.model;

// ABSTRACTION - Abstract base class for all products
public abstract class Product {
    // ENCAPSULATION - private fields with getters/setters
    private String productId;
    private String name;
    private double price;
    private int stock;
    private String category;

    // Constructor
    public Product(String productId, String name, double price, int stock, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // ABSTRACTION - Abstract method to be implemented by subclasses
    public abstract double calculateDiscount();
    public abstract String getProductType();

    // ENCAPSULATION - Getters & Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    public int getStock() { return stock; }
    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative");
        this.stock = stock;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getFinalPrice() {
        return price - calculateDiscount();
    }

    public boolean isAvailable(int quantity) {
        return stock >= quantity;
    }

    public void reduceStock(int quantity) {
        if (!isAvailable(quantity)) {
            throw new IllegalStateException("Insufficient stock for product: " + name);
        }
        this.stock -= quantity;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - ₹%.2f (Stock: %d)", productId, name, getFinalPrice(), stock);
    }
}
