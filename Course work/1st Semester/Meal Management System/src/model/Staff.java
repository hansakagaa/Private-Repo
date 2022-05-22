package model;

public class Staff {
    String stId;
    String stName;
    String stAddress;
    String stNIC;
    String stContact;
    String stPosition;

    public Staff() {
    }

    public Staff(String stId, String stName, String stAddress, String stNIC, String stContact, String stPosition) {
        this.stId = stId;
        this.stName = stName;
        this.stAddress = stAddress;
        this.stNIC = stNIC;
        this.stContact = stContact;
        this.stPosition = stPosition;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStAddress() {
        return stAddress;
    }

    public void setStAddress(String stAddress) {
        this.stAddress = stAddress;
    }

    public String getStNIC() {
        return stNIC;
    }

    public void setStNIC(String stNIC) {
        this.stNIC = stNIC;
    }

    public String getStContact() {
        return stContact;
    }

    public void setStContact(String stContact) {
        this.stContact = stContact;
    }

    public String getStPosition() {
        return stPosition;
    }

    public void setStPosition(String stPosition) {
        this.stPosition = stPosition;
    }

}
