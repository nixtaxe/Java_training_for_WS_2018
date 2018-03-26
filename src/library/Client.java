package library;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Client{
    ConnectionManager connectionManager;
    LoginForm loginForm;

    public Client()
    {
        connectionManager = new ConnectionManager();
        loginForm = new LoginForm(connectionManager);
    }
}
