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

import DB.DbConnection;

public class ManualAddDataLayer  implements ManualAddInterface{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    //done
 
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
                e.printStackTrace();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return bookId;
    }
    
    //done
    //poem add and check
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
                e.printStackTrace();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return poemId;
    }
    //done
    //@Override
    public int bookcheckk(String btitle, String author, String yp) {
        int bookId = CheckBookByNameAndAuthor(btitle, author);

        if (bookId == -1) {
            addData(btitle, author, yp);
            bookId = CheckBookByNameAndAuthor(btitle, author);
        }

        return bookId;
    }
    //done
   // @Override
    public int insertPoem(String title, int bookId) {
        int poemId = getPoemIdByTitleAndBook(title, bookId);
        if (poemId == -1) {
        	try (Connection con = DbConnection.getConnection()){
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
  //insertPoem-function for checking book by using Function  getPoemIdByTitleAndBook
    //done
    //@Override
    public void insertVerse(String text, int poemId) {
    	try (Connection con = DbConnection.getConnection()) {
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
}
