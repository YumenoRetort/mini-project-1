package org.library.service.impl;

import org.library.model.Book;
import org.library.model.LibraryItem;
import org.library.service.Shelf;

import java.util.*;

/**
 * Implementation of the Shelf interface that manages library items, specifically books.
 * Allows addition, removal, and searching of books based on attributes like title and author.
 * Also supports keyword-based and author-based searches.
 *
 * @author Erin Beatrice Micaela G. Reyes
 * @version 1.0
 */
public class ShelfImpl implements Shelf {
    private List<LibraryItem> items;
    private Map<String, LibraryItem> itemsByIdentifier;
    private Map<String, Map<String, List<LibraryItem>>> attributeMaps;

    /**
     * Constructs a new ShelfImpl instance with empty item lists and attribute maps.
     */
    public ShelfImpl() {
        this.items = new ArrayList<>();
        this.itemsByIdentifier = new HashMap<>();
        this.attributeMaps = new HashMap<>();
    }

    /**
     * Adds a library item to the shelf. If the item is a book, it indexes its title and author.
     *
     * @param item The library item to add.
     */
    @Override
    public void addItem(LibraryItem item) {

        if (item instanceof Book) {
            items.add(item);
            itemsByIdentifier.put(item.getIdentifier(), item);
            indexItemAttributes(item);
        }
    }

    /**
     * Indexes the attributes of a library item for searching.
     *
     * @param item The library item to index.
     */
    private void indexItemAttributes(LibraryItem item) {
        indexAttribute(item, "title", item.getTitle());
        indexAttribute(item, "author", ((Book) item).getAuthor());
    }

    /**
     * Indexes a specific attribute of a library item with a given value.
     *
     * @param item      The library item to index.
     * @param attribute The attribute key (e.g., "title", "author").
     * @param value     The value of the attribute to index.
     */
    private void indexAttribute(LibraryItem item, String attribute, String value) {
        attributeMaps.computeIfAbsent(attribute, k -> new HashMap<>())
                .computeIfAbsent(value.toLowerCase(), k -> new ArrayList<>()).add(item);
    }

    /**
     * Removes a library item from the shelf based on its identifier.
     *
     * @param identifier The identifier of the item to remove.
     */
    @Override
    public void removeItem(String identifier) {
        LibraryItem removedItem = itemsByIdentifier.remove(identifier);

        if (removedItem != null) {
            items.remove(removedItem);
            removeIndexedAttributes(removedItem);
            System.out.println("\nItem '" + removedItem.getTitle() + "' removed from the library.\n");
        } else {
            System.out.println("\nItem with identifier '" + identifier + "' not found. No item removed.\n");
        }
    }

    /**
     * Removes indexed attributes of a library item.
     *
     * @param item The library item whose attributes should be removed.
     */
    private void removeIndexedAttributes(LibraryItem item) {
        removeAttribute(item, "title", item.getTitle());
        removeAttribute(item, "author", ((Book) item).getAuthor());
    }

    /**
     * Removes a specific attribute of a library item with a given value.
     *
     * @param item      The library item from which to remove the attribute.
     * @param attribute The attribute key (e.g., "title", "author").
     * @param value     The value of the attribute to remove.
     */
    private void removeAttribute(LibraryItem item, String attribute, String value) {
        Map<String, List<LibraryItem>> attributeMap = attributeMaps.get(attribute);
        if (attributeMap != null) {
            List<LibraryItem> itemsList = attributeMap.get(value.toLowerCase());
            if (itemsList != null) {
                itemsList.remove(item);
                if (itemsList.isEmpty()) {
                    attributeMap.remove(value.toLowerCase());
                }
            }
        }
    }

    /**
     * Searches for a library item based on its unique identifier.
     *
     * @param identifier The unique identifier of the item to search for.
     * @return The library item matching the identifier, or null if not found.
     */
    @Override
    public LibraryItem searchItemByIdentifier(String identifier) {
        return itemsByIdentifier.get(identifier);
    }

    /**
     * Displays all items currently on the shelf.
     */
    @Override
    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("\nNo items in the library.");
        } else {
            System.out.println("\nItems available in the library:\n");
            for (LibraryItem item : items) {
                System.out.println("Library Item: " + item.getClass().getSimpleName());
                System.out.println("Title: " + item.getTitle());
                System.out.println("Identifier: " + item.getIdentifier());
                if (item instanceof Book) {
                    System.out.println("Author: " + ((Book) item).getAuthor());
                }
                System.out.println();
            }
        }
    }

    /**
     * Searches for library items based on keywords present in their title or author fields.
     *
     * @param keywords The keywords to search for, separated by spaces.
     * @return A list of library items matching the keyword search criteria.
     */
    public List<LibraryItem> searchItemsByKeywords(String keywords) {
        String[] searchTerms = keywords.toLowerCase().split(" ");
        Set<LibraryItem> resultSet = new HashSet<>();

        for (String term : searchTerms) {
            term = term.trim();
            for (Map.Entry<String, Map<String, List<LibraryItem>>> entry : attributeMaps.entrySet()) {
                if (entry.getKey().equals("title") || entry.getKey().equals("author")) {
                    List<LibraryItem> itemsList = entry.getValue().get(term);
                    if (itemsList != null) {
                        resultSet.addAll(itemsList);
                    }
                }
            }
        }

        return new ArrayList<>(resultSet);
    }

    /**
     * Searches for library items authored by a specific author.
     *
     * @param author The name of the author to search for.
     * @return A list of library items authored by the specified author.
     */
    public List<LibraryItem> searchItemsByAuthor(String author) {
        Set<LibraryItem> resultSet = new HashSet<>();

        for (LibraryItem item : items) {
            if (item instanceof Book) {
                Book book = (Book) item;
                if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                    resultSet.add(book);
                }
            }
        }

        return new ArrayList<>(resultSet);
    }
}