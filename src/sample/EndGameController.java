package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void playAgain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainGameScene.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.close();
    }
}
