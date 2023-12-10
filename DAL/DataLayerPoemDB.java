package DAL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.util.logging.Level;
import java.util.logging.Logger;

import DB.DbConnection;
import DTO.BooksDTO;

public class DataLayerPoemDB implements PoemInterface {

	DataLayerDB BookDAO = new DataLayerDB();

	private static final Logger logger = Logger.getLogger(DataLayerPoemDB.class.getName());
	public DataLayerPoemDB() {
	}

	//renamed removed comment.
	@Override
	public void addNewBook(String btitle, String a, String yp) {
		BookDAO.addData(btitle, a, yp);
	}

	private int getPoemIdByTitleAndBook1(String title, int bookId) {
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
				logger.log(Level.SEVERE, "Error getting poem ID by title and book ID", e);
			}
		} catch (SQLException e1) {
			logger.log(Level.SEVERE, "Error getting poem ID by title and book ID", e1);
		}
		return poemId;
	}

	@Override
	public int CheckBookByNameAndAuthor(String title, String author) {
		int bookId = -1;
		try (Connection con = DbConnection.getConnection())  {
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
				logger.log(Level.SEVERE, "Error checking book by name and author", e);
			}
		} catch (SQLException e1) {
			logger.log(Level.SEVERE, "Error checking book by name and author", e1);
		}
		return bookId;
	}

	//change
	@Override
	public int CheckOrAddNewBook() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Book Name you want to add poems to:");
		String bookName = sc.nextLine();
		System.out.println("Enter Author Name:");
		String authorName = sc.nextLine();
	
		//change
		String bookTitle;
		String author;
		//change
		String yearPassed;
//change
		int bookId = getExistingBookID(bookName, authorName);

		if (bookId == -1) {
			System.out.println("Book Not Available.\nAdd Book First");
			System.out.println("Enter Book title:");
			bookTitle = sc.nextLine();
			System.out.println("Enter Author:");
			author = sc.nextLine();
			System.out.println("Enter Passing date:");
			yearPassed = sc.nextLine();
			//change
			addNewBook(bookTitle, author, yearPassed);
			//changed
			bookId = getExistingBookID(bookTitle, author);
		}
		return bookId;
	}

	//changed
	@Override
	public int getExistingBookID(String title, String author) {
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
				e.printStackTrace();
				logger.log(Level.SEVERE, "Error getting book by name and author", e);
			}
		} catch (SQLException e1) {
			logger.log(Level.SEVERE, "Error getting book by name and author", e1);
			e1.printStackTrace();
		}
		return bookId;
	}

	//Change start
	@Override
	public int insertPoem(String title, int bookId) {
	    int poemId = getPoemIdByTitleAndBook1(title, bookId);
	    if (poemId == -1) {
	        try (Connection con = DbConnection.getConnection())  {
	            poemId = insertPoemIntoTable(con, title, bookId);
	            insertParsedVersesIntoTable(con, title, poemId);
	        } catch (SQLException e) {
	            e.printStackTrace();
				logger.log(Level.SEVERE, "Error Inserting Poem", e);
	        }
	    }
	    return poemId;
	}

	private int insertPoemIntoTable(Connection con, String title, int bookId) throws SQLException {
	    int poemId = -1;
	    String insertPoemQuery = "INSERT INTO Poems (b_id, p_title) VALUES (?, ?)";
	    try (PreparedStatement poemStatement = con.prepareStatement(insertPoemQuery,
	            PreparedStatement.RETURN_GENERATED_KEYS)) {
	        poemStatement.setInt(1, bookId);
	        poemStatement.setString(2, title);
	        poemStatement.executeUpdate();

	        ResultSet generatedKeys = poemStatement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            poemId = generatedKeys.getInt(1);
	        }
	    }
	    return poemId;
	}

	private void insertParsedVersesIntoTable(Connection con, String title, int poemId) throws SQLException {
	    String insertVerseQuery = "INSERT INTO Verses (verse, p_id) VALUES (?, ?)";
	    try (PreparedStatement verseStatement = con.prepareStatement(insertVerseQuery)) {
	        String[] verses = title.split("\\.\\.\\.");
	        verseStatement.setString(1, verses[0].trim());
	        verseStatement.setInt(2, poemId);
	        verseStatement.executeUpdate();

	        if (verses.length > 1) {
	            verseStatement.setString(1, verses[1].trim());
	            verseStatement.setInt(2, poemId);
	            verseStatement.executeUpdate();
	        }
	    }
	}
//change end. Long function code smell removed!
	
