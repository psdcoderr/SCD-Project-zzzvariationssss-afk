package DAL;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataLayerDB implements DBInterfaceFacade{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    //Created a connection with database and added values to data(Moiz)
    @Override
    public void addData(String btitle, String a, String yp) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            //can directly use 	String insertQuery = "INSERT INTO books (title, author, year_passed) VALUES (btitle, a, yp)";
        	String insertIntoTable = "INSERT INTO books (b_title, author, yp) VALUES (?, ?, ?)";
            //prepared statement object created.
        	try (PreparedStatement ValforExec = connection.prepareStatement(insertIntoTable)) {
                //data set.
            	ValforExec.setString(1, btitle);
                ValforExec.setString(2, a);
                ValforExec.setString(3, yp);
                ValforExec.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //code to add data integrity(i.e. check if
    //data is there to avoid errors).
    @Override
    public boolean checkBook(String bname) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
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
    
    //Update book operation.
    @Override
    public void updateBook(String btitle, String ubtitle, String a, String yp) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
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

 
}
