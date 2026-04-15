package com.supermarket.service;

import com.supermarket.model.*;
import com.supermarket.exceptions.ProductNotFoundException;
import com.supermarket.exceptions.OutOfStockException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// SINGLETON PATTERN - Only one inventory instance in the system
public class InventoryService {
    private static InventoryService instance; // Singleton instance
    private Map<String, Product> products;    // Product catalog

    // Private constructor - prevents instantiation from outside
    private InventoryService() {
        products = new LinkedHashMap<>();
        seedData();
    }

    // SINGLETON - Thread-safe getInstance
    public static synchronized InventoryService getInstance() {
        if (instance == null) {
            instance = new InventoryService();
        }
        return instance;
    }

    // SEED DATA - Pre-load inventory with sample products
    private void seedData() {
        // Groceries
        addProduct(new GroceryProduct("G001", "Basmati Rice (5kg)", 299.0, 50, 5.0, false));
        addProduct(new GroceryProduct("G002", "Organic Whole Wheat Flour", 180.0, 30, 2.0, true));
        addProduct(new GroceryProduct("G003", "Toor Dal (1kg)", 145.0, 40, 1.0, false));
        addProduct(new GroceryProduct("G004", "Sunflower Oil (1L)", 165.0, 25, 1.0, false));
        addProduct(new GroceryProduct("G005", "Organic Honey (500g)", 350.0, 15, 0.5, true));
        addProduct(new GroceryProduct("G006", "Brown Sugar (1kg)", 92.0, 36, 1.0, false));
        addProduct(new GroceryProduct("G007", "Poha Premium (1kg)", 78.0, 42, 1.0, false));
        addProduct(new GroceryProduct("G008", "Biscuit Family Pack", 135.0, 28, 0.8, false));
        addProduct(new GroceryProduct("G009", "Groundnut Oil (1L)", 210.0, 18, 1.0, false));

        // Dairy
        addProduct(new DairyProduct("D001", "Full Cream Milk (1L)",    68.0, 100, LocalDate.now().plusDays(3), true));
        addProduct(new DairyProduct("D002", "Amul Butter (500g)",     275.0, 40,  LocalDate.now().plusDays(30), true));
        addProduct(new DairyProduct("D003", "Greek Yogurt (400g)",     95.0, 25,  LocalDate.now().plusDays(7), true));
        addProduct(new DairyProduct("D004", "Paneer (200g)",          120.0, 20,  LocalDate.now().plusDays(4), true));
        addProduct(new DairyProduct("D005", "Cheddar Cheese (200g)",  220.0, 15,  LocalDate.now().plusDays(60), true));
        addProduct(new DairyProduct("D006", "Curd Cup (400g)",         50.0, 55,  LocalDate.now().plusDays(5), true));
        addProduct(new DairyProduct("D007", "Mozzarella Cheese (200g)",180.0, 22,  LocalDate.now().plusDays(45), true));
        addProduct(new DairyProduct("D008", "Flavoured Milk (200ml)",  35.0, 70,  LocalDate.now().plusDays(20), true));
        addProduct(new DairyProduct("D009", "Fresh Cream (200ml)",     65.0, 24,  LocalDate.now().plusDays(10), true));

        // Electronics
        addProduct(new ElectronicsProduct("E001", "Mixer Grinder 750W", 3499.0, 10, 12, "Philips"));
        addProduct(new ElectronicsProduct("E002", "LED Bulb 12W (Pack of 4)", 499.0, 30, 24, "Syska"));
        addProduct(new ElectronicsProduct("E003", "Electric Kettle 1.5L", 1299.0, 8, 12, "Havells"));
        addProduct(new ElectronicsProduct("E004", "Bluetooth Earbuds",   1999.0, 5, 6, "boAt"));
        addProduct(new ElectronicsProduct("E005", "Induction Cooktop 1800W", 2399.0, 12, 12, "Prestige"));
        addProduct(new ElectronicsProduct("E006", "Digital Weighing Scale", 899.0, 16, 6, "HealthSense"));
        addProduct(new ElectronicsProduct("E007", "Emergency LED Lantern", 749.0, 20, 6, "Wipro"));
        addProduct(new ElectronicsProduct("E008", "Hand Blender 300W",   1599.0, 9, 12, "Inalsa"));
    }

    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
    }

    public Product getProduct(String productId) throws ProductNotFoundException {
        Product p = products.get(productId);
        if (p == null) throw new ProductNotFoundException("Product not found: " + productId);
        return p;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Product> getProductsByCategory(String category) {
        return products.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String keyword) {
        String kw = keyword.toLowerCase();
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(kw) ||
                             p.getCategory().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    public boolean isInStock(String productId, int quantity) throws ProductNotFoundException {
        return getProduct(productId).isAvailable(quantity);
    }

    public Map<String, Long> getStockSummaryByCategory() {
        return products.values().stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
    }

    public List<Product> getLowStockProducts(int threshold) {
        return products.values().stream()
                .filter(p -> p.getStock() <= threshold)
                .collect(Collectors.toList());
    }
}
