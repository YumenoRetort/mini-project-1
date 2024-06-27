package org.cart;

import org.cart.controller.ShopController;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        String loop;

        System.out.print("Hello! Welcome to the supermarket!\n");

        do {
            loop = ""; // Reset loop

            ShopController shop = new ShopController();
            shop.chooseOption();

            // Loop Menu
            while (!loop.equalsIgnoreCase("Y") && !loop.equalsIgnoreCase("N")) {
                System.out.println("Return to the main menu? Y/N?");
                loop = myScan.nextLine().trim();

                if (!loop.matches("[yYnN]")) {
                    System.out.println("\nInvalid input. Please type Y/N.");
                }
            }
        } while (loop.equalsIgnoreCase("Y"));

        myScan.close();
    }
}