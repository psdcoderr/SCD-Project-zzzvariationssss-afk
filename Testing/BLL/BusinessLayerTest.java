package BLL;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BLL.BusinessLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DAL.DBInterfaceFacade;
import DTO.BooksDTO;

public class BusinessLayerTest {

    private static class TestDBInterfaceFacade implements DBInterfaceFacade {
        private List<BooksDTO> allBooksData = new ArrayList<>();

        @Test
        public void addData(String btitle, String a, String yp) {
            allBooksData.add(new BooksDTO(btitle, a, yp));
        }

        @Test
        public boolean checkBook(String bname) {
            return allBooksData.stream().anyMatch(book -> book.getTitle().equals(bname));
        }

        @Override
        public void updateBook(String btitle, String ubtitle, String a, String yp) {
            allBooksData.removeIf(book -> book.getTitle().equals(btitle));
            allBooksData.add(new BooksDTO(ubtitle, a, yp));
        }

        @Override
        public void deleteBook(String title) {
            allBooksData.removeIf(book -> book.getTitle().equals(title));
        }

        @Override
        public List<BooksDTO> showAllBooks() {
            return new ArrayList<>(allBooksData);
        }

        @Override
        public BooksDTO showSingleBook(String b_title) {
            return allBooksData.stream()
                    .filter(book -> book.getTitle().equals(b_title))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<String> show_poems(String bookName) {
            // Implement as needed for your testing
            return new ArrayList<>(); // Return an empty list for now
        }

        public void addBook(BooksDTO booksDTO) {
            allBooksData.add(booksDTO);
        }

        public void setAllBooks(List<BooksDTO> expectedBooks) {
            allBooksData = new ArrayList<>(expectedBooks);
        }
    }

    private BusinessLayer businessLayer;
    private TestDBInterfaceFacade testDB;

    @BeforeEach
    void setUp() {
        testDB = new TestDBInterfaceFacade();
        businessLayer = new BusinessLayer(testDB);
    }

    @Test
    void testAddData() {
        String btitle = "TestBook";
        String author = "TestAuthor";
        String yearPassed = "2022";

        businessLayer.addData(btitle, author, yearPassed);

        assertTrue(testDB.checkBook(btitle));
    }

    @Test
    void testCheckBook() {
        String bookName = "TestBook";

        assertFalse(businessLayer.checkBook(bookName));

        testDB.addBook(new BooksDTO("TestBook", "Author", "2022"));

        assertTrue(businessLayer.checkBook(bookName));
    }

    @Test
    void testUpdateBook() {
        String btitle = "ExistingBook";
        String ubtitle = "UpdatedBook";
        String author = "Author";
        String yearPassed = "2020";

        testDB.addBook(new BooksDTO(btitle, author, yearPassed));

        businessLayer.updateBook(btitle, ubtitle, author, yearPassed);

        assertEquals(1, testDB.showAllBooks().size());
        assertEquals(ubtitle, testDB.showAllBooks().get(0).getTitle());
    }

    @Test
    void testDeleteBook() {
        String title = "BookToDelete";
        testDB.addBook(new BooksDTO(title, "Author", "2020"));

        businessLayer.deleteBook(title);

        assertEquals(0, testDB.showAllBooks().size());
    }

    @Test
    void testShowAllBooks() {
        List<BooksDTO> expectedBooks = Arrays.asList(
                new BooksDTO("Book1", "Author1", "Year1"),
                new BooksDTO("Book2", "Author2", "Year2")
        );

        testDB.setAllBooks(expectedBooks);

        List<BooksDTO> result = businessLayer.showAllBooks();

        assertEquals(expectedBooks, result);
    }

    @Test
    void testShowSingleBook() {
        String bTitle = "TestBook";
        BooksDTO expectedBook = new BooksDTO(bTitle, "TestAuthor", "2022");

        testDB.addBook(expectedBook);

        BooksDTO result = businessLayer.showSingleBook(bTitle);

        assertEquals(expectedBook, result);
    }

    @Test
    void testShowPoems() {
        // Implement as needed for your testing
        // For example, if show_poems is part of the test, you might need a different approach.
    }
}

