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

public class updateSingleRootDAL {

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
			logger.log(Level.SEVERE, "Error getting Verses", e);
            e.printStackTrace();
        }
        return allVerses;
    }

    public List<String> getTokensForVerses(List<String> verses) {
        List<String> tokens = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()) {
            String selectQuery = "SELECT token FROM Tokens WHERE v_id IN (SELECT v_id FROM Verses WHERE verse = ?)";
            try (PreparedStatement getTokens = connection.prepareStatement(selectQuery)) {
                for (String verse : verses) {
                    getTokens.setString(1, verse);
                    try (ResultSet resultSet = getTokens.executeQuery()) {
                        while (resultSet.next()) {
                            String token = resultSet.getString("token");
                            tokens.add(token);
                        }
                    }
                }
            }
        } catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting tokens", e);
            e.printStackTrace();
        }
        return tokens;
    }

    public List<String> getSelectedVerses(int[] selectedRows) {
        List<String> allVerses = getAllVerses();
        List<String> selectedVerses = new ArrayList<>();

        for (int row : selectedRows) {
            if (row >= 0 && row < allVerses.size()) {
                selectedVerses.add(allVerses.get(row));
            }
        }

        return selectedVerses;
    }

    public List<String> getSelectedTokens(int[] selectedRows) {
        List<String> allTokens = getAllTokens();
        List<String> selectedTokens = new ArrayList<>();

        for (int row : selectedRows) {
            if (row >= 0 && row < allTokens.size()) {
                selectedTokens.add(allTokens.get(row));
            }
        }

        return selectedTokens;
    }

    public List<String> getRootsForSelectedVerses(List<String> verses) {
        List<String> roots = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()){
            String selectQuery = "SELECT root FROM Roots WHERE t_id IN (SELECT t_id FROM Tokens WHERE v_id IN (SELECT v_id FROM Verses WHERE verse = ?))";
            try (PreparedStatement getRoots = connection.prepareStatement(selectQuery)) {
                for (String verse : verses) {
                    getRoots.setString(1, verse);
                    try (ResultSet resultSet = getRoots.executeQuery()) {
                        while (resultSet.next()) {
                            String root = resultSet.getString("root");
                            roots.add(root);
                        }
                    }
                }
            }
        } catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting root", e);
            e.printStackTrace();
        }
        return roots;
    }

    public List<String> getRootsForToken(String token) {
        List<String> roots = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()){
            int tokenId = getTokenId(token, connection);
            if (tokenId != -1) {
                String selectQuery = "SELECT root FROM Roots WHERE t_id = ?";
                try (PreparedStatement getRootsStatement = connection.prepareStatement(selectQuery)) {
                    getRootsStatement.setInt(1, tokenId);
                    try (ResultSet resultSet = getRootsStatement.executeQuery()) {
                        while (resultSet.next()) {
                            String root = resultSet.getString("root");
                            roots.add(root);
                        }
                    }
                }
            }
        } catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting roots", e);
            e.printStackTrace();
        }
        return roots;
    }

    
    public void addRootsToTokens(List<String> tokens, String root) {
    	try (Connection connection = DbConnection.getConnection()){
            String insertQuery = "INSERT INTO Roots (t_id, root) VALUES (?, ?)";
            try (PreparedStatement addRootStatement = connection.prepareStatement(insertQuery)) {
                for (String token : tokens) {
                    addRootStatement.setInt(1, getTokenId(token, connection));
                    addRootStatement.setString(2, root);
                    addRootStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
			logger.log(Level.SEVERE, "Error adding roots", e);
            e.printStackTrace();
        }
    }

    
    public void updateRoot(String oldRoot, String newRoot) {
    	try (Connection connection = DbConnection.getConnection()){
            String updateQuery = "UPDATE Roots SET root = ? WHERE root = ?";
            try (PreparedStatement updateRootStatement = connection.prepareStatement(updateQuery)) {
                updateRootStatement.setString(1, newRoot);
                updateRootStatement.setString(2, oldRoot);
                updateRootStatement.executeUpdate();
            }
        } catch (SQLException e) {
			logger.log(Level.SEVERE, "Error updating root", e);
            e.printStackTrace();
        }
    }

    private int getTokenId(String token, Connection connection) throws SQLException {
        String selectQuery = "SELECT t_id FROM Tokens WHERE token = ?";
        try (PreparedStatement getTokenIdStatement = connection.prepareStatement(selectQuery)) {
            getTokenIdStatement.setString(1, token);
            try (ResultSet resultSet = getTokenIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("t_id");
                }
            }
        }
        return -1;
    }

    public List<String> getAllTokens() {
        List<String> allTokens = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()){
            String selectQuery = "SELECT token FROM Tokens";
            try (PreparedStatement getTokens = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = getTokens.executeQuery()) {
                    while (resultSet.next()) {
                        String token = resultSet.getString("token");
                        allTokens.add(token);
                    }
                }
            }
        } catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting tokens", e);
            e.printStackTrace();
        }
        return allTokens;
    }
}
