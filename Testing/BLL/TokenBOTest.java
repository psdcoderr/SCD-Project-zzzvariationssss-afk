package BLL;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import BLL.TokenBO;
import DAL.TokenDALStub;

import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenBOTest {

    private TokenBO tokenBO;
    private TokenDALStub tokenDALStub;

    @BeforeEach
    public void setUp() {
        tokenDALStub = new TokenDALStub();
        tokenBO = new TokenBO();
    }

    @Test
    public void testGetAllVerses() {
        List<String> expectedVerses = Arrays.asList("Verse1", "Verse2", "Verse3");
        tokenDALStub.setAllVerses(expectedVerses);

        List<String> actualVerses = tokenBO.getAllVerses();

        assertEquals(expectedVerses, actualVerses);
    }

    @Test
    public void testAddNewToken() {
        String verse = "Verse1";
        String token = "Token1";

        tokenBO.addNewToken(verse, token);

        assertTrue(tokenDALStub.isAddNewTokenCalled());
        assertEquals(verse, tokenDALStub.getAddedVerse());
        assertEquals(token, tokenDALStub.getAddedToken());
    }
}


