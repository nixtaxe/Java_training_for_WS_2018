package library;

import com.mysql.jdbc.Connection;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionManager implements Initializable{
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/javabase";

    public void logIn(String login, String password)
    {
        System.out.println("Connecting database...");

        try (java.sql.Connection connection = DriverManager.getConnection(url, login, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loading driver...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
    }
}
