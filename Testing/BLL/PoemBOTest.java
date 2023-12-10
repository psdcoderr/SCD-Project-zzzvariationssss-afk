package BLL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAL.DataLayerPoemDBStub;
import DTO.BooksDTO;

class PoemBOUnitTest {

    private DataLayerPoemDBStub poemDALStub;
    private PoemBO poemBO;

    @BeforeEach
    void setUp() {
        poemDALStub = new DataLayerPoemDBStub();
      //poemBO = new PoemBO(poemDALStub);
    }

    @Test
    void testAddData() {
        // Arrange
        String filename = "dummyFilename";
        String bookTitle = "TestPoem";
        String author = "TestAuthor";
        String yearPassed = "2023";
        List<String> dummyPoems = Arrays.asList("Poem1", "Poem2");

        // Stub the DAL method calls
        poemDALStub.setParsePoemsResult(dummyPoems);
        poemDALStub.setInsertPoemsResult(dummyPoems);

        // Act
        List<String> result = poemBO.addData(filename, bookTitle, author, yearPassed);

        // Assert
        assertEquals(dummyPoems, result);
    }

    @Test
    void testGetVersesForPoem() throws Exception {
        // Arrange
        String poemTitle = "TestPoem3";
        String author = "TestAuthor3";
        String yearPassed = "2023";
        poemDALStub.setShowAllBooksResult(Arrays.asList(new BooksDTO(poemTitle, author, yearPassed)));
        int bookId = poemDALStub.checkBookByNameAndAuthor(poemTitle, author);
        //poemDALStub.setGetVersesForPoemResult(Arrays.asList("Verse1", "Verse2"));
        List<String> result = poemBO.getAllVerses();

        assertNotNull(result);
        assertEquals(Arrays.asList("Verse1", "Verse2"), result);
    }
}
