package org.cart.service.impl;

import org.cart.model.Product;
import org.cart.service.Shop;

/**
 * Represents a shop that contains products.
 * Initializes products and provides methods to access them.
 */
public class ShopImpl implements Shop {
    private Product[] products;

    /**
     * Constructs a new Shop instance and initializes the products.
     */
    public ShopImpl() {
        initializeProducts();
    }

    /**
     * Initializes the products available in the shop.
     */
    private void initializeProducts() {
        // Arrays of names and prices
        String[] names = {"Chicken", "Beef", "Pork", "Lamb", "Salmon"};
        double[] prices = {100, 120, 130, 140, 150};

        // Initialize the products array
        products = new Product[names.length];

        // Loop through the arrays to create Product objects
        for (int i = 0; i < names.length; i++) {
            products[i] = new Product(names[i], prices[i]);
        }
    }

    /**
     * Returns the array of products available in the shop.
     *
     * @return An array of Product objects.
     */
    public Product[] getProducts() {
        return products;
    }

    /**
     * Displays the products available in the shop.
     */
    public void displayProducts() {
        System.out.println("Available Products:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i]);
        }
    }
}
