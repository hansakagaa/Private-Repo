package view.tm;

public class OrderTm {
    private String mealPackId;
    private String mealPackSize;
    private int mealPackQty;
    private double total;

    public OrderTm() {
    }

    public OrderTm(String mealPackId, String mealPackSize, int mealPackQty, double total) {
        this.mealPackId = mealPackId;
        this.mealPackSize = mealPackSize;
        this.mealPackQty = mealPackQty;
        this.total = total;
    }

    public String getMealPackId() {
        return mealPackId;
    }

    public void setMealPackId(String mealPackId) {
        this.mealPackId = mealPackId;
    }

    public String getMealPackSize() {
        return mealPackSize;
    }

    public void setMealPackSize(String mealPackSize) {
        this.mealPackSize = mealPackSize;
    }

    public int getMealPackQty() {
        return mealPackQty;
    }

    public void setMealPackQty(int mealPackQty) {
        this.mealPackQty = mealPackQty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTm{" +
                "mealPackId='" + mealPackId + '\'' +
                ", mealPackSize='" + mealPackSize + '\'' +
                ", mealPackQty=" + mealPackQty +
                '}';
    }
}
