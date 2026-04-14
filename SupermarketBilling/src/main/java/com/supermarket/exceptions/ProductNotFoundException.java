package com.supermarket.exceptions;

// CUSTOM EXCEPTIONS - OOP Exception Handling

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) { super(message); }
}
