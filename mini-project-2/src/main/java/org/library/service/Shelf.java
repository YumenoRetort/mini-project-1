package org.library.service;

import org.library.model.LibraryItem;

import java.util.List;

public interface Shelf {
    LibraryItem searchItemByIdentifier(String identifier);
    void addItem(LibraryItem item);
    void removeItem(String identifier);
    void displayItems();
}
