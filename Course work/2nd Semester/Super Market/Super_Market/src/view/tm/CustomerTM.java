package view.tm;

public class CustomerTM {
    private String custID;
    private String custTitle;
    private String custName;
    private String custAddress;
    private String custCity;
    private String custProvince;
    private String custPostalCode;

    public CustomerTM() {
    }

    public CustomerTM(String custID, String custTitle, String custName, String custAddress, String custCity, String custProvince, String custPostalCode) {
        this.custID = custID;
        this.custTitle = custTitle;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProvince = custProvince;
        this.custPostalCode = custPostalCode;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCustTitle() {
        return custTitle;
    }

    public void setCustTitle(String custTitle) {
        this.custTitle = custTitle;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProvince() {
        return custProvince;
    }

    public void setCustProvince(String custProvince) {
        this.custProvince = custProvince;
    }

    public String getCustPostalCode() {
        return custPostalCode;
    }

    public void setCustPostalCode(String custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "custID='" + custID + '\'' +
                ", custTitle='" + custTitle + '\'' +
                ", custName='" + custName + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custCity='" + custCity + '\'' +
                ", custProvince='" + custProvince + '\'' +
                ", custPostalCode='" + custPostalCode + '\'' +
                '}';
    }
}
