package DAL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DataLayerPoemDB implements PoemInterface {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	@Override
	public void addData(String btitle, String a, String yp) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String insertIntoTable = "INSERT INTO books (b_title, author, yp) VALUES (?, ?, ?)";
			try (PreparedStatement ValforExec = connection.prepareStatement(insertIntoTable)) {
				ValforExec.setString(1, btitle);
				ValforExec.setString(2, a);
				ValforExec.setString(3, yp);
				ValforExec.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getPoemIdByTitleAndBook(String title, int bookId) {
		int poemId = -1;
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
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
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return poemId;
	}

	@Override
	public int CheckBookByNameAndAuthor(String title, String author) {
		int bookId = -1;
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
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
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return bookId;
	}

	@Override
	public int insertPoem(String title, int bookId) {
		int poemId = getPoemIdByTitleAndBook(title, bookId);
		if (poemId == -1) {
			try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
				String insertQuery = "INSERT INTO Poems (p_title, b_id) VALUES (?, ?)";
				try (PreparedStatement statement = con.prepareStatement(insertQuery)) {
					statement.setString(1, title);
					statement.setInt(2, bookId);
					statement.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
			poemId = getPoemIdByTitleAndBook(title, bookId);
		}
		return poemId;
	}

	@Override
	public void insertVerse(String text, int poemId) {
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String insertQuery = "INSERT INTO Verses (verse, p_id) VALUES (?, ?)";
			try (PreparedStatement statement = con.prepareStatement(insertQuery)) {
				statement.setString(1, text);
				statement.setInt(2, poemId);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int bookcheckk() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Book Name you want to add poems to:");
		String bname = sc.nextLine();
		System.out.println("Enter Author Name:");
		String aname = sc.nextLine();

		String btitle;
		String author;
		String Yearpassed;

		int bookId = CheckBookByNameAndAuthor(bname, aname);

		if (bookId == -1) {
			System.out.println("Book Not Available.\nAdd Book First");
			System.out.println("Enter Book title:");
			btitle = sc.nextLine();
			System.out.println("Enter Author:");
			author = sc.nextLine();
			System.out.println("Enter Passing date:");
			Yearpassed = sc.nextLine();
			addData(btitle, author, Yearpassed);
			bookId = CheckBookByNameAndAuthor(btitle, author);
		}
		return bookId;
	}

	@Override
	public void ParsePoems(String fileName) {
		int x = 0;
		String poemTitle = "";
		String tempVerse1 = "";
		String tempVerse2 = "";

		int book_id = bookcheckk();

		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			try (BufferedReader obj = new BufferedReader(new FileReader(fileName))) {
				String newline;

				while ((newline = obj.readLine()) != null) {
					if (newline.equals("==========")) {
						x = 1;
						continue;
					}

					if (x == 1) {
						if (newline.startsWith("[")) {
							if (newline.endsWith("]")) {
								int endBracket = newline.indexOf("]");
								poemTitle = newline.substring(1, endBracket);
							}
						} else if (newline.startsWith("(")) {
							int endIndex = newline.indexOf(")");
							if (endIndex > 0) {
								String verse = newline.substring(1, endIndex);
								String[] vp = verse.split("\\.\\.\\.");
								tempVerse1 = vp[0].trim();

								if (vp.length > 1) {
									tempVerse2 = vp[1];
								} else {
									tempVerse2 = "N/A";
								}

								int poemId = insertPoem(poemTitle, book_id);
								if (poemId != -1) {
									insertVerse(tempVerse1, poemId);
									if (!tempVerse2.equals("N/A")) {
										insertVerse(tempVerse2, poemId);
									}
								}
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
