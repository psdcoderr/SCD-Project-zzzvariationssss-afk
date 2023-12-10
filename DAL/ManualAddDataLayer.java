package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import DB.DbConnection;

public class ManualAddDataLayer implements ManualAddInterface {
	private static final Logger logger = Logger.getLogger(DataLayerPoemDB.class.getName());

	public void addData(String btitle, String a, String yp) {
		try (Connection connection = DbConnection.getConnection()) {
			String insertIntoTable = "INSERT INTO books (b_title, author, yp) VALUES (?, ?, ?)";
			try (PreparedStatement ValforExec = connection.prepareStatement(insertIntoTable)) {
				ValforExec.setString(1, btitle);
				ValforExec.setString(2, a);
				ValforExec.setString(3, yp);
				ValforExec.executeUpdate();
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error adding book", e);
			e.printStackTrace();
		}
	}

	public int CheckBookByNameAndAuthor(String title, String author) {
		int bookId = -1;
		try (Connection con = DbConnection.getConnection()) {
			String selectQuery = "SELECT b_id FROM Books WHERE b_title = ? AND author = ?";
			try (PreparedStatement statement = con.prepareStatement(selectQuery)) {
				statement.setString(1, title);
				statement.setString(2, author);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						bookId = resultSet.getInt("b_id");
					}
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Error getting book", e);
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			logger.log(Level.SEVERE, "Error getting book", e1);
			e1.printStackTrace();
		}
		return bookId;
	}

	private int getPoemIdByTitleAndBook(String title, int bookId) {
		int poemId = -1;
		try (Connection con = DbConnection.getConnection()) {
			String selectQuery = "SELECT pid FROM Poems WHERE p_title = ? AND b_id = ?";
			try (PreparedStatement statement = con.prepareStatement(selectQuery)) {
				statement.setString(1, title);
				statement.setInt(2, bookId);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						poemId = resultSet.getInt("pid");
					}
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Error getting poem", e);
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			logger.log(Level.SEVERE, "Error getting poem", e1);
			e1.printStackTrace();
		}
		return poemId;
	}

	@Override
	public int bookcheckk(String btitle, String author, String yp) {
		int bookId = CheckBookByNameAndAuthor(btitle, author);

		if (bookId == -1) {
			addData(btitle, author, yp);
			bookId = CheckBookByNameAndAuthor(btitle, author);
		}

		return bookId;
	}

	@Override
	public int insertPoem(String title, int bookId) {
		int poemId = getPoemIdByTitleAndBook(title, bookId);
		if (poemId == -1) {
			try (Connection con = DbConnection.getConnection()) {
				String insertQuery = "INSERT INTO Poems (p_title, b_id) VALUES (?, ?)";
				try (PreparedStatement statement = con.prepareStatement(insertQuery)) {
					statement.setString(1, title);
					statement.setInt(2, bookId);
					statement.executeUpdate();
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Error inserting poem", e);
				e.printStackTrace();
				return -1;
			}
			poemId = getPoemIdByTitleAndBook(title, bookId);
		}
		return poemId;
	}

	@Override
	public void insertVerse(String text, int poemId) {
		try (Connection con = DbConnection.getConnection()) {
			String insertQuery = "INSERT INTO Verses (verse, p_id) VALUES (?, ?)";
			try (PreparedStatement statement = con.prepareStatement(insertQuery)) {
				statement.setString(1, text);
				statement.setInt(2, poemId);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error inserting verse", e);
			e.printStackTrace();
		}
	}
}
