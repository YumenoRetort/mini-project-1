package org.cart.service.impl;

import org.cart.model.Product;
import org.cart.service.Cart;

import java.util.ArrayList;
import java.util.List;


public class CartImpl implements Cart {
    private final List<Product> products;

    public CartImpl() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public double calculateTotalPrice() {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public void viewCart() {
        if (products.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Products in your cart:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }
}
