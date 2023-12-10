package BLL;
import BLL.EditTokenBLL;
import DAL.EditTokenDALStub;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditTokenBLLTest {

    private static EditTokenBLL editTokenBLL;

    @BeforeAll
    static void setUp() {
        // Use the stub instead of the actual DAL for testing
        EditTokenDALStub editTokenDALStub = new EditTokenDALStub();
        editTokenBLL = new EditTokenBLL();
    }

    @Test
    @DisplayName("Test getting all tokens in verse")
    void testGetAllTokensInVerse() {
        List<String> tokens = editTokenBLL.getAllTokensInVerse("Verse1");
        assertEquals(3, tokens.size());
        assertTrue(tokens.contains("Token1"));
        assertTrue(tokens.contains("Token2"));
        assertTrue(tokens.contains("Token3"));
    }

    @Test
    @DisplayName("Test updating a token")
    void testUpdateToken() {
        assertTrue(editTokenBLL.updateToken("Verse1", "Token1", "NewToken"));
        List<String> updatedTokens = editTokenBLL.getAllTokensInVerse("Verse1");
        assertTrue(updatedTokens.contains("NewToken"));
    }

    @Test
    @DisplayName("Test updating a verse with new token")
    void testUpdateVerse() {
        editTokenBLL.updateVerse("Verse1", "Token1", "NewToken");
        List<String> updatedTokens = editTokenBLL.getAllTokensInVerse("Verse1");
        assertTrue(updatedTokens.contains("NewToken"));
    }

}