package sample;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;


public class MainGameSceneController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            Game game = new Game();
            game.gameStart();
    }

}
