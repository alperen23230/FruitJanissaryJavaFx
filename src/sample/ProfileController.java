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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private TableView<MyScoresViewModel> tableView;
    @FXML
    private TableColumn<MyScoresViewModel, String> usernameColumn;
    @FXML
    private TableColumn<MyScoresViewModel, String> firstNameColumn;
    @FXML
    private TableColumn<MyScoresViewModel, String> lastNameColumn;
    @FXML
    private TableColumn<MyScoresViewModel, Integer> scoreColumn;
    @FXML
    private TableColumn<MyScoresViewModel, Integer> durationColumn;
    @FXML
    private TableColumn<MyScoresViewModel, String> playDateColumn;

    private ObservableList<MyScoresViewModel> observableList = FXCollections.observableArrayList();

    private static String loggedInUsername;
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //For Connection
        connection = ConnectionUtil.connectionDB();

        //For TableView Setup
        usernameColumn.setCellValueFactory(new PropertyValueFactory<MyScoresViewModel, String>("username"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<MyScoresViewModel, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<MyScoresViewModel, String>("lastName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<MyScoresViewModel, Integer>("score"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<MyScoresViewModel, Integer>("duration"));
        playDateColumn.setCellValueFactory(new PropertyValueFactory<MyScoresViewModel, String>("playDate"));
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
        String query = "Select P.UserName, P.Name,P.Surname,G.Score,G.GameDuration,G.DateOfPlay from game G, player P where P.Id = G.PlayerId and G.PlayerId = ? ";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, loggedInPlayerId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){

            observableList.add(new MyScoresViewModel(rs.getString("UserName"), rs.getInt("Score"), rs.getInt("GameDuration"),rs.getString("DateOfPlay"),rs.getString("Name"),rs.getString("Surname")));
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
