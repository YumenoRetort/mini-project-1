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

    /**
     * Method to add an item to the shelf and updates the relevant maps.
     *
     * @param item The LibraryItem to be added.
     */
    public void addItem(LibraryItem item) {
        // Add item to the main list and identifier map
        items.add(item);
        itemsByIdentifier.put(item.getIdentifier(), item);

        // Add title to title map, split by words
        addToHashMapList(itemsByTitle, item.getTitle().toLowerCase().split(" "), item);

        // If the item is a Book, add it to the author map
        if (item instanceof Book) {
            addToHashMapList(itemsByAuthor, ((Book) item).getAuthor().toLowerCase().split(" "), item);
        }
    }

    /**
     * Method to add item to hasmhmaps
     *
     * @param map The map to update
     * @param keys The keys to split and add the item under.
     * @param item The item to add
     */
    private void addToHashMapList(Map<String, List<LibraryItem>> map, String[] keys, LibraryItem item) {
        for (String key : keys) {
            key = key.trim();

            if (!key.isEmpty()) {
                map.computeIfAbsent(key, k -> new ArrayList<>()).add(item); // Creates a new key if a matching key is not found
            }
        }
    }

    /**
     * Method to remove item from shelf
     *
     * @param identifier The identifier of the library item to be removed
     */
    public void removeItem(String identifier) {

        LibraryItem removedItem = itemsByIdentifier.remove(identifier);

        // Check for item
        if (removedItem != null) {
            items.remove(removedItem);

            // Remove instance of words in title from itemsByTitle map
            removeFromHashMapList(itemsByTitle, removedItem.getTitle().toLowerCase().split(" "), removedItem);

            // If a book, remove instance of author from itemsByAuthor map
            if (removedItem instanceof Book) {
                removeFromHashMapList(itemsByAuthor, ((Book) removedItem).getAuthor().toLowerCase().split(" "), removedItem);
            }

            System.out.println("\nItem '" + removedItem.getTitle() + "' removed from the library.\n");
        } else {
            System.out.println("\nItem with identifier '" + identifier + "' not found. No item removed.\n"); // No item found
        }
    }

    /**
     * Method to remove items from hashmaps
     *
     * @param map The map to delete from
     * @param keys The keys to check and delete from
     * @param item The item to remove
     */
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

    /**
     * Method that passes the keywords to the searchItems() method;
     *
     * @param keywords The keywords to search
     * @return Returns results from searchItem() Method
     */
    public List<LibraryItem> searchItemsByKeywords(String keywords) {
        String[] searchTerms = keywords.toLowerCase().split(" ");
        return searchItems(itemsByTitle, searchTerms);
    }

    /**
     * Method that passes the author name to the searchItems() method;
     *
     * @param author The author to search
     * @return Returns results from searchItem() Method
     */
    public List<LibraryItem> searchItemsByAuthor(String author) {
        String[] searchTerms = author.toLowerCase().split(" ");
        return searchItems(itemsByAuthor, searchTerms);
    }

    /**
     * Method that searches for the identifier;
     *
     * @param identifier The identifier to search
     * @return Returns results from the itemsByIdentifier map
     */
    public LibraryItem searchItemByIdentifier(String identifier) {
        return itemsByIdentifier.get(identifier);
    }

    /**
     * Method that searches for keywords and authors
     *
     * @param map The map to search in
     * @param searchTerms The term being searched
     * @return Returns an ArrayList of results
     */
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

    /**
     * Method to print out all items in the library
     */
    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("\nNo items in the library.");
        } else {
            System.out.println("\nItems available in the library:\n");
            for (LibraryItem item : items) {

                if (item instanceof Book) {
                    System.out.println("Library Item: Book");
                    System.out.println("Title: " + item.getTitle());
                    System.out.println("Author: " + ((Book) item).getAuthor());
                    System.out.println("Identifier: " + item.getIdentifier());
                    System.out.println();
                }

            }
        }
    }
}
