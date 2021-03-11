package sample.configs;

import sample.entitys.Goods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandlerGoods extends Configs{

    public Connection getConnection() throws ClassNotFoundException, SQLException{
        String connectionStr = "jdbc:mysql://"+ dbHost + ":" + dbPort +
                "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionStr, dbUser, dbPassword);
    }
    public void addNewGoods(Goods goods) {
        String insert = "INSERT INTO " + ConstGoods.GOODS_TABLE + "( " + ConstGoods.GOODS_CODE + ", " +
                ConstGoods.GOODS_NAME_OF_ITEM + ", " + ConstGoods.GOODS_QUANTITY + ", " +
                ConstGoods.GOODS_PRICE_FOR_ONE + ", " + ConstGoods.GOODS_TOTAL_PRICE + ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(insert);
            statement.setInt(1, goods.getCode());
            statement.setString(2, goods.getNameOfItem());
            statement.setInt(3, goods.getQuantity());
            statement.setDouble(4, goods.getPriceForOne());
            statement.setDouble(5, goods.getTotalPrice());

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
