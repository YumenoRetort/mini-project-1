package org.library;

import org.library.controller.LibraryController;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is the main class of the program.
 * The program is a simple library management system
 * that allows the user to view, add, remove, and search
 * for library items (limited to books at this time).
 *
 * @author Erin Beatrice Micaela G. Reyes
 */
public class Main {

    public static void main(String[] args) {

        // Initialization
        LibraryController libraryController = new LibraryController();
        Scanner myScan = new Scanner(System.in);
        String loop;

        libraryController.initializeBooks();

        System.out.print("Hello! Welcome to The Library of Alexandria! \n");

        do {
            loop = ""; // Reset loop

            // Main Menu
            try {
                System.out.print("\n" +
                        "Select a Function: \n" +
                        "View Books[1], \n" +
                        "Add a Book [2], \n" +
                        "Delete a Book [3], \n" +
                        "Search for a Book [4] \n" +
                        "\n");
                System.out.print("Enter your choice:");

                int optionInput = myScan.nextInt();
                myScan.nextLine(); // Consume newline character left in the buffer

                libraryController.option(optionInput); // Sends option to LibraryController

            } catch (InputMismatchException e) { // Error Handling
                System.out.println("\nAn error occurred Invalid input. Please enter a number.\n");
                myScan.nextLine(); // Clear the scanner buffer

            } catch (Exception e) {
                System.out.println("\nAn error occurred: " + e.getMessage() + "\n");
                myScan.nextLine(); // Clear the scanner buffer
            }

            // Loop Menu
            while(!loop.equalsIgnoreCase("Y") && !loop.equalsIgnoreCase("N")){
                System.out.println("Return to the main menu? Y/N?");
                loop = myScan.nextLine().trim();

                if(!loop.matches("[yYnN]")) {
                    System.out.println("\nInvalid input. Please type Y/N.");
                }
            }
        } while(loop.equalsIgnoreCase("Y"));

        myScan.close();
    }
}