//change
	@Override
	public void insertVerseManually(String text, int poemId) {
		try (Connection con = DbConnection.getConnection()) {
			String insertQuery = "INSERT INTO Verses (verse, p_id) VALUES (?, ?)";
			try (PreparedStatement statement = con.prepareStatement(insertQuery)) {
				statement.setString(1, text);
				statement.setInt(2, poemId);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error Inserting verse", e);
			e.printStackTrace();
		}
	}

	@Override
	public List<String> ParsePoems(String fileName) {
		int x = 0;
		String poemTitle = "";
		String tempVerse1 = "";
		String tempVerse2 = "";

		int book_id = CheckOrAddNewBook();
		// has all verses.
		List<String> parsedVerses = new ArrayList<>();

		try (Connection con = DbConnection.getConnection())  {
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

								int poemId = insertPoem(poemTitle, book_id);
								if (poemId != -1) {
									insertVerseManually(tempVerse1, poemId);
									parsedVerses.add(tempVerse1);

									if (vp.length > 1) {
										tempVerse2 = vp[1].trim();
										insertVerseManually(tempVerse2, poemId);
										parsedVerses.add(tempVerse2);
									}
								}
							}
						}
					}
				}
				tokenize();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error parsing poems", e);
				e.printStackTrace();
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error parsing poems", e);
			e.printStackTrace();
		}

		return parsedVerses;
	}

	@Override
	public List<BooksDTO> showAllBooks() {
		List<BooksDTO> allBooksData = new ArrayList<>();
		try (Connection connection = DbConnection.getConnection()) {
			String selectQuery = "SELECT b_title, author, yp FROM books";
			try (PreparedStatement GetBooks = connection.prepareStatement(selectQuery)) {
				try (ResultSet resultSet = GetBooks.executeQuery()) {
					while (resultSet.next()) {
						String title = resultSet.getString("b_title");
						String author = resultSet.getString("author");
						String yearPassed = resultSet.getString("yp");
						BooksDTO book = new BooksDTO(title, author, yearPassed);
						allBooksData.add(book);
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error Showing Books", e);
			e.printStackTrace();
		}
		return allBooksData;
	}

	public void tokenize() {

		Map<Integer, String> allVersesWithIds = getAllVersesWithIds();
		DataLayerRoots rootsDAO = new DataLayerRoots();
		Map<Integer, String> newmap = new HashMap<>();

		try (Connection con = DbConnection.getConnection()) {

			for (Map.Entry<Integer, String> entry : allVersesWithIds.entrySet()) {

				Integer verseId = entry.getKey();
				String verseText = entry.getValue();

				String[] words = verseText.split("\\s+");

				for (String x : words) {
					insertToken(x, verseId, con);
				}

			}

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error tokenizing", e);
			e.printStackTrace();
		}

		newmap = getAllTokensWithIds();

		rootsDAO.createRoots(newmap);

	}

	public void insertToken(String token, int v_id, Connection con) throws SQLException {

		String insertTokenQuery = "INSERT INTO Tokens (token, v_id) VALUES (?, ?)";
		try (PreparedStatement tokenStatement = con.prepareStatement(insertTokenQuery)) {
			tokenStatement.setString(1, token);
			tokenStatement.setInt(2, v_id);
			tokenStatement.executeUpdate();
		}
	}

	public Map<Integer, String> getAllTokensWithIds() {
		Map<Integer, String> allTokens = new HashMap<>();
		try (Connection connection = DbConnection.getConnection()) {
			String selectQuery = "SELECT t_id, token FROM Tokens";
			try (PreparedStatement getVerses = connection.prepareStatement(selectQuery)) {
				try (ResultSet resultSet = getVerses.executeQuery()) {
					while (resultSet.next()) {
						int tokenId = resultSet.getInt("t_id");
						String token = resultSet.getString("token");
						allTokens.put(tokenId, token);
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting tokens", e);
			e.printStackTrace();
		}
		return allTokens;
	}

	public Map<Integer, String> getAllVersesWithIds() {
		Map<Integer, String> allVerses = new HashMap<>();
		try (Connection connection = DbConnection.getConnection())  {
			String selectQuery = "SELECT v_id, verse FROM Verses";
			try (PreparedStatement getVerses = connection.prepareStatement(selectQuery)) {
				try (ResultSet resultSet = getVerses.executeQuery()) {
					while (resultSet.next()) {
						int verseId = resultSet.getInt("v_id");
						String verse = resultSet.getString("verse");
						allVerses.put(verseId, verse);
					}
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error getting verses", e);
			e.printStackTrace();
		}
		return allVerses;
	}

	@Override
	public List<String> getAllVerses() {
		List<String> allVerses = new ArrayList<>();
		try (Connection connection = DbConnection.getConnection()){
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
			logger.log(Level.SEVERE, "Error getting all verses", e);
			e.printStackTrace();
		}
		return allVerses;
	}

	@Override
	public void updateBook(String btitle, String ubtitle, String a, String yp) {
		try (Connection connection = DbConnection.getConnection()){
			String updateQuery = "UPDATE books SET b_title = ?, author = ?, yp = ? WHERE b_title = ?";
			try (PreparedStatement Updatevals = connection.prepareStatement(updateQuery)) {
				Updatevals.setString(1, ubtitle);
				Updatevals.setString(2, a);
				Updatevals.setString(3, yp);
				Updatevals.setString(4, btitle);
				Updatevals.executeUpdate();
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error updating book", e);
			e.printStackTrace();
		}
	}

	@Override
	public void updatePoem(String oldTitle, String newTitle, String newAuthor, String newYearPassed) {
		int bookId = CheckBookByNameAndAuthor(oldTitle, newAuthor);
		int oldBookId = CheckBookByNameAndAuthor(oldTitle, "");
		if (bookId == -1) {
			if (oldBookId != -1) {
				updateBook(oldTitle, newTitle, newAuthor, newYearPassed);
			}
		} else {
			System.out.println("Book with new details already exists.");
			logger.log(Level.SEVERE, "Error . Book Exists");
		}
	}

	@Override
	public void deletePoem(String title) {
		int bookId = CheckBookByNameAndAuthor(title, "");
		if (bookId != -1) {
			try (Connection con = DbConnection.getConnection()){
				String deletePoemQuery = "DELETE FROM Poems WHERE b_id = ?";
				try (PreparedStatement deletePoemStatement = con.prepareStatement(deletePoemQuery)) {
					deletePoemStatement.setInt(1, bookId);
					deletePoemStatement.executeUpdate();
				}

				String deleteBookQuery = "DELETE FROM Books WHERE b_id = ?";
				try (PreparedStatement deleteBookStatement = con.prepareStatement(deleteBookQuery)) {
					deleteBookStatement.setInt(1, bookId);
					deleteBookStatement.executeUpdate();
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Error Book not found", e);
				e.printStackTrace();
			}
		} else {
			System.out.println("Book not found for deletion.");
		}
	}

	@Override
	public List<String> viewAllPoems() {
		List<String> poems = new ArrayList<>();
		try (Connection con = DbConnection.getConnection()){
			String selectAllPoemsQuery = "SELECT p_title FROM Poems";
			try (PreparedStatement statement = con.prepareStatement(selectAllPoemsQuery);
					ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					poems.add(resultSet.getString("p_title"));
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error Poems not recieved", e);
			e.printStackTrace();
		}
		return poems;
	}

	@Override
   public List<String> viewSinglePoem(String title) {
	       List<String> verses = new ArrayList<>();
	       try (Connection con = DbConnection.getConnection()) {
	           int bookId = CheckBookByNameAndAuthor(title, "");
	           if (bookId != -1) {
	               String selectSinglePoemQuery = "SELECT verse FROM Verses v INNER JOIN Poems p ON v.p_id = p.pid WHERE p_title = ?";
	               try (PreparedStatement statement = con.prepareStatement(selectSinglePoemQuery)) {
	                   statement.setString(1, title);
	                   try (ResultSet resultSet = statement.executeQuery()) {
	                       while (resultSet.next()) {
	                           verses.add(resultSet.getString("verse"));
	                       }
	                   }
	               }
	           } else {
	               System.out.println("Poem not found.");
	           }
	       } catch (SQLException e) {
				logger.log(Level.SEVERE, "Error poem not found", e);
	           e.printStackTrace();
	       }
	       return verses;
	}
	
	//change
	@Override
	public List<String> insertPoemsManually(List<String> poems, String bookTitle, String author, String yearPassed) {
	    List<String> verseList = new ArrayList<>();
	    int bookId = CheckBookByNameAndAuthor(bookTitle, author);

	    if (bookId == -1) {
	    	//change
	        addNewBook(bookTitle, author, yearPassed);
	        bookId = CheckBookByNameAndAuthor(bookTitle, author);
	    }

	    for (String poem : poems) {
	        int poemId = insertPoem(poem, bookId);
	        if (poemId != -1) {
	            insertVerseManually(poem, poemId);
	            verseList.add(poem);
	        }
	    }
	    return verseList;
	}


}