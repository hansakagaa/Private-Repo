package model;

public class StoreItem {
    private String sItemCode;
    private String sItemDescription;
    private double sItemUnitPrice;
    private int totalQty;
    private double totalPrice;

    public StoreItem() {
    }

    public StoreItem(String sItemCode, String sItemDescription, Double sItemUnitPrice, int totalQty, Double totalPrice) {
        this.sItemCode = sItemCode;
        this.sItemDescription = sItemDescription;
        this.sItemUnitPrice = sItemUnitPrice;
        this.totalQty = totalQty;
        this.totalPrice = totalPrice;
    }

    public String getsItemCode() {
        return sItemCode;
    }

    public void setsItemCode(String sItemCode) {
        this.sItemCode = sItemCode;
    }

    public String getsItemDescription() {
        return sItemDescription;
    }

    public void setsItemDescription(String sItemDescription) {
        this.sItemDescription = sItemDescription;
    }

    public double getsItemUnitPrice() {
        return sItemUnitPrice;
    }

    public void setsItemUnitPrice(double sItemUnitPrice) {
        this.sItemUnitPrice = sItemUnitPrice;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "StoreItem{" +
                "sItemCode='" + sItemCode + '\'' +
                ", sItemDescription='" + sItemDescription + '\'' +
                ", sItemUnitPrice=" + sItemUnitPrice +
                ", totalQty=" + totalQty +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
