package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DB.DbConnection;
import DTO.BooksDTO;

public class DataLayerRoots implements Roots_Interface {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";


	@Override
	public void addRoot(String root, int t_id) {
		try (Connection connection = DbConnection.getConnection()) {
			String insertQuery = "INSERT INTO Roots (root,t_id) VALUES (?,?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				preparedStatement.setString(1, root);
				preparedStatement.setInt(2, t_id);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//This is add funtion of Roots

	@Override
	public boolean checkRoot(String rName) {
		try (Connection connection = DbConnection.getConnection()) {
			String selectQuery = "SELECT root FROM Roots WHERE root = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				preparedStatement.setString(1, rName);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					return resultSet.next();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	// This is check function of roots

	@Override
	public void updateRoot(String root, String newRoot) {
		try (Connection connection = DbConnection.getConnection()) {
			String updateQuery = "UPDATE Roots SET root = ? WHERE root = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
				preparedStatement.setString(1, newRoot);
				preparedStatement.setString(2, root);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// This is updateRoots func

	@Override
	public void deleteRoot(String root) {
		try (Connection connection = DbConnection.getConnection()){
			String deleteQuery = "DELETE FROM Roots WHERE root = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
				preparedStatement.setString(1, root);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// this is deletion of roots

	}

	@Override
	public Map<Integer, String> showAllRoots() {
		Map<Integer, String> Allrootdata = new HashMap<>();

		try (Connection connection = DbConnection.getConnection()){
			String selectQuery = "SELECT r.root, COUNT(v.v_id) AS verseCount " + "FROM Roots r "
					+ "LEFT JOIN Tokens t ON r.t_id = t.t_id " + "LEFT JOIN Verses v ON t.v_id = v.v_id "
					+ "GROUP BY r.root";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						String root = resultSet.getString("root");
						int verseCount = resultSet.getInt("verseCount");
						Allrootdata.put(verseCount, root);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Allrootdata;
	}

	@Override
	public List<String> showRootData(String root) {
		List<String> versesList = new ArrayList<>();

		try (Connection connection = DbConnection.getConnection()){
			// Get all root IDs associated with the given root text
			String rootIdsQuery = "SELECT r_id FROM Roots WHERE root = ?";
			try (PreparedStatement rootIdsStatement = connection.prepareStatement(rootIdsQuery)) {
				rootIdsStatement.setString(1, root);

				try (ResultSet rootIdsResultSet = rootIdsStatement.executeQuery()) {
					while (rootIdsResultSet.next()) {
						int rootId = rootIdsResultSet.getInt("r_id");
						// Call VersesforSingleRoot with each rootId
						List<String> versesForSingleRoot = VersesforSingleRoot(rootId);
						versesList.addAll(versesForSingleRoot);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return versesList;
	}

	public List<String> VersesforSingleRoot(int rootId) {
		List<String> versesList = new ArrayList<>();

		try (Connection con = DbConnection.getConnection()) {
			String verseQuery = "SELECT v.verse " + "FROM Verses v " + "JOIN Tokens t ON v.v_id = t.v_id "
					+ "JOIN Roots r ON t.t_id = r.t_id " + "WHERE r.r_id = ?";

			try (PreparedStatement statement = con.prepareStatement(verseQuery)) {
				statement.setInt(1, rootId);

				try (ResultSet rs = statement.executeQuery()) {
					while (rs.next()) {
						String verse = rs.getString("verse");
						versesList.add(verse);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return versesList;
	}

	// Roots creator code:

	// for initial tokenize.
	public void addRootAuto(String root, int t_id, Connection connection) throws SQLException {
		String insertQuery = "INSERT INTO Roots (root, t_id) VALUES (?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
			preparedStatement.setString(1, root);
			preparedStatement.setInt(2, t_id);
			preparedStatement.executeUpdate();
		}

	}

	public void createRoots(Map<Integer, String> newmap) {
		try (Connection connection = DbConnection.getConnection()) {
	        for (Map.Entry<Integer, String> entry : newmap.entrySet()) {
	            Integer verseId = entry.getKey();
	            String words = entry.getValue();

	            String[] rootsArray = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(words)
	                    .getAllRootString().split("[-:]");

	            for (String root : rootsArray) {
	                root = root.trim();

	                if (!root.isEmpty()) {
	                    addRootAuto(root, verseId, connection);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}