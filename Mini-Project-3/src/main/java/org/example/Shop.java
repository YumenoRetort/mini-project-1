package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Shop {
    private Product[] products;
    private Cart cart;
    private Scanner scanner;

    public Shop() {
        initializeProducts();
        cart = new Cart();
        scanner = new Scanner(System.in);
    }

    public void initializeProducts() {
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

    public Product[] getProducts() {
        return products;
    }

    public void displayProducts() {
        System.out.println("Available Products:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i]);
        }
    }

    public void chooseOption() {
        try {
            int optionInput;
            do {
                System.out.print("\nMain Menu: \nView Products [1], \nAdd a Product to Cart [2], \nDelete a Product from Cart [3], \nView Cart [4], \nFinalize Cart [5] \n");
                optionInput = scanner.nextInt();
                scanner.nextLine(); // Consume newline character left in the buffer

                switch (optionInput) {
                    case 1:
                        displayProducts();
                        break;
                    case 2:
                        displayProducts();
                        System.out.println("Enter the product number to add to the cart:");
                        int addProductIndex = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consume newline
                        if (addProductIndex >= 0 && addProductIndex < getProducts().length) {
                            cart.addProduct(getProducts()[addProductIndex]);
                            System.out.println("Product added to cart.");
                        } else {
                            System.out.println("Invalid product number.");
                        }
                        break;
                    case 3:
                        cart.viewCart();
                        System.out.println("Enter the product number to remove from the cart:");
                        int removeProductIndex = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consume newline
                        if (removeProductIndex >= 0 && removeProductIndex < cart.getProducts().size()) {
                            cart.removeProduct(cart.getProducts().get(removeProductIndex));
                            System.out.println("Product removed from cart.");
                        } else {
                            System.out.println("Invalid product number.");
                        }
                        break;
                    case 4:
                        cart.viewCart();
                        break;
                    case 5:
                        cart.viewCart();
                        System.out.println("Total Price: $" + cart.calculateTotalPrice());
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a number between 1 and 5.");
                        break;
                }
            } while (optionInput != 5);

        } catch (InputMismatchException e) { // Error Handling
            System.out.println("\nAn error occurred: Invalid input. Please enter a number.\n");
            scanner.nextLine(); // Clear the scanner buffer

        } catch (Exception e) {
            System.out.println("\nAn error occurred: " + e.getMessage() + "\n");
            scanner.nextLine(); // Clear the scanner buffer
        }
    }
}