package org.cart.service;

import org.cart.model.Product;

import java.util.List;

public interface Cart {
    void addProduct(Product product);
    void removeProduct(Product product);
    List<Product> getProducts();
    double calculateTotalPrice();
    void viewCart();
}
