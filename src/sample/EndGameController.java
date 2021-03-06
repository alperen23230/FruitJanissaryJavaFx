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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {


    @FXML
    private Label scoreLbl;
    @FXML
    private Label durationLbl;
    @FXML
    private Label highScoreLbl;

    private Integer score;
    private Integer duration;
    private static String loggedInUsername;

    private PreparedStatement preparedStatement;
    private Connection connection;
    private Integer loggedInPlayerId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = ConnectionUtil.connectionDB();
    }

    public void playAgain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("MainGameScene.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.close();
    }
    public void initData(Integer score, Integer duration,String username)  {
        this.score = score;
        this.duration = duration;
        loggedInUsername = username;
        scoreLbl.setText(score.toString() + " " + "Point");
        durationLbl.setText(duration.toString() + " " + "Second");
        try {
            gameDataToDB(loggedInUsername,score,duration);
            getMyHighScore(loggedInPlayerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void gameDataToDB(String username, Integer score, Integer duration) throws SQLException {
        String sql = "Select * from player where UserName = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            loggedInPlayerId = resultSet.getInt("Id");
        }
        String query = "INSERT INTO game (PlayerId, Score, GameDuration) VALUES (?,?,?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,loggedInPlayerId);
        preparedStatement.setInt(2,score);
        preparedStatement.setInt(3,duration);
        preparedStatement.execute();
    }
    public void getMyHighScore(Integer playerId) throws SQLException {
        String sql = "Select Max(Score) from fruitjanissary.game where PlayerId = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, playerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            highScoreLbl.setText("Your High Score: " + " " + resultSet.getInt(1));
        }
    }

    public void goToMainMenu(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("GameMenu.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
