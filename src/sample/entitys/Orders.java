package sample.entitys;

public class Orders {

    private int idOrders;
    private int idGoods;
    private int idUsers;


    public Orders() {

    }

    public Orders(int idGoods, int idUsers) {
        this.idGoods = idGoods;
        this.idUsers = idUsers;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getIdGoods() {
        return idGoods;
    }

    public void setIdGoods(int idGoods) {
        this.idGoods = idGoods;
    }

    public int getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(int idOrders) {
        this.idOrders = idOrders;
    }
}
