package org.cart.controller;

import org.cart.service.Cart;
import org.cart.service.Shop;
import org.cart.service.impl.CartImpl;
import org.cart.service.impl.ShopImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Controller class to manage user interactions with the shop.
 * Handles product selection, cart operations, and displays.
 */
public class ShopController {
    private final Shop shop;
    private final Cart cart;
    private final Scanner scanner;

    /**
     * Constructs a new ShopController instance and initializes the shop, cart, and scanner.
     */
    public ShopController() {
        shop = new ShopImpl();
        cart = new CartImpl();
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu and handles user inputs to interact with the shop and cart.
     */
    public void chooseOption() {
        try {
            int optionInput;
            do {
                System.out.print("\nMain Menu:" +
                        "\nView Products [1], " +
                        "\nAdd a Product to Cart [2], " +
                        "\nDelete a Product from Cart [3], " +
                        "\nView Cart [4], " +
                        "\nFinalize Cart [5] \n\n");
                System.out.println("Enter Option:");
                optionInput = scanner.nextInt();
                scanner.nextLine(); // Consume newline character left in the buffer

                switch (optionInput) {
                    case 1:
                        shop.displayProducts();
                        break;

                    case 2:
                        shop.displayProducts();
                        System.out.println("Enter the product number to add to the cart:");
                        int addProductIndex = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consume newline

                        if (addProductIndex >= 0 && addProductIndex < shop.getProducts().length) {
                            cart.addProduct(shop.getProducts()[addProductIndex]);
                            System.out.println("\nProduct added to cart.");
                        } else {
                            System.out.println("\nInvalid product number.");
                        }
                        break;

                    case 3:
                        cart.viewCart();
                        System.out.println("Enter the product number to remove from the cart:\n");
                        int removeProductIndex = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consume newline
                        if (removeProductIndex >= 0 && removeProductIndex < cart.getProducts().size()) {
                            cart.removeProduct(cart.getProducts().get(removeProductIndex));
                            System.out.println("\nProduct removed from cart.");
                        } else {
                            System.out.println("\nInvalid product number.");
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
