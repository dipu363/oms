package com.aait.oms.product;

public class StockViewModel {
    //stockmodel   {"pcode":"APREAF _100ctn","uomName":"CTN","soldQty":"170","totalQty":"5000","currentQty":"4830","avgPurRate":"90.00","salesRate":"100","currentTotalPrice":"434700","pname":"APPLE RED 100ctn","cumTotalPrice":"450000"}

    String pcode;
    String picByte;
    String uomName;
    String soldQty;
    String totalQty;
    String currentQty;
    String avgPurRate;
    String salesRate;
    String currentTotalPrice;
    String pname;
    String cumTotalPrice;

    public StockViewModel() {
    }

    public StockViewModel(String pcode, String picByte, String uomName, String soldQty, String totalQty, String currentQty, String avgPurRate, String salesRate, String currentTotalPrice, String pname, String cumTotalPrice) {
        this.pcode = pcode;
        this.picByte = picByte;
        this.uomName = uomName;
        this.soldQty = soldQty;
        this.totalQty = totalQty;
        this.currentQty = currentQty;
        this.avgPurRate = avgPurRate;
        this.salesRate = salesRate;
        this.currentTotalPrice = currentTotalPrice;
        this.pname = pname;
        this.cumTotalPrice = cumTotalPrice;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPicByte() {
        return picByte;
    }

    public void setPicByte(String picByte) {
        this.picByte = picByte;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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
                ", picByte='" + picByte + '\'' +
                ", uomName='" + uomName + '\'' +
                ", soldQty='" + soldQty + '\'' +
                ", totalQty='" + totalQty + '\'' +
                ", currentQty='" + currentQty + '\'' +
                ", avgPurRate='" + avgPurRate + '\'' +
                ", salesRate='" + salesRate + '\'' +
                ", currentTotalPrice='" + currentTotalPrice + '\'' +
                ", pname='" + pname + '\'' +
                ", cumTotalPrice='" + cumTotalPrice + '\'' +
                '}';
    }
}
