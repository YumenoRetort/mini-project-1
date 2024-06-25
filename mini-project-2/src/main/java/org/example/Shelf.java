/**
 * This class is the Shelf class. It represents a library shelf
 * that manages various library items. It provides methods to
 * add, remove, search, and display items.
 *
 * @author Erin Beatrice Micaela G. Reyes
 */

package org.example;
import java.util.*;

public class Shelf {
    private List<LibraryItem> items;
    private Map<String, LibraryItem> itemsByIdentifier;
    private Map<String, List<LibraryItem>> itemsByTitle;
    private Map<String, List<LibraryItem>> itemsByAuthor;

    /**
     * Constructor to initialize the Shelf with empty collections.
     */
    public Shelf() {
        this.items = new ArrayList<>();
        this.itemsByIdentifier = new HashMap<>();
        this.itemsByTitle = new HashMap<>();
        this.itemsByAuthor = new HashMap<>();
    }


    public void addItem(LibraryItem item) {
        items.add(item);
        itemsByIdentifier.put(item.getIdentifier(), item);

        addToHashMapList(itemsByTitle, item.getTitle().toLowerCase().split(" "), item);

        if (item instanceof Book) {
            addToHashMapList(itemsByAuthor, ((Book) item).getAuthor().toLowerCase().split(" "), item);
        }
    }

    private void addToHashMapList(Map<String, List<LibraryItem>> map, String[] keys, LibraryItem item) {
        for (String key : keys) {
            key = key.trim();
            if (!key.isEmpty()) {
                map.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
            }
        }
    }

    public void removeItem(String identifier) {
        LibraryItem removedItem = itemsByIdentifier.remove(identifier);
        if (removedItem != null) {
            items.remove(removedItem);

            // Remove words in title from the itemsByTitle map
            removeFromHashMapList(itemsByTitle, removedItem.getTitle().toLowerCase().split(" "), removedItem);

            if (removedItem instanceof Book) {
                removeFromHashMapList(itemsByAuthor, ((Book) removedItem).getAuthor().toLowerCase().split(" "), removedItem);
            }

            System.out.println("\nItem '" + removedItem.getTitle() + "' removed from the library.");
        } else {
            System.out.println("\nItem with identifier '" + identifier + "' not found. No item removed.");
        }
    }

    private void removeFromHashMapList(Map<String, List<LibraryItem>> map, String[] keys, LibraryItem item) {
        for (String key : keys) {
            key = key.trim();
            if (map.containsKey(key)) {
                map.get(key).remove(item);
                if (map.get(key).isEmpty()) {
                    map.remove(key);
                }
            }
        }
    }

    public List<LibraryItem> searchItemsByTitle(String title) {
        String[] searchTerms = title.toLowerCase().split(" ");
        return searchItems(itemsByTitle, searchTerms);
    }

    public List<LibraryItem> searchItemsByAuthor(String author) {
        String[] searchTerms = author.toLowerCase().split(" ");
        return searchItems(itemsByAuthor, searchTerms);
    }

    public LibraryItem searchItemByIdentifier(String identifier) {
        return itemsByIdentifier.get(identifier);
    }

    private List<LibraryItem> searchItems(Map<String, List<LibraryItem>> map, String[] searchTerms) {
        Set<LibraryItem> resultSet = new HashSet<>();

        for (String term : searchTerms) {
            term = term.trim();
            if (map.containsKey(term)) {
                resultSet.addAll(map.get(term));
            }
        }
        return new ArrayList<>(resultSet);
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("\nNo items in the library.");
        } else {
            System.out.println("\nItems available in the library:\n");
            for (LibraryItem item : items) {
                System.out.println("Title: " + item.getTitle());
                if (item instanceof Book) {
                    System.out.println("Author: " + ((Book) item).getAuthor());
                }
                System.out.println("Identifier: " + item.getIdentifier());
                System.out.println();
            }
        }
    }
}
