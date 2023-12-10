package DAL;

import DB.DbConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditTokenDALStub {

    private static final String DUMMY_VERSE = "sample verse.";
    private static final String DUMMY_OLD_TOKEN = "token1"; 
    private static final String DUMMY_NEW_TOKEN = "token2"; 

    private static EditTokenDAL editTokenDAL;

    @BeforeAll
    static void setUpBeforeClass() {
        editTokenDAL = new EditTokenDAL();
    }

    @BeforeEach
    void setUp() {
        insertDummyVerseIntoDatabase(DUMMY_VERSE);
        insertDummyTokenIntoDatabase(DUMMY_VERSE, DUMMY_OLD_TOKEN);
    }

    @AfterAll
    static void tearDownAfterClass() {
        deleteDummyVerseFromDatabase(DUMMY_VERSE);
    }

    @Test
    void testShowAllTokensInVerse() {
        List<String> tokens = editTokenDAL.showAllTokensInVerse(DUMMY_VERSE);
        assertEquals(1, tokens.size());
        assertEquals(DUMMY_OLD_TOKEN, tokens.get(0));
    }

    @Test
    void testUpdateVerseWithNewToken() {
        editTokenDAL.updateVerseWithNewToken(DUMMY_VERSE, DUMMY_OLD_TOKEN, DUMMY_NEW_TOKEN);
         List<String> updatedTokens = editTokenDAL.showAllTokensInVerse(DUMMY_VERSE);
        assertEquals(1, updatedTokens.size());
        assertEquals(DUMMY_NEW_TOKEN, updatedTokens.get(0));
    }

    @Test
    void testUpdateToken() {
        editTokenDAL.updateToken(DUMMY_VERSE, DUMMY_OLD_TOKEN, DUMMY_NEW_TOKEN);
        List<String> updatedTokens = editTokenDAL.showAllTokensInVerse(DUMMY_VERSE);
        assertEquals(1, updatedTokens.size());
        assertEquals(DUMMY_NEW_TOKEN, updatedTokens.get(0));
    }
    private void insertDummyVerseIntoDatabase(String verse) {
        try (Connection connection = DbConnection.getConnection()) {
            String insertVerseQuery = "INSERT INTO Verses (verse) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertVerseQuery)) {
                preparedStatement.setString(1, verse);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertDummyTokenIntoDatabase(String verse, String token) {
        try (Connection connection = DbConnection.getConnection()) {
            int vId = editTokenDAL.getVID(verse, connection);
            String insertTokenQuery = "INSERT INTO Tokens (v_id, token) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertTokenQuery)) {
                preparedStatement.setInt(1, vId);
                preparedStatement.setString(2, token);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDummyVerseFromDatabase(String verse) {
        try (Connection connection = DbConnection.getConnection()) {
            int vId = editTokenDAL.getVID(verse, connection);
            String deleteVerseQuery = "DELETE FROM Verses WHERE v_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteVerseQuery)) {
                preparedStatement.setInt(1, vId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
