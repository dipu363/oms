package com.aait.oms.product;

import java.util.Arrays;

public class StockViewModel {


/*    {
            "pcode": "OnSmPa _10kg",
            "l1Code": 5,
            "l1Name": "ONION",
            "l2Code": 9,
            "l2Name": "SMALL",
            "l3Code": 8,
            "l3Name": "Pakistan",
            "uomId": 39,
            "uomCode": "10kg",
            "uomName": "GUNI",
            "prodName": "ONION SMALL INDIA 10KG",
            "picByte": "/9j/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQiEE",
            "prodDetails": "This Product Import Pakistan",
            "prodStatus": "1",
            "soldQty": 175,
            "totalQty": 107,
            "currentQty": -68,
            "avgPurRate": 10.42,
            "salesRate": 20,
            "currentTotalPrice": -708.6,
            "cumTotalPrice": 1115
    }*/



    String pcode;
    String l1Code;
    String l1Name;
    String l2Code;
    String l2Name;
    String l3Code;
    String l3Name;
    String uomId;
    String uomCode;
    String uomName;
    String prodName;
    String picByte;
    String prodDetails;
    String prodStatus;
    String soldQty;
    String totalQty;
    String currentQty;
    String avgPurRate;
    String salesRate;
    String currentTotalPrice;
    String cumTotalPrice;

    public StockViewModel() {
    }

    public StockViewModel(String pcode, String l1Code, String l1Name, String l2Code, String l2Name, String l3Code, String l3Name, String uomId, String uomCode, String uomName, String prodName, String picByte, String prodDetails, String prodStatus, String soldQty, String totalQty, String currentQty, String avgPurRate, String salesRate, String currentTotalPrice, String cumTotalPrice) {
        this.pcode = pcode;
        this.l1Code = l1Code;
        this.l1Name = l1Name;
        this.l2Code = l2Code;
        this.l2Name = l2Name;
        this.l3Code = l3Code;
        this.l3Name = l3Name;
        this.uomId = uomId;
        this.uomCode = uomCode;
        this.uomName = uomName;
        this.prodName = prodName;
        this.picByte = picByte;
        this.prodDetails = prodDetails;
        this.prodStatus = prodStatus;
        this.soldQty = soldQty;
        this.totalQty = totalQty;
        this.currentQty = currentQty;
        this.avgPurRate = avgPurRate;
        this.salesRate = salesRate;
        this.currentTotalPrice = currentTotalPrice;
        this.cumTotalPrice = cumTotalPrice;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getL1Code() {
        return l1Code;
    }

    public void setL1Code(String l1Code) {
        this.l1Code = l1Code;
    }

    public String getL1Name() {
        return l1Name;
    }

    public void setL1Name(String l1Name) {
        this.l1Name = l1Name;
    }

    public String getL2Code() {
        return l2Code;
    }

    public void setL2Code(String l2Code) {
        this.l2Code = l2Code;
    }

    public String getL2Name() {
        return l2Name;
    }

    public void setL2Name(String l2Name) {
        this.l2Name = l2Name;
    }

    public String getL3Code() {
        return l3Code;
    }

    public void setL3Code(String l3Code) {
        this.l3Code = l3Code;
    }

    public String getL3Name() {
        return l3Name;
    }

    public void setL3Name(String l3Name) {
        this.l3Name = l3Name;
    }

    public String getUomId() {
        return uomId;
    }

    public void setUomId(String uomId) {
        this.uomId = uomId;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getPicByte() {
        return picByte;
    }

    public void setPicByte(String picByte) {
        this.picByte = picByte;
    }

    public String getProdDetails() {
        return prodDetails;
    }

    public void setProdDetails(String prodDetails) {
        this.prodDetails = prodDetails;
    }

    public String getProdStatus() {
        return prodStatus;
    }

    public void setProdStatus(String prodStatus) {
        this.prodStatus = prodStatus;
    }

    public String getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(String soldQty) {
        this.soldQty = soldQty;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(String currentQty) {
        this.currentQty = currentQty;
    }

    public String getAvgPurRate() {
        return avgPurRate;
    }

    public void setAvgPurRate(String avgPurRate) {
        this.avgPurRate = avgPurRate;
    }

    public String getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(String salesRate) {
        this.salesRate = salesRate;
    }

    public String getCurrentTotalPrice() {
        return currentTotalPrice;
    }

    public void setCurrentTotalPrice(String currentTotalPrice) {
        this.currentTotalPrice = currentTotalPrice;
    }

    public String getCumTotalPrice() {
        return cumTotalPrice;
    }

    public void setCumTotalPrice(String cumTotalPrice) {
        this.cumTotalPrice = cumTotalPrice;
    }

    @Override
    public String toString() {
        return "StockViewModel{" +
                "pcode='" + pcode + '\'' +
                ", l1Code='" + l1Code + '\'' +
                ", l1Name='" + l1Name + '\'' +
                ", l2Code='" + l2Code + '\'' +
                ", l2Name='" + l2Name + '\'' +
                ", l3Code='" + l3Code + '\'' +
                ", l3Name='" + l3Name + '\'' +
                ", uomId='" + uomId + '\'' +
                ", uomCode='" + uomCode + '\'' +
                ", uomName='" + uomName + '\'' +
                ", prodName='" + prodName + '\'' +
                ", picByte='" + picByte + '\'' +
                ", prodDetails='" + prodDetails + '\'' +
                ", prodStatus='" + prodStatus + '\'' +
                ", soldQty='" + soldQty + '\'' +
                ", totalQty='" + totalQty + '\'' +
                ", currentQty='" + currentQty + '\'' +
                ", avgPurRate='" + avgPurRate + '\'' +
                ", salesRate='" + salesRate + '\'' +
                ", currentTotalPrice='" + currentTotalPrice + '\'' +
                ", cumTotalPrice='" + cumTotalPrice + '\'' +
                '}';
    }
}


