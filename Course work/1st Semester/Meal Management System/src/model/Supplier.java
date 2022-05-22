package model;

public class Supplier {
    private String supId;
    private String supName;
    private String supContact;
    private String supAddress;

    public Supplier() {
    }

    public Supplier(String supId, String supName, String supContact, String supAddress) {
        this.supId = supId;
        this.supName = supName;
        this.supContact = supContact;
        this.supAddress = supAddress;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupContact() {
        return supContact;
    }

    public void setSupContact(String supContact) {
        this.supContact = supContact;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public void setSupAddress(String supAddress) {
        this.supAddress = supAddress;
    }

}
