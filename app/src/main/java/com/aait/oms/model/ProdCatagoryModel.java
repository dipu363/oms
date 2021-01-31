package com.aait.oms.model;

public class ProdCatagoryModel {
    //    l1Code: 1
//            ​
//    l1Name: "APPLE"
//            ​
//    ssCreatedOn: "2021-01-21T00:00:00.000+0000"
//            ​
//    ssCreator: "admin"
//            ​
//    ssModifiedOn: "2021-01-21T00:00:00.000+0000"
//            ​
//    ssModifier: null

    int l1Code;
    String l1Name;

    public ProdCatagoryModel(int l1Code, String l1Name) {
        this.l1Code = l1Code;
        this.l1Name = l1Name;
    }

    public int getL1Code() {
        return l1Code;
    }

    public void setL1Code(int l1Code) {
        this.l1Code = l1Code;
    }

    public String getL1Name() {
        return l1Name;
    }

    public void setL1Name(String l1Name) {
        this.l1Name = l1Name;
    }

    @Override
    public String toString() {
        return "prodCatagoryModel{" +
                "l1Code=" + l1Code +
                ", l1Name='" + l1Name + '\'' +
                '}';
    }
}
