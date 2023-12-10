package BLL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAL.AddSingleRootDALStub;

class AddSingleRootBLLTest {

    private addSingleRootBLL rootBLL; 
    private AddSingleRootDALStub rootDALStub;

    @BeforeEach
    void setUp() {
        rootDALStub = new AddSingleRootDALStub();
        rootBLL = new addSingleRootBLL();
    }

    @Test
    void testGetSelectedSuggestedRoots() {
        int[] selectedRows = {0, 1, 2};
        List<String> result = rootBLL.getSelectedSuggestedRoots(selectedRows);
        List<String> expectedResult = Arrays.asList("root1", "root2", "root3");
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetSuggestedRoots() {
        String verse = "This is a sample verse";
        List<String> result = rootBLL.getSuggestedRoots(verse);
        List<String> expectedResult = Arrays.asList("root1", "root2");
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllVerses() {
        List<String> result = rootBLL.getAllVerses();
        List<String> expectedResult = Arrays.asList("Verse1", "Verse2");
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetTokensForVerses() {
        List<String> verses = Arrays.asList("Verse1", "Verse2");
        List<String> result = rootBLL.getTokensForVerses(verses);
        List<String> expectedResult = Arrays.asList("token1", "token2");
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetSelectedVerses() {
        int[] selectedRows = {0, 1};
        List<String> result = rootBLL.getSelectedVerses(selectedRows);
        List<String> expectedResult = Arrays.asList("Verse1", "Verse2");
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetSelectedTokens() {
        int[] selectedRows = {0, 1};
        List<String> result = rootBLL.getSelectedTokens(selectedRows);
        List<String> expectedResult = Arrays.asList("token1", "token2");
        assertEquals(expectedResult, result);
    }

    @Test
    void testAddRootsToTokens() {
        List<String> tokens = Arrays.asList("token1", "token2");
        String root = "sampleRoot";
        rootBLL.addRootsToTokens(tokens, root);
        List<String> updatedTokens = rootDALStub.testGetAllTokens();
        assertTrue(updatedTokens.contains("sampleRoot"));
    }
}
