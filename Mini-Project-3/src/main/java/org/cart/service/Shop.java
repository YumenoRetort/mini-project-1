package org.cart.service;

import org.cart.model.Product;

public interface Shop {
    Product[] getProducts();
    void displayProducts();
}
