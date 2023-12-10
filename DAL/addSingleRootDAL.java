package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.DbConnection;

public class addSingleRootDAL {

    public List<String> getSelectedSuggestedRoots(int[] selectedRows) {
        List<String> allSuggestedRoots = getSuggestedRootsForVerses(getAllVerses());
        List<String> selectedSuggestedRoots = new ArrayList<>();

        for (int row : selectedRows) {
            if (row >= 0 && row < allSuggestedRoots.size()) {
                selectedSuggestedRoots.add(allSuggestedRoots.get(row));
            }
        }

        return selectedSuggestedRoots;
    }

    private List<String> getSuggestedRootsForVerses(List<String> verses) {
        List<String> suggestedRoots = new ArrayList<>();
        for (String verse : verses) {
            suggestedRoots.addAll(suggestedRoots(verse));
        }
        return suggestedRoots;
    }

    
    
    public List<String> suggestedRoots(String verse) {
        List<String> suggestedrootsarr = new ArrayList<>();
        String words[] = verse.split("\\s+");

        for (String x : words) {
            try {
                // Assuming that net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(x).getAllRootString() is the correct method
                String root = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(x).getAllRootString();
                root = root.replaceAll("[-:]", "").trim();

                if (!root.isEmpty()) {
                    suggestedrootsarr.add(root);
                }
            } catch (Exception e) {
                // Handle the exception (e.g., print or log the error)
                e.printStackTrace();
            }
        }
        return suggestedrootsarr;
    }

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
            e.printStackTrace();
        }
        return allVerses;
    }

    public List<String> getTokensForVerses(List<String> verses) {
        List<String> tokens = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()){
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
            e.printStackTrace();
        }
        return tokens;
    }

    public List<String> getAllTokens() {
        List<String> allTokens = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()) {
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
            e.printStackTrace();
        }
        return allTokens;
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
}
