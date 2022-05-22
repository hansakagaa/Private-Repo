package model;

import java.util.ArrayList;

public class Order {
    private String orderID;
    private String orderDate;
    private String cstID;

    public Order() {
    }

    public Order(String orderID, String orderDate, String cstID) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.cstID = cstID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCstID() {
        return cstID;
    }

    public void setCstID(String cstID) {
        this.cstID = cstID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", cstID='" + cstID + '\'' +
                '}';
    }
}
