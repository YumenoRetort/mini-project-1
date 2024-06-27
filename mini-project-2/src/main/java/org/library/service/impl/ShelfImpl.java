package org.library.service.impl;

import org.library.model.Book;
import org.library.model.LibraryItem;
import org.library.service.Shelf;

import java.util.*;

public class ShelfImpl implements Shelf {
    private List<LibraryItem> items;
    private Map<String, LibraryItem> itemsByIdentifier;
    private Map<String, Map<String, List<LibraryItem>>> attributeMaps;

    public ShelfImpl() {
        this.items = new ArrayList<>();
        this.itemsByIdentifier = new HashMap<>();
        this.attributeMaps = new HashMap<>();
    }

    @Override
    public void addItem(LibraryItem item) {
        if (item instanceof Book) {
            items.add(item);
            itemsByIdentifier.put(item.getIdentifier(), item);
            indexItemAttributes(item);
        }
    }

    private void indexItemAttributes(LibraryItem item) {
        indexAttribute(item, "title", item.getTitle());
        indexAttribute(item, "author", ((Book) item).getAuthor());
    }

    private void indexAttribute(LibraryItem item, String attribute, String value) {
        attributeMaps.computeIfAbsent(attribute, k -> new HashMap<>())
                .computeIfAbsent(value.toLowerCase(), k -> new ArrayList<>()).add(item);
    }

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

    private void removeIndexedAttributes(LibraryItem item) {
        removeAttribute(item, "title", item.getTitle());
        removeAttribute(item, "author", ((Book) item).getAuthor());
    }

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

    @Override
    public List<LibraryItem> searchItems(String key, String value) {
        Map<String, List<LibraryItem>> attributeMap = attributeMaps.get(key.toLowerCase());
        if (attributeMap != null) {
            List<LibraryItem> result = attributeMap.get(value.toLowerCase());
            if (result != null) {
                return new ArrayList<>(result);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public LibraryItem searchItemByIdentifier(String identifier) {
        return itemsByIdentifier.get(identifier);
    }

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