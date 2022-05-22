package model;

public class Customer {
    private String cstID;
    private String cstTitle;
    private String cstName;
    private String cstAddress;
    private String cstCity;
    private String cstProvince;
    private String cstPostalCode;

    public Customer() {
    }

    public Customer(String cstID, String cstTitle, String cstName, String cstAddress, String cstCity, String cstProvince, String cstPostalCode) {
        this.cstID = cstID;
        this.cstTitle = cstTitle;
        this.cstName = cstName;
        this.cstAddress = cstAddress;
        this.cstCity = cstCity;
        this.cstProvince = cstProvince;
        this.cstPostalCode = cstPostalCode;
    }

    public String getCstID() {
        return cstID;
    }

    public void setCstID(String cstID) {
        this.cstID = cstID;
    }

    public String getCstTitle() {
        return cstTitle;
    }

    public void setCstTitle(String cstTitle) {
        this.cstTitle = cstTitle;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName;
    }

    public String getCstAddress() {
        return cstAddress;
    }

    public void setCstAddress(String cstAddress) {
        this.cstAddress = cstAddress;
    }

    public String getCstCity() {
        return cstCity;
    }

    public void setCstCity(String cstCity) {
        this.cstCity = cstCity;
    }

    public String getCstProvince() {
        return cstProvince;
    }

    public void setCstProvince(String cstProvince) {
        this.cstProvince = cstProvince;
    }

    public String getCstPostalCode() {
        return cstPostalCode;
    }

    public void setCstPostalCode(String cstPostalCode) {
        this.cstPostalCode = cstPostalCode;
    }
}
