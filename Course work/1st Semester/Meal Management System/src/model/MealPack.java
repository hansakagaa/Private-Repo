package model;

public class MealPack {
    private String mPId;
    private String mPDescription;
    private String mPPackSize;
    private double mPUnitPrice;

    public MealPack() {
    }

    public MealPack(String mPId, String mPDescription, String mPPackSize, double mPUnitPrice) {
        this.mPId = mPId;
        this.mPDescription = mPDescription;
        this.mPPackSize = mPPackSize;
        this.mPUnitPrice = mPUnitPrice;
    }

    public String getMPId() {
        return mPId;
    }

    public void setMPId(String mPId) {
        this.mPId = mPId;
    }

    public String getMPDescription() {
        return mPDescription;
    }

    public void setMPDescription(String mPDescription) {
        this.mPDescription = mPDescription;
    }

    public String getMPPackSize() {
        return mPPackSize;
    }

    public void setMPPackSize(String mPPackSize) {
        this.mPPackSize = mPPackSize;
    }

    public double getMPUnitPrice() {
        return mPUnitPrice;
    }

    public void setMPUnitPrice(double mPUnitPrice) {
        this.mPUnitPrice = mPUnitPrice;
    }

    @Override
    public String toString() {
        return "MealPack{" +
                "mPId='" + getMPId() + '\'' +
                ", mPDescription='" + getMPDescription() + '\'' +
                ", mPPackSize='" + getMPPackSize() + '\'' +
                ", mPUnitPrice=" + getMPUnitPrice() +
                '}';
    }
}
