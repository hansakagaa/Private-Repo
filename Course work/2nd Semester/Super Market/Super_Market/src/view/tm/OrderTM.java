package view.tm;

public class OrderTM {
    private String orderID;
    private String custID;
    private String orderDate;
    private String itemCode;
    private int qtyForSell;
    private double discount;

    public OrderTM() {
    }

    public OrderTM(String orderID, String custID, String orderDate, String itemCode, int qtyForSell, double discount) {
        this.orderID = orderID;
        this.custID = custID;
        this.orderDate = orderDate;
        this.itemCode = itemCode;
        this.qtyForSell = qtyForSell;
        this.discount = discount;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQtyForSell() {
        return qtyForSell;
    }

    public void setQtyForSell(int qtyForSell) {
        this.qtyForSell = qtyForSell;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderID='" + orderID + '\'' +
                ", custID='" + custID + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qtyForSell=" + qtyForSell +
                ", discount=" + discount +
                '}';
    }
}
