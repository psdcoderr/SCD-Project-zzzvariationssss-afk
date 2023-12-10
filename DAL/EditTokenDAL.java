package DAL;

import DB.DbConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditTokenDAL {
	private static final Logger logger = Logger.getLogger(DataLayerPoemDB.class.getName());

	public List<String> showAllTokensInVerse(String verse) {
		List<String> tokens = new ArrayList<>();

		try (Connection connection = DbConnection.getConnection()) {
			int v_id = getVID(verse, connection);

			if (v_id > 0) {
				String selectQuery = "SELECT token FROM Tokens WHERE v_id = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
					preparedStatement.setInt(1, v_id);

					try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

	public int getVID(String verse, Connection connection) {
		int v_id = -1;

		try {
			String selectQuery = "SELECT v_id FROM Verses WHERE verse = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				preparedStatement.setString(1, verse);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						v_id = resultSet.getInt("v_id");
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting verse_id", e);
			e.printStackTrace();
		}

		return v_id;
	}

	public void updateVerseWithNewToken(String verse, String oldToken, String newToken) {
		try (Connection connection = DbConnection.getConnection()) {
			int vId = getVID(verse, connection);

			if (vId > 0) {
				List<String> tokens = showAllTokensInVerse(verse);

				for (int i = 0; i < tokens.size(); i++) {
					if (tokens.get(i).equals(oldToken)) {
						tokens.set(i, newToken);
					}
				}
				StringBuilder updatedVerse = new StringBuilder();
				for (String token : tokens) {
					updatedVerse.append(token).append(" ");
				}
				updatedVerse.setLength(updatedVerse.length() - 1);

				String updateQuery = "UPDATE Verses SET verse = ? WHERE v_id = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
					preparedStatement.setString(1, updatedVerse.toString());
					preparedStatement.setInt(2, vId);

					preparedStatement.executeUpdate();
				}
			} else {
				
				System.out.println("Verse not found in the database.");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting verse", e);
			e.printStackTrace();
		}
	}

	public void updateToken(String verse, String oldToken, String newToken) {
		try (Connection connection = DbConnection.getConnection()) {
			int vId = getVID(verse, connection);

			if (vId > 0) {
				String updateQuery = "UPDATE Tokens SET token = ? WHERE token = ? AND v_id = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
					preparedStatement.setString(1, newToken);
					preparedStatement.setString(2, oldToken);
					preparedStatement.setInt(3, vId);

					preparedStatement.executeUpdate();
				}
			} else {
				System.out.println("Verse not found in the database.");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error updating verse", e);
			e.printStackTrace();
		}
	}

}
