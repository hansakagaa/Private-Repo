package model;

public class PrintDetails {
    String billNo;
    String cusId;
    String date;
    double discount;
    int qty;
    double totalAmount;

    public PrintDetails() {
    }

    public PrintDetails(String billNo, String cusId, String date, double discount, int qty, double totalAmount) {
        this.billNo = billNo;
        this.cusId = cusId;
        this.date = date;
        this.discount = discount;
        this.qty = qty;
        this.totalAmount = totalAmount;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
