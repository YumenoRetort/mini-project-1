package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShelfTest {

    private Shelf shelf;

    @BeforeEach
    void setUp() {
        shelf = new Shelf();
    }

    @Test
    void addItem() {
        Book book = new Book("Java Programming", "John Doe", "12345");

        shelf.addItem(book);

        LibraryItem addedBook = shelf.searchItemByIdentifier("12345");
        assertNotNull(addedBook);
        assertEquals("Java Programming", addedBook.getTitle());
        assertEquals("John Doe", ((Book) addedBook).getAuthor());
        assertEquals("12345", addedBook.getIdentifier());
    }

    @Test
    void removeItem_existingItem() {
        Book book = new Book("Java Programming", "John Doe", "12345");
        shelf.addItem(book);

        shelf.removeItem("12345");

        LibraryItem removedBook = shelf.searchItemByIdentifier("12345");
        assertNull(removedBook);
    }

    @Test
    void removeItem_nonExistingItem() {
        shelf.removeItem("999999"); // Assuming 999999 doesn't exist
        // No assertions needed as it should gracefully handle non-existing removal
    }


    @Test
    void searchItemsByTitle_nonExistingTitle() {
        List<LibraryItem> results = shelf.searchItemsByKeywords("Nonexistent Title");

        assertTrue(results.isEmpty());
    }

    @Test
    void searchItemsByAuthor_existingAuthor() {
        Book book1 = new Book("Java Programming", "John Doe", "12345");
        Book book2 = new Book("Python Programming", "John Doe", "67890");
        shelf.addItem(book1);
        shelf.addItem(book2);

        List<LibraryItem> results = shelf.searchItemsByAuthor("John Doe");

        assertEquals(2, results.size());
        assertEquals("John Doe", ((Book) results.get(0)).getAuthor());
        assertEquals("John Doe", ((Book) results.get(1)).getAuthor());
    }

    @Test
    void searchItemsByAuthor_nonExistingAuthor() {
        List<LibraryItem> results = shelf.searchItemsByAuthor("Unknown Author");

        assertTrue(results.isEmpty());
    }

    @Test
    void searchItemByIdentifier_existingIdentifier() {
        Book book = new Book("Java Programming", "John Doe", "12345");
        shelf.addItem(book);

        LibraryItem result = shelf.searchItemByIdentifier("12345");

        assertNotNull(result);
        assertEquals("12345", result.getIdentifier());
    }

    @Test
    void searchItemByIdentifier_nonExistingIdentifier() {
        LibraryItem result = shelf.searchItemByIdentifier("999999");

        assertNull(result);
    }


}
