package com.aait.oms.product;

public class ProductFilterRequest {

    int l1Code;
    int l2Code;
    int l3Code;
    String uomCode;
    String prodCode;

    public ProductFilterRequest() {
    }

    public int getL1Code() {
        return l1Code;
    }

    public void setL1Code(int l1Code) {
        this.l1Code = l1Code;
    }

    public int getL2Code() {
        return l2Code;
    }

    public void setL2Code(int l2Code) {
        this.l2Code = l2Code;
    }

    public int getL3Code() {
        return l3Code;
    }

    public void setL3Code(int l3Code) {
        this.l3Code = l3Code;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }
}
