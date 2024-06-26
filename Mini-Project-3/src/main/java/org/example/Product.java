package org.example;

public class Product {
    private String name;
    private double price;

    public Product(String name, double price) throws IllegalArgumentException  {

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ": $" + price;
    }
}
