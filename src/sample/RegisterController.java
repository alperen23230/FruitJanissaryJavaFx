package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPassField;
    Connection connection;
    Alert alert;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public RegisterController(){
        connection = ConnectionUtil.connectionDB();
    }

    public void registerClicked(ActionEvent event) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPass = confirmPassField.getText();

        String regex = "((\\w)+@(([a-zA-Z])+\\.)+(com))";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);


        if(name.isEmpty() || surname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No field can be empty.!");
            alert.show();
        } else {
            if(!password.equals(confirmPass)){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password and confirm password must be the same!");
                alert.show();
            }
            else if(matcher.matches()) {
                String hashedPassword = PasswordHash.generateStrongPasswordHash(password);
                register(name,surname,username,email,hashedPassword);
                Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
                Scene scene = new Scene(parent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid email address!");
                alert.show();
            }
        }
    }

    public void register(String name, String surname, String username, String email, String hashedPassword){
        String sql = "INSERT INTO player (Name, Surname, UserName, Email, HashedPassword) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, hashedPassword);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
