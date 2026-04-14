package com.supermarket.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// ENCAPSULATION - Bill represents a completed transaction
public class Bill {
    private String billId;
    private ShoppingCart cart;
    private LocalDateTime billTime;
    private PaymentMethod paymentMethod;
    private String status; // PENDING, PAID, CANCELLED

    public Bill(String billId, ShoppingCart cart, PaymentMethod paymentMethod) {
        this.billId = billId;
        this.cart = cart;
        this.paymentMethod = paymentMethod;
        this.billTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public void generateBill() {
        // Deduct stock for all items
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceStock(item.getQuantity());
        }
        // Award loyalty points (1 point per ₹10 spent)
        int points = (int) (cart.getFinalTotal() / 10);
        cart.getCustomer().addLoyaltyPoints(points);
        this.status = "PAID";
    }

    public String getBillSummary() {
        StringBuilder sb = new StringBuilder();
        String line = "=".repeat(55);
        String dashes = "-".repeat(55);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");

        sb.append("\n").append(line).append("\n");
        sb.append("        🛒  FRESH MART SUPERMARKET  🛒\n");
        sb.append("         Your Neighbourhood Store\n");
        sb.append(line).append("\n");
        sb.append(String.format("Bill No    : %s%n", billId));
        sb.append(String.format("Date/Time  : %s%n", billTime.format(fmt)));
        sb.append(String.format("Customer   : %s%n", cart.getCustomer().getName()));
        sb.append(String.format("Phone      : %s%n", cart.getCustomer().getPhone()));
        sb.append(String.format("Membership : %s%n", cart.getCustomer().isMember() ? "✅ Member" : "Guest"));
        sb.append(dashes).append("\n");
        sb.append(String.format("%-30s %4s %10s%n", "ITEM", "QTY", "AMOUNT"));
        sb.append(dashes).append("\n");

        for (CartItem item : cart.getItems()) {
            sb.append(String.format("%-30s x%-3d ₹%8.2f%n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getSubtotal()));
            sb.append(String.format("  %-28s %14s%n",
                    "  [" + item.getProduct().getProductType() + " | " + item.getTaxCategory() + "]",
                    "Tax: ₹" + String.format("%.2f", item.calculateTax())));
        }

        sb.append(dashes).append("\n");
        sb.append(String.format("%-40s ₹%8.2f%n", "Subtotal:", cart.getSubtotal()));
        sb.append(String.format("%-40s ₹%8.2f%n", "Total Tax:", cart.getTotalTax()));
        if (cart.getMemberDiscount() > 0) {
            sb.append(String.format("%-40s -₹%7.2f%n", "Member Discount (5%):", cart.getMemberDiscount()));
        }
        sb.append(dashes).append("\n");
        sb.append(String.format("%-40s ₹%8.2f%n", "GRAND TOTAL:", cart.getFinalTotal()));
        sb.append(dashes).append("\n");
        sb.append(String.format("%-40s %s%n", "Payment Mode:", paymentMethod.getMethodName()));
        sb.append(String.format("%-40s %d pts%n", "Loyalty Points Earned:", (int)(cart.getFinalTotal() / 10)));
        sb.append(line).append("\n");
        sb.append("   Thank you for shopping at Fresh Mart! 💚\n");
        sb.append("       Visit again for more savings!\n");
        sb.append(line).append("\n");

        return sb.toString();
    }

    public String getBillId() { return billId; }
    public ShoppingCart getCart() { return cart; }
    public String getStatus() { return status; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getBillTime() { return billTime; }
}
