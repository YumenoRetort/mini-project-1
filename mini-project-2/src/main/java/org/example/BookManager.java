/**
 * This class is the BookManager class. It interacts with
 * the Shelf class and LibraryItems interface to allow users
 * to use the methods in the shelf class.
 *
 * @author Erin Beatrice Micaela G. Reyes
 */

package org.example;
import java.util.*;

public class BookManager {
    // Instance variables
    Shelf shelf = new Shelf();
    private Scanner myScan = new Scanner(System.in);

    /**
     * Fills up the library with predefined books.
     */
    public void initializeBooks() {
        //Arrays of titles, authors, and isbns
        String[] titles = {"Python Programming", "Data Structures", "Algorithms",
                "Java Programming", "Machine Learning", "Artificial Intelligence",
                "Databases", "Software Engineering", "Operating Systems", "Networks"};
        String[] authors = {"John Doe", "Jane Smith", "Alan Turing", "Ada Lovelace",
                "Andrew Ng", "Ian Goodfellow", "Elmasri Navathe",
                "Ian Sommerville", "Abraham Silberschatz", "Andrew Tanenbaum"};
        String[] isbns = {"12345", "67890", "11223", "44556", "77889", "99000",
                "22334", "55667", "88990", "11234"};

        // Loop through the arrays to create Book objects
        for (int i = 0; i < titles.length; i++) {
            Book book = new Book(titles[i], authors[i], isbns[i]);
            shelf.addItem(book); // Add books to the shelf
        }
    }

    /**
     * Accepts input on Book title, author, and isbn.
     * Creates a new Book object to add to the shelf
     */
    public void addBook() {

        String title;
        String author;
        String isbn;

        System.out.print("\nAdd a Book ");
        System.out.println("Book Title: ");
        title = myScan.nextLine();


        while (true){
            System.out.print("Book Author: ");
            author = myScan.nextLine();

            if(author.matches("[a-zA-Z]+")){
                break;
            } else {
                System.out.println("Numbers are not a valid input for author's name. Please use roman numerals if necessary.\n");
            }
        }

        while (true) {
            System.out.print("Book ISBN: ");
            isbn = myScan.nextLine();

            if (!isbn.matches("\\d+")) {
                System.out.println("Invalid ISBN. Please enter a numeric ISBN without any alphabetic characters.\n");
            } else if (shelf.searchItemByIdentifier(isbn) != null){
                System.out.println("This ISBN already exists. Please enter a different ISBN.\n");
            }else {
                break;
            }
        }

        Book newBook = new Book(title, author, isbn);

        shelf.addItem(newBook);

        System.out.println("\nBook '" + newBook.getTitle() + "' added to the library.\n");
    }

    /**
     * Accepts input on a book's isbn
     * to remove the book from the shelf
     */
    public void removeBook() {

        System.out.println("\nTo ensure the integrity and security of the library, we only allow deletion through ISBN\n");
        System.out.println("Enter [1] to go back and to ensure you have the correct ISBN\n");
        System.out.println("Enter [2] if you have the book's correct ISBN");

        String userInput = myScan.nextLine();

        if ("1".equals(userInput)) {
            System.out.println("\nReturning to the main menu...");

        } else if ("2".equals(userInput)) {
            System.out.print("Enter the ISBN of the book to remove: ");
            String isbnToRemove = myScan.nextLine();
            shelf.removeItem(isbnToRemove);
        }
    }

    /**
     * Accepts input on a book's keywords, author, or ISBN to search for a book
     * Prioritizes ISBN > Keywords > Author.
     */
    public void searchBook() {
        System.out.print("\nEnter either keywords, author, or ISBN of a book to search: ");
        String searchTerm = myScan.nextLine();

        // Search by ISBN or exact match
        LibraryItem searchResult = shelf.searchItemByIdentifier(searchTerm);
        if (searchResult != null) {
            System.out.println("Book found: " + searchResult + "\n");
            return;
        }

        // Search by title
        var searchResults = shelf.searchItemsByTitle(searchTerm);
        if (!searchResults.isEmpty()) {
            displaySearchResults("Books found:", searchResults);
            return;
        }

        // Search by author
        searchResults = shelf.searchItemsByAuthor(searchTerm);
        if (!searchResults.isEmpty()) {
            displaySearchResults("Books found by author '" + searchTerm + "':", searchResults);
            return;
        }

        // No results found
        System.out.println("Book not found.\n");
    }

    /**
     * Prints out the LibraryItems that match with the search input
     */
    private void displaySearchResults(String message, List<LibraryItem> searchResults) {
        System.out.println(message + "\n");
        for (LibraryItem book : searchResults) {
            System.out.println(book);
        }
        System.out.println();
    }

    /**
     * Prints out the LibraryItems that match with the search input
     */
    public void option(int option) {
        switch (option) {
            case 1:
                shelf.displayItems();
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
