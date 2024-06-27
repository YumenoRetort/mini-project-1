package org.library.service.impl;

import org.library.model.Book;
import org.library.model.LibraryItem;
import org.library.service.BookManager;

import java.util.List;

/**
 * Implementation of the BookManager interface that manages operations related to books on a shelf.
 * Provides methods to initialize books, add, remove, search by identifier, keywords, and author,
 * as well as display all books.
 *
 * This class uses ShelfImpl to manage the operations of books.
 *
 * @author Erin Beatrice Micaela G. Reyes
 * @version 1.0
 */
public class BookManagerImpl implements BookManager {
    private ShelfImpl shelf = new ShelfImpl();

    /**
     * Initializes a predefined set of books and adds them to the shelf during initialization.
     */
    @Override
    public void initializeBooks() {
        String[] titles = {"Python Programming", "Data Structures", "Algorithms",
                "Java Programming", "Machine Learning", "Artificial Intelligence",
                "Databases", "Software Engineering", "Operating Systems", "Networks"};
        String[] authors = {"John Doe", "Jane Smith", "Alan Turing", "Ada Lovelace",
                "Andrew Ng", "Ian Goodfellow", "Elmasri Navathe", "Ian Sommerville",
                "Abraham Silberschatz", "Andrew Tanenbaum"};
        String[] isbns = {"12345", "67890", "11223", "44556", "77889", "99000", "22334",
                "55667", "88990", "11234"};

        for (int i = 0; i < titles.length; i++) {
            Book book = new Book(titles[i], authors[i], isbns[i]);
            shelf.addItem(book);
        }
    }

    /**
     * Adds a book to the shelf.
     *
     * @param book The book to add.
     */
    @Override
    public void addBook(Book book) {
        shelf.addItem(book);
    }

    /**
     * Removes a book from the shelf based on its ISBN.
     *
     * @param isbn The ISBN of the book to remove.
     */
    @Override
    public void removeBook(String isbn) {
        shelf.removeItem(isbn);
    }

    /**
     * Searches for a book on the shelf by its unique identifier.
     *
     * @param identifier The identifier of the book to search for.
     * @return The book matching the identifier, or null if not found.
     */
    @Override
    public LibraryItem searchBookByIdentifier(String identifier) {
        return shelf.searchItemByIdentifier(identifier);
    }

    /**
     * Searches for books on the shelf based on keywords present in their titles or authors' names.
     *
     * @param keywords The keywords to search for, separated by spaces.
     * @return A list of books matching the keyword search criteria.
     */
    @Override
    public List<LibraryItem> searchBooksByKeywords(String keywords) {
        return shelf.searchItemsByKeywords(keywords);
    }

    /**
     * Searches for books authored by a specific author on the shelf.
     *
     * @param author The name of the author to search for.
     * @return A list of books authored by the specified author.
     */
    @Override
    public List<LibraryItem> searchBooksByAuthor(String author) {
        return shelf.searchItemsByAuthor(author);
    }

    /**
     * Displays all books currently on the shelf.
     */
    @Override
    public void displayAllBooks() {
        shelf.displayItems();
    }
}
