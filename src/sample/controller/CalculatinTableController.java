package sample.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import sample.configs.ConstGoods;
import sample.configs.DatabaseHandler;
import sample.configs.DatabaseHandlerGoods;
import sample.entitys.Goods;

import static java.lang.Integer.parseInt;

public class CalculatinTableController {

    Connection con = null;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Goods> goodsTable;

    @FXML
    private TableColumn<Goods, Integer> idgoods;

    @FXML
    private TableColumn<Goods, Integer> goods_code;

    @FXML
    private TableColumn<Goods, String> goods_name;

    @FXML
    private TableColumn<Goods, Integer> goods_quantity;

    @FXML
    private TableColumn<Goods, Double> goods_price_one;

    @FXML
    private TableColumn<Goods, Double> goods_price_total;

    @FXML
    void initialize() throws Exception {
        assert goodsTable != null;
        goodsTable.setEditable(true);
        changGoods();

        idgoods.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("id"));
        goods_code.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("code"));
        goods_name.setCellValueFactory(new PropertyValueFactory<Goods, String>("nameOfItem"));
        goods_price_one.setCellValueFactory(new PropertyValueFactory<Goods, Double>("priceForOne"));
        goods_quantity.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("quantity"));
        goods_price_total.setCellValueFactory(new PropertyValueFactory<Goods, Double>("totalPrice"));

        DatabaseHandlerGoods handlerGoods = new DatabaseHandlerGoods();
        try {
            con = handlerGoods.getConnection();
            buildData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private ObservableList<Goods> data;
    // Вывод данных из БД
    public void buildData(){

        data = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM " + ConstGoods.GOODS_TABLE;
            ResultSet rs = con.createStatement().executeQuery(SQL);

            while (rs.next()) {
                Goods goods = new Goods();
                goods.idProperty().set(rs.getInt("idgoods"));
                goods.codeProperty().set(rs.getInt("code"));
                goods.nameOfItemProperty().set(rs.getString("name_of_item"));
                goods.priceForOneProperty().set(rs.getDouble("price_for_one"));
                goods.quantityProperty().set(rs.getInt("quantity"));
                goods.totalPriceProperty().set(rs.getDouble("total_price"));

                data.add(goods);
            }
            goodsTable.setItems(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // Изменения данных таблицы
    public void changGoods() {

        idgoods.setCellFactory(TextFieldTableCell.<Goods, Integer>forTableColumn(new IntegerStringConverter()));
        idgoods.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, Integer> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                        .setId(g.getNewValue());
            }
        });

        goods_code.setCellFactory(TextFieldTableCell.<Goods, Integer>forTableColumn(new IntegerStringConverter()));
        goods_code.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, Integer> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                        .setCode(g.getNewValue());
                try {
                    String update = "UPDATE " + ConstGoods.GOODS_TABLE + " SET " + ConstGoods.GOODS_CODE + " = ?";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.setInt(1, g.getNewValue());
                    statement.executeUpdate();
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        goods_name.setCellFactory(TextFieldTableCell.forTableColumn());
        goods_name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, String> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                        .setNameOfItem(g.getNewValue());
                try {
                    String update = "UPDATE " + ConstGoods.GOODS_TABLE + " SET " +
                            ConstGoods.GOODS_NAME_OF_ITEM + " = ?";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.setString(1, g.getNewValue());
                    statement.executeUpdate();
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        goods_quantity.setCellFactory(TextFieldTableCell.<Goods, Integer>forTableColumn(new IntegerStringConverter()));
        goods_quantity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, Integer> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                .setQuantity(g.getNewValue());
                try {
                    String update = "UPDATE " + ConstGoods.GOODS_TABLE + " SET " +
                            ConstGoods.GOODS_QUANTITY + " = ?";

                    PreparedStatement statement = con.prepareStatement(update);
                    statement.setInt(1, g.getNewValue());
                    statement.executeUpdate();
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        goods_price_one.setCellFactory(TextFieldTableCell.<Goods, Double>forTableColumn(new DoubleStringConverter()));
        goods_price_one.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, Double> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                        .setPriceForOne(g.getNewValue());
                try {
                    String update = "UPDATE " + ConstGoods.GOODS_TABLE + " SET " +
                            ConstGoods.GOODS_PRICE_FOR_ONE + " = ?";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.setDouble(1, g.getNewValue());
                    statement.executeUpdate();
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        goods_price_total.setCellFactory(TextFieldTableCell.<Goods, Double>forTableColumn(new DoubleStringConverter()));
        goods_price_total.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, Double> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                        .setTotalPrice(g.getNewValue());
                try {
                    String update = "UPDATE " + ConstGoods.GOODS_TABLE + " SET " +
                            ConstGoods.GOODS_TOTAL_PRICE + " = ?";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.setDouble(1, g.getNewValue());
                    statement.executeUpdate();
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }



}







