package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BookManagerTest {

    private Shelf mockShelf;
    private BookManager bookManager;
    private InputStream sysInBackup;

    @BeforeEach
    public void setUp() {
        mockShelf = new Shelf();
        bookManager = new BookManager();
        bookManager.shelf = mockShelf;
        sysInBackup = System.in;
    }

    @AfterEach
    public void tearDown() {
        System.setIn(sysInBackup);
    }

    @Test
    public void testAddBook() {
        String simulatedInput = "New Book\nJohn Doe\n12345\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Ensure the ISBN doesn't exist initially
        assert mockShelf.searchItemByIdentifier("12345") == null;

        bookManager.addBook();

        // Verify the book was added correctly by searching it in the shelf
        Book addedBook = (Book) mockShelf.searchItemByIdentifier("12345");

        assert addedBook != null;
        assert addedBook.getTitle().equals("New Book");
        assert addedBook.getAuthor().equals("John Doe");
        assert addedBook.getIdentifier().equals("12345");
    }

    @Test
    public void testRemoveBook() {
        String simulatedInput = "2\n12345\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Add a book to the shelf for testing removal
        Book bookToRemove = new Book("Java Programming", "John Doe", "12345");
        mockShelf.addItem(bookToRemove);

        bookManager.removeBook();

        // Verify the book was removed correctly by searching it in the shelf
        LibraryItem removedBook = mockShelf.searchItemByIdentifier("12345");
        assert removedBook == null;
    }

    public static void main(String[] args) {
        BookManagerTest test = new BookManagerTest();
        test.setUp();
        test.testAddBook();
        test.testRemoveBook();
        test.tearDown();
    }
}
