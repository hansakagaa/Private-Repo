package model;

public class OrderDetails {
    private String orderID;
    private String itemCode;
    private int OrderQty;
    private double discount;

    public OrderDetails() {
    }

    public OrderDetails(String orderID, String itemCode, int orderQty, double discount) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        OrderQty = orderQty;
        this.discount = discount;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderID='" + orderID + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", OrderQty=" + OrderQty +
                ", discount=" + discount +
                '}';
    }
}