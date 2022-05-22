package model;


public class OrderDetails {
    private String orderId;
    private String cusId;
    private int orderQty;
    private String orderTime;
    private String orderDate;
    private double totalAmount;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String cusId, int orderQty, String orderTime, String orderDate, double totalAmount) {
        this.orderId = orderId;
        this.cusId = cusId;
        this.orderQty = orderQty;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", orderQty=" + orderQty +
                ", orderTime=" + orderTime +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                '}';
    }

}
