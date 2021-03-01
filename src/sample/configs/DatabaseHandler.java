package sample.configs;

import com.mysql.cj.jdbc.JdbcConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entitys.Goods;
import sample.entitys.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseHandler extends Configs {

    Connection dbConnection;

    // Метод подключения к базе данных
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionStr = "jdbc:mysql://"+ dbHost + ":" + dbPort +
                "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionStr, dbUser, dbPassword);
        return dbConnection;
    }
    //  Метод регистрации пользователя в базе данных
    public void singUpUser(User user) {
        String insert = "INSERT INTO " + ConstUser.USER_TABLE + "(" + ConstUser.USERS_FIRSTNAME + "," +
                ConstUser.USERS_LASTNAME + "," + ConstUser.USERS_LOGIN + "," + ConstUser.USERS_PASSWORD +
                ")" + "VALUES(?,?,?,?)";
        try {
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getLogin());
        preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Получить пользователя из базы данных
    public ResultSet getUser(User user) {
        ResultSet resultSet = null;
        Connection connection;
        String select = "SELECT * FROM " + ConstUser.USER_TABLE + " WHERE " + ConstUser.USERS_LOGIN +
                "=? AND " + ConstUser.USERS_PASSWORD + "=?";

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Метод записи товара в базу данных
    public void addGoods(Goods goods) {
        String insert = "INSERT INTO " + ConstGoods.GOODS_TABLE + "(" + ConstGoods.GOODS_CODE + "," +
                ConstGoods.GOODS_NAME_OF_ITEM + "," + ConstGoods.GOODS_QUANTITY + "," +
                ConstGoods.GOODS_PRICE_FOR_ONE + "," + ConstGoods.GOODS_TOTAL_PRICE +
                ")" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setInt(1, goods.getCode());
            preparedStatement.setString(2, goods.getNameOfItem());
            preparedStatement.setInt(3, goods.getQuantity());
            preparedStatement.setDouble(4, goods.getPriceForOne());
            preparedStatement.setDouble(5, goods.getTotalPrice());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
