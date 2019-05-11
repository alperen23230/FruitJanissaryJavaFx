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

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public LoginController(){
        connection = ConnectionUtil.connectionDB();
    }

    public void goToRegister(ActionEvent event)throws IOException{
        System.out.println("Register");

        Parent parent = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
    public void goToGameMenu(ActionEvent event) throws IOException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        String sql = "SELECT * FROM player WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String passwordHash = resultSet.getString("HashedPassword");
            if(PasswordHash.validatePassword(password,passwordHash)){

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("GameMenu.fxml"));


                Parent parent = loader.load();
                Scene scene = new Scene(parent);

                GameMenu controller = loader.getController();
                controller.initData(username);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter your password correctly!");
                alert.show();
            }
        }


    }

}
