package com.supermarket.model;

import com.supermarket.interfaces.Discountable;

// ENCAPSULATION - Customer base class
public class Customer implements Discountable {
    private String customerId;
    private String name;
    private String phone;
    private String email;
    private boolean isMember;
    private int loyaltyPoints;

    public Customer(String customerId, String name, String phone, String email) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isMember = false;
        this.loyaltyPoints = 0;
    }

    // INTERFACE IMPLEMENTATION - Discountable
    @Override
    public double applyDiscount(double amount) {
        return isMember ? amount * 0.05 : 0.0;
    }

    @Override
    public String getDiscountDescription() {
        return isMember ? "Member Discount (5%)" : "No Discount";
    }

    public void addLoyaltyPoints(int points) { this.loyaltyPoints += points; }

    // Getters & Setters
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isMember() { return isMember; }
    public void setMember(boolean member) { isMember = member; }
    public int getLoyaltyPoints() { return loyaltyPoints; }

    @Override
    public String toString() {
        return String.format("Customer[%s]: %s | %s | Member: %s | Points: %d",
                customerId, name, phone, isMember, loyaltyPoints);
    }
}


// INHERITANCE - MemberCustomer extends Customer with extra privileges
class MemberCustomer extends Customer {
    private String membershipTier; // SILVER, GOLD, PLATINUM
    private double creditBalance;

    public MemberCustomer(String customerId, String name, String phone, String email, String membershipTier) {
        super(customerId, name, phone, email);
        setMember(true);
        this.membershipTier = membershipTier;
        this.creditBalance = 0.0;
    }

    // POLYMORPHISM - Members get tiered discounts
    @Override
    public double applyDiscount(double amount) {
        return switch (membershipTier) {
            case "PLATINUM" -> amount * 0.15;
            case "GOLD"     -> amount * 0.10;
            case "SILVER"   -> amount * 0.07;
            default         -> amount * 0.05;
        };
    }

    @Override
    public String getDiscountDescription() {
        return membershipTier + " Member Discount (" + (int)(applyDiscount(100)) + "%)";
    }

    public String getMembershipTier() { return membershipTier; }
    public double getCreditBalance() { return creditBalance; }
    public void addCredit(double amount) { this.creditBalance += amount; }
}
