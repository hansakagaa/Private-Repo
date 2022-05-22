package view.tm;

public class CartTM {
    private String itemCode;
    private String description;
    private String packSize;
    private int orderQty;
    private Double unitPrice;
    private Double total;

    public CartTM() {
    }

    public CartTM(String itemCode, String description, String packSize, int orderQty, Double unitPrice) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
    }

    public CartTM(String itemCode, String description, String packSize, int orderQty, Double unitPrice, Double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", OrderQty=" + orderQty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
