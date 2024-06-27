package org.library.service.impl;

import org.library.model.Book;
import org.library.model.LibraryItem;
import org.library.service.BookManager;

import java.util.List;

public class BookManagerImpl implements BookManager {
    private ShelfImpl shelf = new ShelfImpl();

    @Override
    public void initializeBooks() {
        String[] titles = {"Python Programming", "Data Structures", "Algorithms", "Java Programming", "Machine Learning", "Artificial Intelligence", "Databases", "Software Engineering", "Operating Systems", "Networks"};
        String[] authors = {"John Doe", "Jane Smith", "Alan Turing", "Ada Lovelace", "Andrew Ng", "Ian Goodfellow", "Elmasri Navathe", "Ian Sommerville", "Abraham Silberschatz", "Andrew Tanenbaum"};
        String[] isbns = {"12345", "67890", "11223", "44556", "77889", "99000", "22334", "55667", "88990", "11234"};

        for (int i = 0; i < titles.length; i++) {
            Book book = new Book(titles[i], authors[i], isbns[i]);
            shelf.addItem(book);
        }
    }

    @Override
    public void addBook(Book book) {
        shelf.addItem(book);
    }

    @Override
    public void removeBook(String isbn) {
        shelf.removeItem(isbn);
    }

    @Override
    public LibraryItem searchBookByIdentifier(String identifier) {
        return shelf.searchItemByIdentifier(identifier);
    }

    @Override
    public List<LibraryItem> searchBooksByKeywords(String keywords) {
        return shelf.searchItemsByKeywords(keywords);
    }

    @Override
    public List<LibraryItem> searchBooksByAuthor(String author) {
        return shelf.searchItemsByAuthor(author);
    }

    @Override
    public void displayAllBooks() {
        shelf.displayItems();
    }
}
