package model;

public class SupplyDetails {
    private String supId;
    private String sItemCode;
    private int supQty;
    private String sIDescription;
    private double sItemUP;
    private String date;

    public SupplyDetails() {
    }

    public SupplyDetails(String supId, String sItemCode, int supQty, String sIDescription, double sItemUP, String date) {
        this.supId = supId;
        this.sItemCode = sItemCode;
        this.supQty = supQty;
        this.sIDescription = sIDescription;
        this.sItemUP = sItemUP;
        this.date = date;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getsItemCode() {
        return sItemCode;
    }

    public void setsItemCode(String sItemCode) {
        this.sItemCode = sItemCode;
    }

    public int getSupQty() {
        return supQty;
    }

    public void setSupQty(int supQty) {
        this.supQty = supQty;
    }

    public String getsIDescription() {
        return sIDescription;
    }

    public void setsIDescription(String sIDescription) {
        this.sIDescription = sIDescription;
    }

    public double getsItemUP() {
        return sItemUP;
    }

    public void setsItemUP(double sItemUP) {
        this.sItemUP = sItemUP;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SupplyDetails{" +
                "supId='" + supId + '\'' +
                ", sItemCode='" + sItemCode + '\'' +
                ", supQty=" + supQty +
                ", sIDescription='" + sIDescription + '\'' +
                ", sItemUP=" + sItemUP +
                ", date='" + date + '\'' +
                '}';
    }
}
