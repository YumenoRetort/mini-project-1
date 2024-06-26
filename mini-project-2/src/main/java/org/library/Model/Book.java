/**
 * This class is the Book class. It stores the
 * information that makes up the Book object
 *
 * @author Erin Beatrice Micaela G. Reyes
 */

package org.library.Model;

public class Book implements LibraryItem {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getIdentifier() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    // Getters and setters for author (optional for interface compliance)

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
