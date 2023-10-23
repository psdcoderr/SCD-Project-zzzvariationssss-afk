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

public class DataLayerImport implements Import_Interface{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    //done
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


//DAL(DATA ACCESS LAYER)
//Connected to DB for Data Insertion
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
  //CheckBookByNameAndAuthor
  //The CheckBookByNameAndAuthor function checks book by title and author 
   //returns its book ID (b_id) if found
  // -1 if not found. 
    //done
    //poem add and check
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
//checks for the existence of a poem in a database based on its title and the ID of the book 
//If the poem is found, its ID is returned. 
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

//bookcheckk-function for checking book by using Function CheckBookByNameAndAuthor 
    //done
   // @Override
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
    //insertPoem-function for checking book by using Function  getPoemIdByTitleAndBook
      
   // @Override
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
   // adds a method for inserting a verse into the database
}