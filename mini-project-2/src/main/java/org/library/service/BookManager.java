package org.library.service;

import org.library.model.Book;
import org.library.model.LibraryItem;

import java.util.List;

public interface BookManager {
    void initializeBooks();
    void addBook(Book book);
    void removeBook(String isbn);
    void displayAllBooks();
    LibraryItem searchBookByIdentifier(String identifier);
    List<LibraryItem> searchBooksByKeywords(String keywords);
    List<LibraryItem> searchBooksByAuthor(String author);
}
