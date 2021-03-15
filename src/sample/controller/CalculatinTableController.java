package sample.controller;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TextField codeText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField quantityText;

    @FXML
    private TextField priceOneText;



    @FXML
    void initialize() throws Exception {
        assert goodsTable != null;
        goodsTable.setEditable(true);
        changGoods();

        addButton.setOnAction(actionEvent -> {

            int index = goodsTable.getSelectionModel().getSelectedIndex() + 1;

            data.add(index, addGoods());

            goodsTable.getSelectionModel().select(index);

            goodsTable.layout();

            codeText.clear();
            nameText.clear();
            quantityText.clear();
            priceOneText.clear();


        });
        deleteButton.setOnAction(actionEvent -> {
                delete();
            goodsTable.getItems().clear();
            goodsTable.refresh();
            buildData();

        });

        refreshButton.setOnAction(actionEvent -> {
            goodsTable.getItems().clear();
            goodsTable.refresh();
            buildData();
        });

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


       /* idgoods.setCellFactory(TextFieldTableCell.<Goods, Integer>forTableColumn(new IntegerStringConverter()));
        idgoods.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, Integer> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                        .setId(g.getNewValue());
                try {
                    String update = "UPDATE " + ConstGoods.GOODS_TABLE + " SET " + ConstGoods.GOODS_ID + " = ?";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.setInt(1, g.getNewValue());
                    statement.executeUpdate();
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
*/
        goods_code.setCellFactory(TextFieldTableCell.<Goods, Integer>forTableColumn(new IntegerStringConverter()));
        goods_code.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, Integer> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                        .setCode(g.getNewValue());
                try {
                    String update = "UPDATE " + ConstGoods.GOODS_TABLE + " SET " + ConstGoods.GOODS_CODE +
                              " = ?" + " WHERE " + ConstGoods.GOODS_ID + " = ?";
                    PreparedStatement statement = con.prepareStatement(update);

                    // Получить значения id
                    IntegerProperty val = g.getTableView().getItems().get(g.getTablePosition().getRow())
                            .idProperty();
                    //System.out.println(val.get());

                    statement.setInt(1, g.getNewValue());
                    statement.setInt(2, val.get());

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
                            ConstGoods.GOODS_NAME_OF_ITEM +
                            " = ?" + " WHERE " + ConstGoods.GOODS_ID + " = ?";
                    PreparedStatement statement = con.prepareStatement(update);

                    // Получить значения id
                    IntegerProperty val = g.getTableView().getItems().get(g.getTablePosition().getRow())
                            .idProperty();

                    statement.setString(1, g.getNewValue());
                    statement.setInt(2, val.get());
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
                            ConstGoods.GOODS_QUANTITY + " = ? " + ", " + ConstGoods.GOODS_TOTAL_PRICE
                            + " = ? " + " WHERE " + ConstGoods.GOODS_ID + " = ? ";
                    PreparedStatement statement = con.prepareStatement(update);

                    // Получить значения id
                    IntegerProperty val = g.getTableView().getItems().get(g.getTablePosition().getRow())
                            .idProperty();
                    // Получаем значения ячейки цена за один
                    DoubleProperty val2 = g.getTableView().getItems().get(g.getTablePosition().getRow())
                            .priceForOneProperty();
                    int result = (int) (val2.get() * g.getNewValue());

                    statement.setInt(1, g.getNewValue());
                    statement.setInt(2, result);
                    statement.setInt(3, val.get());
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
                            ConstGoods.GOODS_PRICE_FOR_ONE + " = ? " + ", " + ConstGoods.GOODS_TOTAL_PRICE
                            + " = ? " + " WHERE " + ConstGoods.GOODS_ID + " = ? ";

                    PreparedStatement statement = con.prepareStatement(update);

                    // Получить значения id
                    IntegerProperty val = g.getTableView().getItems().get(g.getTablePosition().getRow())
                            .idProperty();
                    // Получаем значения ячейки количество
                    IntegerProperty val2 = g.getTableView().getItems().get(g.getTablePosition().getRow())
                            .quantityProperty();
                    int result = (int) (val2.get() * g.getNewValue());

                    System.out.println(result);
                    statement.setDouble(1, g.getNewValue());
                    statement.setDouble(2, result);
                    statement.setInt(3, val.get());
                    statement.executeUpdate();
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

       /* goods_price_total.setCellFactory(TextFieldTableCell.<Goods, Double>forTableColumn(new DoubleStringConverter()));
        goods_price_total.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Goods, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Goods, Double> g) {
                ((Goods)g.getTableView().getItems().get(g.getTablePosition().getRow()))
                        .setTotalPrice(g.getNewValue());
                try {
                    String update = "UPDATE " + ConstGoods.GOODS_TABLE + " SET " +
                            ConstGoods.GOODS_TOTAL_PRICE +
                            " = ?" + " WHERE " + ConstGoods.GOODS_ID + " = ?";
                    PreparedStatement statement = con.prepareStatement(update);

                    // Получить значения id
                    IntegerProperty val = g.getTableView().getItems().get(g.getTablePosition().getRow())
                            .idProperty();

                    statement.setDouble(1, g.getNewValue());
                    statement.setDouble(2, val.get());
                    statement.executeUpdate();
                    statement.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    */

    }


    // Метод добавления новых данных в таблицу
    public Goods addGoods() {
        DatabaseHandlerGoods handlerGoods = new DatabaseHandlerGoods();
        int code = Integer.parseInt(codeText.getText());
        String nameOfItem = nameText.getText();
        int quantity = Integer.parseInt(quantityText.getText());
        double priceForOne = Double.parseDouble(priceOneText.getText());
        double totalPrice = Double.parseDouble(String.valueOf((priceForOne * quantity)));

        Goods goods = new Goods(code, nameOfItem, priceForOne, quantity, totalPrice);
        handlerGoods.addNewGoods(goods);

        return goods;

    }

    // Метод удаления данных таблицы
    public void delete() {

            try {
                String delete = "DELETE FROM " + ConstGoods.GOODS_TABLE + " WHERE " + ConstGoods.GOODS_ID +
                        " = ?";
                PreparedStatement statement = con.prepareStatement(delete);
                IntegerProperty sel = goodsTable.getSelectionModel().getSelectedItem().idProperty();
                statement.setInt(1, sel.get());
                System.out.println(sel.get());
                statement.executeUpdate();
                statement.close();


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        System.out.println("Inside method delete");
    }



}







