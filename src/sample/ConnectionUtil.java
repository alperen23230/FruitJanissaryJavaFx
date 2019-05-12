package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    //This function returns our connection
    public static Connection connectionDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fruitjanissary?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","root", "23230*");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
