package model;

import java.sql.Date;

public class OrderDTO {
    private String orderID;
    private Date orderDate;
    private String cstID;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, Date orderDate, String cstID) {
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
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
