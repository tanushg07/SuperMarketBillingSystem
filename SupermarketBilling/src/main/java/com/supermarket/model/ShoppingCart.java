package com.supermarket.model;

import java.util.*;

// ENCAPSULATION - ShoppingCart manages CartItems
public class ShoppingCart {
    private String cartId;
    private List<CartItem> items;
    private Customer customer;

    public ShoppingCart(String cartId, Customer customer) {
        this.cartId = cartId;
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        if (!product.isAvailable(quantity)) {
            throw new IllegalStateException("Insufficient stock for: " + product.getName());
        }
        // Check if product already in cart
        for (CartItem item : items) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(String productId) {
        items.removeIf(item -> item.getProduct().getProductId().equals(productId));
    }

    public void updateQuantity(String productId, int newQuantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductId().equals(productId)) {
                if (newQuantity <= 0) {
                    removeItem(productId);
                } else {
                    item.setQuantity(newQuantity);
                }
                return;
            }
        }
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    public double getTotalTax() {
        return items.stream().mapToDouble(CartItem::calculateTax).sum();
    }

    public double getGrandTotal() {
        return items.stream().mapToDouble(CartItem::getTotalWithTax).sum();
    }

    public double getMemberDiscount() {
        return customer.isMember() ? getSubtotal() * 0.05 : 0.0;
    }

    public double getFinalTotal() {
        return getGrandTotal() - getMemberDiscount();
    }

    public boolean isEmpty() { return items.isEmpty(); }
    public List<CartItem> getItems() { return Collections.unmodifiableList(items); }
    public String getCartId() { return cartId; }
    public Customer getCustomer() { return customer; }
    public int getItemCount() { return items.size(); }
}
