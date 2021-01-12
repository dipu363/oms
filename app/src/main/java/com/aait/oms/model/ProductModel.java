package com.aait.oms.model;

public class ProductModel {

//     "ssCreator": "absfaruk",
//             "ssCreatedOn": "2020-12-11T00:00:00.000+0000",
//             "ssModifier": null,
//             "ssModifiedOn": "2020-12-11T00:00:00.000+0000",
//             "l1code": "2",
//             "l2code": "6",
//             "l3code": "1 ",
//             "l4code": "APFUCH _CTN_100",
//             "productname": "APPLE FUJI CHINA 100",
//             "salesrate": 100,
//             "uomid": 2,
//             "activeStatus": "1",
//             "ledgername": "THIS PRODUCT IMPORT FROM CHINA"

    String l1code;
    String l2code;
    String l3code;
    String l4code;
    String salesrate;
    String uomid;
    String productname;
    String activeStatus;
    String ledgername;

    public ProductModel(String l1code, String l2code, String l3code, String l4code, String salesrate, String uomid, String productname, String activeStatus, String ledgername) {
        this.l1code = l1code;
        this.l2code = l2code;
        this.l3code = l3code;
        this.l4code = l4code;
        this.salesrate = salesrate;
        this.uomid = uomid;
        this.productname = productname;
        this.activeStatus = activeStatus;
        this.ledgername = ledgername;
    }

    public ProductModel() {
    }

    public String getL1code() {
        return l1code;
    }

    public void setL1code(String l1code) {
        this.l1code = l1code;
    }

    public String getL2code() {
        return l2code;
    }

    public void setL2code(String l2code) {
        this.l2code = l2code;
    }

    public String getL3code() {
        return l3code;
    }

    public void setL3code(String l3code) {
        this.l3code = l3code;
    }

    public String getL4code() {
        return l4code;
    }

    public void setL4code(String l4code) {
        this.l4code = l4code;
    }

    public String getSalesrate() {
        return salesrate;
    }

    public void setSalesrate(String salesrate) {
        this.salesrate = salesrate;
    }

    public String getUomid() {
        return uomid;
    }

    public void setUomid(String uomid) {
        this.uomid = uomid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getLedgername() {
        return ledgername;
    }

    public void setLedgername(String ledgername) {
        this.ledgername = ledgername;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "l1code='" + l1code + '\'' +
                ", l2code='" + l2code + '\'' +
                ", l3code='" + l3code + '\'' +
                ", l4code='" + l4code + '\'' +
                ", salesrate='" + salesrate + '\'' +
                ", uomid='" + uomid + '\'' +
                ", productname='" + productname + '\'' +
                ", activeStatus='" + activeStatus + '\'' +
                ", ledgername='" + ledgername + '\'' +
                '}';
    }
}
