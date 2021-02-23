package com.aait.oms.orders;

import org.json.JSONObject;

public class CardModel {

    String l1code;
    String l2code;
    String l3code;
    String l4code;
    String salesrate;
    String uomid;
    String productname;
    String activeStatus;
    String ledgername;
    int qty;

    public CardModel() {
    }

    public CardModel(String l1code, String l2code, String l3code, String l4code, String salesrate, String uomid, String productname, String activeStatus, String ledgername, int qty) {
        this.l1code = l1code;
        this.l2code = l2code;
        this.l3code = l3code;
        this.l4code = l4code;
        this.salesrate = salesrate;
        this.uomid = uomid;
        this.productname = productname;
        this.activeStatus = activeStatus;
        this.ledgername = ledgername;
        this.qty = qty;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


    public String getJsonObject() {
        JSONObject cartItems = new JSONObject();
        try
        {
            cartItems.put("l1code", getL1code());
            cartItems.put("l2code", getL2code());
            cartItems.put("l3code",getL3code());
            cartItems.put("l4code",getL4code());
            cartItems.put("salesrate",getSalesrate());
            cartItems.put("uomid",getUomid());
            cartItems.put("productname",getProductname());
            cartItems.put("activeStatus",getActiveStatus());
            cartItems.put("ledgername",getLedgername());
            cartItems.put("qty",getQty());
        }
        catch (Exception e) {}
        return cartItems.toString();
    }
}
