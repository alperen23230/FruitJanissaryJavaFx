package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.dgc.Lease;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    @FXML
    private TableView<LeaderboardViewModel> tableView;
    @FXML
    private TableColumn<LeaderboardViewModel, String> usernameColumn;
    @FXML
    private TableColumn<LeaderboardViewModel, Integer> scoreColumn;
    @FXML
    private TableColumn<LeaderboardViewModel, Integer> durationColumn;

    private ObservableList<LeaderboardViewModel> observableList = FXCollections.observableArrayList();

    private static String loggedInUsername;
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //For DB connection
        connection = ConnectionUtil.connectionDB();

        //For TableView Setup
        usernameColumn.setCellValueFactory(new PropertyValueFactory<LeaderboardViewModel, String>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<LeaderboardViewModel, Integer>("score"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<LeaderboardViewModel, Integer>("duration"));

    }

    public void initData(String username){
        loggedInUsername = username;
        try {
            getData(loggedInUsername);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getData(String loggedInUserName) throws SQLException {
        Integer loggedInPlayerId = 0;
        String sql = "Select * from player where UserName = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,loggedInUsername);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            loggedInPlayerId = resultSet.getInt("Id");
        }
        String query = "Select P.UserName,G.Score,G.GameDuration from game G, player P where P.Id = G.PlayerId order by G.score desc ";
        preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){

            observableList.add(new LeaderboardViewModel(rs.getString("UserName"), rs.getInt("Score"), rs.getInt("GameDuration")));
        }

        tableView.setItems(observableList);
    }

    public void goToMenu(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("GameMenu.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
