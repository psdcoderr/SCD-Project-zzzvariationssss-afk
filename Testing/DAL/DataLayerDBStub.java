package DAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DTO.BooksDTO;

public class DataLayerDBStub {

    private DataLayerDB dataLayer;

    @BeforeEach
    public void setUp() {
        dataLayer = new DataLayerDB();
    }

    @Test
    public void testAddData() {
        dataLayer.addData("TestBook", "TestAuthor", "2023");
        assertTrue(dataLayer.checkBook("TestBook"));
    }

    @Test
    public void testUpdateBook() {
        dataLayer.addData("TestBook2", "TestAuthor2", "2023");
        dataLayer.updateBook("TestBook2", "UpdatedTestBook", "UpdatedTestAuthor", "2024");
        BooksDTO updatedBook = dataLayer.showSingleBook("UpdatedTestBook");
        assertNotNull(updatedBook);
        assertEquals("UpdatedTestBook", updatedBook.getTitle());
        assertEquals("UpdatedTestAuthor", updatedBook.getAuthor());
        assertEquals("2024", updatedBook.getYearPassed());
    }

    @Test
    public void testDeleteBook() {
        dataLayer.addData("TestBook3", "TestAuthor3", "2023");
        dataLayer.deleteBook("TestBook3");
        assertFalse(dataLayer.checkBook("TestBook3"));
    }

    @Test
    public void testShowAllBooks() {
        List<BooksDTO> allBooks = dataLayer.showAllBooks();
        assertNotNull(allBooks);
        // Add assertions based on the expected dummy data
    }

    @Test
    public void testShowSingleBook() {
        dataLayer.addData("TestBook4", "TestAuthor4", "2023");
        BooksDTO book = dataLayer.showSingleBook("TestBook4");
        assertNotNull(book);
        assertEquals("TestBook4", book.getTitle());
        assertEquals("TestAuthor4", book.getAuthor());
        assertEquals("2023", book.getYearPassed());
    }

    @Test
    public void testShowPoems() {
        List<String> poems = dataLayer.show_poems("TestBook5");
        assertNotNull(poems);
    }
}
