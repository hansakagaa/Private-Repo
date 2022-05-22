package model;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String mPIds;
    private String mealQty;
    private double paidAmount;
    private double discountPrice;
    private double totalAmount;

    public Order() {
    }

    public Order(String orderId, String mPIds, String mealQty, double paidAmount, double discountPrice, double totalAmount) {
        this.orderId = orderId;
        this.mPIds = mPIds;
        this.mealQty = mealQty;
        this.paidAmount = paidAmount;
        this.discountPrice = discountPrice;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMPIds() {
        return mPIds;
    }

    public void setMPIds(String mPIds) {
        this.mPIds = mPIds;
    }

    public String getMealQty() {
        return mealQty;
    }

    public void setMealQty(String mealQty) {
        this.mealQty = mealQty;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", mPIds=" + mPIds +
                ", mealQty=" + mealQty +
                ", paidAmount=" + paidAmount +
                ", discountPrice=" + discountPrice +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
