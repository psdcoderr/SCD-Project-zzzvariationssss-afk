package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DB.DbConnection;
import DTO.BooksDTO;

public class DataLayerDB implements DBInterfaceFacade {

	@Override
	public void addData(String btitle, String a, String yp) {
		try (Connection connection = DbConnection.getConnection()) {
			String insertIntoTable = "INSERT INTO books (b_title, author, yp) VALUES (?, ?, ?)";
			//update
			try (PreparedStatement ValsforExecution = connection.prepareStatement(insertIntoTable)) {
				ValsforExecution.setString(1, btitle);
				ValsforExecution.setString(2, a);
				ValsforExecution.setString(3, yp);
				ValsforExecution.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkBook(String bname) {
		try (Connection connection = DbConnection.getConnection()) {
			String selectQuery = "SELECT b_title FROM books WHERE b_title = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				preparedStatement.setString(1, bname);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					return resultSet.next();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updateBook(String btitle, String ubtitle, String a, String yp) {
		try (Connection connection = DbConnection.getConnection()) {
			String updateQuery = "UPDATE books SET b_title = ?, author = ?, yp = ? WHERE b_title = ?";
			try (PreparedStatement Updatevals = connection.prepareStatement(updateQuery)) {
				Updatevals.setString(1, ubtitle);
				Updatevals.setString(2, a);
				Updatevals.setString(3, yp);
				Updatevals.setString(4, btitle);
				Updatevals.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteBook(String title) {
		try (Connection connection = DbConnection.getConnection()) {
			String delval = "DELETE FROM books WHERE b_title = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(delval)) {
				preparedStatement.setString(1, title);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			e.printStackTrace();
		}
		return allBooksData;
	}

	@Override
	public BooksDTO showSingleBook(String b_title) {
		BooksDTO bookDetail = null;
		try (Connection connection = DbConnection.getConnection()) {
			String selectQuery = "SELECT b_title, author, yp FROM books WHERE b_title = ?";
			try (PreparedStatement GetBook = connection.prepareStatement(selectQuery)) {
				GetBook.setString(1, b_title);
				try (ResultSet resultSet = GetBook.executeQuery()) {
					if (resultSet.next()) {
						String title = resultSet.getString("b_title");
						String author = resultSet.getString("author");
						String yearPassed = resultSet.getString("yp");
						bookDetail = new BooksDTO(title, author, yearPassed);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookDetail;
	}

	@Override
	public List<String> show_poems(String bookName) {
	    List<String> allPoems = new ArrayList<>();
	  //update.
	    try (Connection connection = DbConnection.getConnection()) {

	        String selectBookIdQuery = "SELECT b_id FROM books WHERE b_title = ?";
	        try (PreparedStatement getBookIdStatement = connection.prepareStatement(selectBookIdQuery)) {
	            getBookIdStatement.setString(1, bookName);

	            try (ResultSet bookIdResultSet = getBookIdStatement.executeQuery()) {
	                if (bookIdResultSet.next()) {
	                    int bookId = bookIdResultSet.getInt("b_id");
	                    String selectQuery = "SELECT p_title FROM poems WHERE b_id = ?";

	                    try (PreparedStatement getPoemsStatement = connection.prepareStatement(selectQuery)) {
	                        getPoemsStatement.setInt(1, bookId);

	                        try (ResultSet resultSet = getPoemsStatement.executeQuery()) {
	                            while (resultSet.next()) {
	                                String poemTitle = resultSet.getString("p_title");
	                                allPoems.add(poemTitle);
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        return allPoems;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}

}
