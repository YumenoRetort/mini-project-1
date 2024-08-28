package org.library;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.service.impl.BookManagerImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainTest {

    private InputStream originalSystemIn;
    private BookManagerImpl bookManager;

    @BeforeEach
    public void setUp() {
        originalSystemIn = System.in;

        bookManager = new BookManagerImpl();
        bookManager.initializeBooks();
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalSystemIn);
    }

    @Test
    public void testMainFlow() {
        String input = "1\nN\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(new String[]{});

    }

    @Test
    public void testInvalidInput() {
        String input = "invalid\n1\nN\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(new String[]{});

    }
}
