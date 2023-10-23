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

public class DataLayerImport {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

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

}
//DAL(DATA ACCESS LAYER)
//Connected to DB for Data Insertion