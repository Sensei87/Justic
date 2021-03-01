package sample.configs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.entitys.Goods;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandlerGoods extends Configs{

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        String connectionStr = "jdbc:mysql://"+ dbHost + ":" + dbPort +
                "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionStr, dbUser, dbPassword);
    }


/*
      // Еще способ доступа к базе даных
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionStr = "jdbc:mysql://"+ dbHost + ":" + dbPort +
                "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(connectionStr, dbUser, dbPassword);
        return dbConnection;
    }

    // Получить товар из базы данных
    public ObservableList<Goods> getGoods() throws Exception{
        ResultSet result = null;


        String select = "SELECT * FROM " + ConstGoods.GOODS_TABLE;
        try {

            PreparedStatement statement = getDbConnection().prepareStatement(select);

             result = statement.executeQuery();

            ObservableList<Goods> list = FXCollections.observableArrayList();

            while (result.next()) {
                    System.out.println("START!!!");
                    Goods goods = new Goods();
                    goods.setId(result.getInt(ConstGoods.GOODS_ID));
                    goods.setCode(result.getInt(ConstGoods.GOODS_CODE));
                    goods.setNameOfItem(result.getString(ConstGoods.GOODS_NAME_OF_ITEM));
                    goods.setQuantity(result.getInt(ConstGoods.GOODS_QUANTITY));
                    goods.setPriceForOne(result.getDouble(ConstGoods.GOODS_PRICE_FOR_ONE));
                    goods.setTotalPrice(result.getDouble(ConstGoods.GOODS_TOTAL_PRICE));

                    list.add(goods);

            }
            System.out.println("All records have been selected");
            return list;
        } catch (Exception e){
            return null;
        }
    }

*/


}
