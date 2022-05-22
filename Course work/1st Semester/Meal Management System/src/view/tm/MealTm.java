package view.tm;

public class MealTm {
    String id;
    String description;
    String packSize;
    double unitPrice;
    int qty;
    double total;

    public MealTm() {
    }

    public MealTm(String id, String description, String packSize, double unitPrice, int qty, double total) {
        this.id = id;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
