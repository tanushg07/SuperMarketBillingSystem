package com.supermarket.service;

import com.supermarket.model.*;
import com.supermarket.exceptions.*;

import java.util.*;

// SERVICE CLASS - Handles billing operations
public class BillingService {
    private Map<String, Bill> bills;
    private int billCounter;
    private InventoryService inventoryService;

    public BillingService() {
        this.bills = new LinkedHashMap<>();
        this.billCounter = 1000;
        this.inventoryService = InventoryService.getInstance();
    }

    public ShoppingCart createCart(Customer customer) {
        String cartId = "CART-" + System.currentTimeMillis();
        return new ShoppingCart(cartId, customer);
    }

    public void addToCart(ShoppingCart cart, String productId, int quantity)
            throws ProductNotFoundException, OutOfStockException {
        Product product = inventoryService.getProduct(productId);
        if (!product.isAvailable(quantity)) {
            throw new OutOfStockException("Only " + product.getStock() + " units of '" + product.getName() + "' available.");
        }
        cart.addItem(product, quantity);
    }

    public Bill checkout(ShoppingCart cart, String paymentType)
            throws EmptyCartException, PaymentFailedException {
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cannot checkout with an empty cart!");
        }

        PaymentMethod payment = PaymentMethod.of(paymentType);
        boolean paid = payment.processPayment(cart.getFinalTotal());

        if (!paid) {
            throw new PaymentFailedException("Payment processing failed. Please try again.");
        }

        String billId = "BILL-" + (++billCounter);
        Bill bill = new Bill(billId, cart, payment);
        bill.generateBill();
        bills.put(billId, bill);

        return bill;
    }

    public Bill getBill(String billId) throws BillNotFoundException {
        Bill bill = bills.get(billId);
        if (bill == null) throw new BillNotFoundException("Bill not found: " + billId);
        return bill;
    }

    public List<Bill> getAllBills() {
        return new ArrayList<>(bills.values());
    }

    public double getTotalRevenue() {
        return bills.values().stream()
                .filter(b -> "PAID".equals(b.getStatus()))
                .mapToDouble(b -> b.getCart().getFinalTotal())
                .sum();
    }
}
