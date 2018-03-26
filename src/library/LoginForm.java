package library;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginForm implements Initializable{
    public TextField loginField;
    public PasswordField passwordField;

    public Button signInButton;
    public Button signUpButton;

    private ConnectionManager connectionManager_;

    public LoginForm(ConnectionManager connectionManager)
    {
        connectionManager_ = connectionManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //...
    }

    @FXML
    private void signIn(Event event)
    {
        String login = loginField.getText();
        String password = passwordField.getText();
        connectionManager_.logIn(login, password);
    }

    @FXML
    private void switchToSignUp(Event event)
    {
        //...
    }
}
