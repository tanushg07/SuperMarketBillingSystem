package com.supermarket;

import com.supermarket.model.*;
import com.supermarket.service.*;
import com.supermarket.exceptions.*;

/**
 * =====================================================================
 * SUPERMARKET BILLING SYSTEM - OOP Concepts Demonstration
 * =====================================================================
 * OOP CONCEPTS USED:
 * 1. ENCAPSULATION   - All fields private with getters/setters
 *                      (Product, Customer, CartItem, ShoppingCart, Bill)
 * 2. INHERITANCE     - GroceryProduct, DairyProduct, ElectronicsProduct
 *                      extend Product; MemberCustomer extends Customer;
 *                      CashPayment, CardPayment, UPIPayment extend PaymentMethod
 * 3. POLYMORPHISM    - calculateDiscount() overridden in each product type;
 *                      processPayment() overridden in each payment type;
 *                      applyDiscount() overridden in MemberCustomer
 * 4. ABSTRACTION     - Abstract classes: Product, PaymentMethod
 *                      Abstract methods: calculateDiscount(), processPayment()
 * 5. INTERFACE       - Taxable (CartItem), Discountable (Customer)
 * 6. SINGLETON       - InventoryService.getInstance()
 * 7. EXCEPTION       - Custom checked exceptions for robust error handling
 * 8. STATIC FACTORY  - PaymentMethod.of(type) factory method
 * =====================================================================
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("🛒 FRESH MART - SUPERMARKET BILLING SYSTEM");
        System.out.println("=".repeat(55));

        // SINGLETON - Get single inventory instance
        InventoryService inventory = InventoryService.getInstance();
        BillingService billing = new BillingService();

        // -------------------------------------------------------
        // CUSTOMER 1: Regular Guest Customer
        // -------------------------------------------------------
        System.out.println("\n📋 CUSTOMER 1: Regular Guest");
        Customer guest = new Customer("C001", "Ravi Kumar", "9876543210", "ravi@email.com");

        try {
            ShoppingCart cart1 = billing.createCart(guest);
            billing.addToCart(cart1, "G001", 2);  // Basmati Rice x2
            billing.addToCart(cart1, "D001", 3);  // Milk x3
            billing.addToCart(cart1, "G005", 1);  // Organic Honey x1

            // POLYMORPHISM in action - different discount for each product type
            System.out.println("\n🛒 Cart Items:");
            cart1.getItems().forEach(item ->
                System.out.printf("  %-30s ₹%.2f (Disc: ₹%.2f) Tax: %s%n",
                    item.getProduct().getName(),
                    item.getProduct().getFinalPrice(),
                    item.getProduct().calculateDiscount(),
                    item.getTaxCategory())
            );

            // PAYMENT - UPI
            Bill bill1 = billing.checkout(cart1, "UPI");
            System.out.println(bill1.getBillSummary());

        } catch (ProductNotFoundException | OutOfStockException e) {
            System.err.println("❌ Product Error: " + e.getMessage());
        } catch (EmptyCartException | PaymentFailedException e) {
            System.err.println("❌ Billing Error: " + e.getMessage());
        }

        // -------------------------------------------------------
        // CUSTOMER 2: Member Customer
        // -------------------------------------------------------
        System.out.println("\n📋 CUSTOMER 2: Premium Member");
        Customer member = new Customer("C002", "Priya Singh", "9123456789", "priya@email.com");
        member.setMember(true);

        try {
            ShoppingCart cart2 = billing.createCart(member);
            billing.addToCart(cart2, "E001", 1);  // Mixer Grinder
            billing.addToCart(cart2, "D002", 2);  // Butter x2
            billing.addToCart(cart2, "G003", 3);  // Dal x3

            // POLYMORPHISM - Electronics get 10% discount
            System.out.println("\n🛒 Cart Items with Discounts:");
            cart2.getItems().forEach(item ->
                System.out.printf("  %-30s Type: %-20s Disc: ₹%.2f%n",
                    item.getProduct().getName(),
                    item.getProduct().getProductType(),   // POLYMORPHISM
                    item.getProduct().calculateDiscount()) // POLYMORPHISM
            );

            // PAYMENT - Card
            Bill bill2 = billing.checkout(cart2, "CARD");
            System.out.println(bill2.getBillSummary());

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }

        // -------------------------------------------------------
        // OUT OF STOCK EXCEPTION DEMO
        // -------------------------------------------------------
        System.out.println("\n📋 EXCEPTION DEMO: Out of Stock");
        Customer c3 = new Customer("C003", "Amit Patel", "9012345678", "amit@email.com");
        try {
            ShoppingCart cart3 = billing.createCart(c3);
            billing.addToCart(cart3, "E004", 999); // Way more than stock!
        } catch (OutOfStockException e) {
            System.out.println("✅ OutOfStockException caught: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            System.out.println("✅ ProductNotFoundException caught: " + e.getMessage());
        }

        // -------------------------------------------------------
        // REVENUE REPORT
        // -------------------------------------------------------
        System.out.println("\n📊 REVENUE REPORT");
        System.out.printf("  Total Bills Generated : %d%n", billing.getAllBills().size());
        System.out.printf("  Total Revenue         : ₹%.2f%n", billing.getTotalRevenue());

        System.out.println("\n📦 LOW STOCK ALERT (≤ 10 units):");
        inventory.getLowStockProducts(10).forEach(p ->
            System.out.printf("  ⚠️  %-30s Stock: %d%n", p.getName(), p.getStock())
        );
    }
}
