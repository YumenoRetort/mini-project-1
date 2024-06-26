/**
 * This is the LibraryItem interface. It acts as the
 * blueprint for the current library item Book and
 * any future library item. It exists under the
 * assumption that despite how varied the items
 * in a library are in terms of attributes, all items
 * have a title and identifier.
 *
 * @author Erin Beatrice Micaela G. Reyes
 */

package org.library.Model;

public interface LibraryItem {
    String getTitle();
    String getIdentifier();
}
