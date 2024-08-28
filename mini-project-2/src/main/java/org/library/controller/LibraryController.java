package org.library.controller;

import org.library.model.Book;
import org.library.model.LibraryItem;
import org.library.service.BookManager;
import org.library.service.impl.BookManagerImpl;

import java.util.List;
import java.util.Scanner;

public class LibraryController {
    private BookManager bookManager = new BookManagerImpl();
    private Scanner scanner = new Scanner(System.in);

    public void initializeBooks() {
        bookManager.initializeBooks();
    }

    public void addBook() {
        System.out.print("\nAdd a Book\n");

        while (true) {
            String title = getInput("Book Title: ", "[a-zA-Z\\d+ ]+");
            String author = getInput("Book Author: ", "[a-zA-Z ]+");
            String isbn = getInput("Book ISBN: ", "\\d+");

            Book existingBook = (Book) bookManager.searchBookByIdentifier(isbn);
            if (existingBook != null) {
                System.out.println("This ISBN matches a book in the library. Please check your information and try again.\n");
            } else {
                Book newBook = new Book(title, author, isbn);
                bookManager.addBook(newBook);
                System.out.println("\nBook '" + newBook.getTitle() + "' added to the library.\n");
                break;
            }
        }
    }

    public void removeBook() {
        System.out.println("\nTo ensure the integrity and security of the library, we only allow deletion through ISBN\n");
        System.out.println("Enter [1] to go back and to ensure you have the correct ISBN");
        System.out.println("Enter [2] if you have the book's correct ISBN");

        String userInput = scanner.nextLine();

        if ("1".equals(userInput)) {
            System.out.println("\nReturning to the main menu...");
        } else if ("2".equals(userInput)) {
            System.out.print("\nEnter the ISBN of the book to remove: ");
            String isbnToRemove = scanner.nextLine();
            bookManager.removeBook(isbnToRemove);
        }
    }

    public void searchBook() {
        System.out.print("\nEnter either keywords, author, or ISBN of a book to search: ");
        String searchTerm = scanner.nextLine();

        LibraryItem searchResult = bookManager.searchBookByIdentifier(searchTerm);
        if (searchResult != null) {
            System.out.println("Book found: " + searchResult + "\n");
            return;
        }

        List<LibraryItem> searchResults = bookManager.searchBooksByKeywords(searchTerm);
        if (!searchResults.isEmpty()) {
            displaySearchResults("Books found:", searchResults);
            return;
        }

        searchResults = bookManager.searchBooksByAuthor(searchTerm);
        if (!searchResults.isEmpty()) {
            displaySearchResults("Books found by author: '" + searchTerm + "':", searchResults);
            return;
        }

        System.out.println("Book not found.\n");
    }

    private String getInput(String prompt, String regex) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (input.matches(regex)) {
                break;
            } else {
                System.out.println("Invalid input.\n");
            }
        }
        return input;
    }

    private void displaySearchResults(String message, List<LibraryItem> searchResults) {
        System.out.println(message + "\n");
        for (LibraryItem book : searchResults) {
            System.out.println(book);
        }
        System.out.println();
    }

    public void displayAllBooks() {
        bookManager.displayAllBooks();
    }

    public void option(int option) {
        switch (option) {
            case 1:
                displayAllBooks();
                break;
            case 2:
                addBook();
                break;
            case 3:
                removeBook();
                break;
            case 4:
                searchBook();
                break;
            default:
                throw new UnsupportedOperationException("Your input was not one of the options. Please enter a valid input.");
        }
    }
}
