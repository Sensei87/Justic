package sample.entitys;

import javafx.beans.property.*;

public class Goods {

    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty code = new SimpleIntegerProperty();
    private StringProperty nameOfItem = new SimpleStringProperty();
    private DoubleProperty priceForOne = new SimpleDoubleProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private DoubleProperty totalPrice = new SimpleDoubleProperty();

    public Goods() {

    }
    public Goods(int id, int code, String nameOfItem, double priceForOne, int quantity, double totalPrice) {
        this.id.set(id);
        this.code.set(code);
        this.nameOfItem.set(nameOfItem);
        this.priceForOne.set(priceForOne);
        this.quantity.set(quantity);
        this.totalPrice.set(totalPrice);
    }
    public Goods( int code, String nameOfItem, double priceForOne, int quantity, double totalPrice) {
        this.code.set(code);
        this.nameOfItem.set(nameOfItem);
        this.priceForOne.set(priceForOne);
        this.quantity.set(quantity);
        this.totalPrice.set(totalPrice);
    }



    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getCode() {
        return code.get();
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public void setCode(int code) {
        this.code.set(code);
    }

    public String getNameOfItem() {
        return nameOfItem.get();
    }

    public StringProperty nameOfItemProperty() {
        return nameOfItem;
    }

    public void setNameOfItem(String nameOfItem) {
        this.nameOfItem.set(nameOfItem);
    }

    public double getPriceForOne() {
        return priceForOne.get();
    }

    public DoubleProperty priceForOneProperty() {
        return priceForOne;
    }

    public void setPriceForOne(double priceForOne) {
        this.priceForOne.set(priceForOne);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }
}
