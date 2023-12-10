package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import DB.DbConnection;

public class TokenDAL {
	private static final Logger logger = Logger.getLogger(DataLayerPoemDB.class.getName());
    public List<String> getAllVerses() {
        List<String> allVerses = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()) {
            String selectQuery = "SELECT verse FROM Verses";
            try (PreparedStatement getVerses = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = getVerses.executeQuery()) {
                    while (resultSet.next()) {
                        String verse = resultSet.getString("verse");
                        allVerses.add(verse);
                    }
                }
            }
        } catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting verses", e);
            e.printStackTrace();
        }
        return allVerses;
    }

    public int getVerseId(String verse) {
        int vId = -1;
        try (Connection connection = DbConnection.getConnection()) {
            String selectQuery = "SELECT v_id FROM Verses WHERE verse = ?";
            try (PreparedStatement getVerseIdStatement = connection.prepareStatement(selectQuery)) {
                getVerseIdStatement.setString(1, verse);
                try (ResultSet resultSet = getVerseIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        vId = resultSet.getInt("v_id");
                    }
                }
            }
        } catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting verse Id", e);
            e.printStackTrace();
        }
        return vId;
    }

    public void addNewToken(String verse, String token) {
        int vId = getVerseId(verse);
        if (vId != -1) {
        	try (Connection con = DbConnection.getConnection()){
                String query = "INSERT INTO Tokens (token, v_id) VALUES (?, ?)";
                try (PreparedStatement statement = con.prepareStatement(query)) {
                    statement.setString(1, token);
                    statement.setInt(2, vId);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
    			logger.log(Level.SEVERE, "Error adding token", e);
                e.printStackTrace();
            }
        }
    }
}
