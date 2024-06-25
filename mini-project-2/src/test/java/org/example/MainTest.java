package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainTest {

    private InputStream originalSystemIn;
    private BookManager bookManager;

    @BeforeEach
    public void setUp() {
        originalSystemIn = System.in;

        bookManager = new BookManager();
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

    @Test
    public void testExceptionHandling() {
        String input = "100\nN\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Main.main(new String[]{});

    }


}
