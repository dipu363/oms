package com.aait.oms.supplier;

public class SupplierModel {

//       "ssCreator": "absfaruk",
//               "ssCreatedOn": "2020-12-08T00:00:00.000+0000",
//               "ssModifier": null,
//               "ssModifiedOn": "2020-12-08T00:00:00.000+0000",
//               "supplierId": 1,
//               "supName": "GHP",
//               "supAddress": "no.5, lorong industri tanah putih baru 6,",
//               "supMobile": "0169201633",
//               "supEmail": "absfaruk.fk@gmail.com",
//               "contactPerson": "0169201633",
//               "status": null

    String ssCreator;
    String ssCreatedOn;
    String  ssModifier;
    String ssModifiedOn;
    int supplierId ;
    String supName ;
    String supAddress ;
    String supMobile ;
    String supEmail ;
    String contactPerson ;
    String status ;

    public SupplierModel() {
    }

    public SupplierModel(int supplierId, String supName, String supAddress, String supMobile, String supEmail, String contactPerson, String status) {
        this.supplierId = supplierId;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supMobile = supMobile;
        this.supEmail = supEmail;
        this.contactPerson = contactPerson;
        this.status = status;
    }

    public String getSsCreator() {
        return ssCreator;
    }

    public void setSsCreator(String ssCreator) {
        this.ssCreator = ssCreator;
    }

    public String getSsCreatedOn() {
        return ssCreatedOn;
    }

    public void setSsCreatedOn(String ssCreatedOn) {
        this.ssCreatedOn = ssCreatedOn;
    }

    public String getSsModifier() {
        return ssModifier;
    }

    public void setSsModifier(String ssModifier) {
        this.ssModifier = ssModifier;
    }

    public String getSsModifiedOn() {
        return ssModifiedOn;
    }

    public void setSsModifiedOn(String ssModifiedOn) {
        this.ssModifiedOn = ssModifiedOn;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public void setSupAddress(String supAddress) {
        this.supAddress = supAddress;
    }

    public String getSupMobile() {
        return supMobile;
    }

    public void setSupMobile(String supMobile) {
        this.supMobile = supMobile;
    }

    public String getSupEmail() {
        return supEmail;
    }

    public void setSupEmail(String supEmail) {
        this.supEmail = supEmail;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SupplierModel{" +
                "ssCreator='" + ssCreator + '\'' +
                ", ssCreatedOn='" + ssCreatedOn + '\'' +
                ", ssModifier='" + ssModifier + '\'' +
                ", ssModifiedOn='" + ssModifiedOn + '\'' +
                ", supplierId=" + supplierId +
                ", supName='" + supName + '\'' +
                ", supAddress='" + supAddress + '\'' +
                ", supMobile='" + supMobile + '\'' +
                ", supEmail='" + supEmail + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
