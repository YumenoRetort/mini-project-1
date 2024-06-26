package org.library.Service;

import org.library.Model.LibraryItem;

import java.util.List;

public interface BookManager {
    public void initializeBooks();
    public void addBook();
    public void removeBook();
    public void searchBook();
    public void displaySearchResults(String message, List<LibraryItem> searchResults);
    public void option(int option);
}
