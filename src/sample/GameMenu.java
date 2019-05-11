package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameMenu implements Initializable {

    @FXML
    private Label usernameLabel;

    private static String loggedInUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText("Welcome to Fruit Janissary " + loggedInUsername);
    }

    public void initData(String username){
        loggedInUsername = username;
        usernameLabel.setText("Welcome to Fruit Janissary " + loggedInUsername);
    }
    public void signOutClicked(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void goToGameScene(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("MainGameScene.fxml"));
        Scene scene = new Scene(parent);

        Game.initData(loggedInUsername);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.close();
    }
    public void goToProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Profile.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        ProfileController profileController = loader.getController();
        profileController.initData(loggedInUsername);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void goToLeaderboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Leaderboard.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        LeaderboardController leaderboardController = loader.getController();
        leaderboardController.initData(loggedInUsername);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
