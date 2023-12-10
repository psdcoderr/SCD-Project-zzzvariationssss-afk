package DAL;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DTO.BooksDTO;

public class DataLayerPoemDBStub {

	private static DataLayerPoemDB dataLayer;

	@BeforeAll
	static void setUpBeforeClass() {
		dataLayer = new DataLayerPoemDB();
	}

	@BeforeEach
	void setUp() {
	}

	@AfterAll
	static void tearDownAfterClass() {
	}

	@Test
	void testAddData() {
		dataLayer.insertPoem("TestPoem",3);
		assertTrue(dataLayer.CheckBookByNameAndAuthor("TestPoem", "TestAuthor") != -1);
	}

	@Test
	void testCheckBookByNameAndAuthor() {
		dataLayer.insertPoem("TestPoem2",4);
		int bookId = dataLayer.CheckBookByNameAndAuthor("TestPoem2", "TestAuthor2");
		assertTrue(bookId != -1);
	}

	@Test
	void testGetVersesForPoem() throws SQLException {
		dataLayer.insertPoem("TestPoem2",4);
		int bookId = dataLayer.CheckBookByNameAndAuthor("TestPoem3", "TestAuthor3");
		int poemId = dataLayer.insertPoem("TestPoem3", bookId);
		assertNotNull(poemId);
		List<String> verses = dataLayer.getAllVerses();
		assertNotNull(verses);
	}

	@Test
	void testBookCheckk() {
		dataLayer.insertPoem("TestPoem4",4);
		int bookId = dataLayer.CheckOrAddNewBook();
		assertTrue(bookId != -1);
	}

	@Test
	void testCheckBookk() {
		dataLayer.insertPoem("TestPoem5",6);
		int bookId = dataLayer.CheckBookByNameAndAuthor("TestPoem5", "TestAuthor5");
		assertTrue(bookId != -1);
	}

	public void setParsePoemsResult(List<String> dummyPoems) {
	}

	public void setInsertPoemsResult(List<String> dummyPoems) {
	}

	public void setShowAllBooksResult(List<BooksDTO> dummyBooks) {
	}

	public int checkBookByNameAndAuthor(String poemTitle, String author) {
		return 0;
	}

	public void setGetVersesForPoemResult(int poemId) {
	}
}
