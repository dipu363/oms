package com.aait.oms.model;

public class ProdSubCatagoryModel {
//    l1Code: "1"
//            ​
//    l2Code: 2
//            ​
//    l2Name: "RED"
//            ​
//    ssCreatedOn: "2021-01-21T00:00:00.000+0000"
//            ​
//    ssCreator: "admin"
//            ​
//    ssModifiedOn: "2021-01-21T00:00:00.000+0000"
//            ​
//    ssModifier: null

    int l1Code;
    int l2Code;
    String l2Name;

    public ProdSubCatagoryModel(int l1Code, int l2Code, String l2Name) {
        this.l1Code = l1Code;
        this.l2Code = l2Code;
        this.l2Name = l2Name;
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

    public String getL2Name() {
        return l2Name;
    }

    public void setL2Name(String l2Name) {
        this.l2Name = l2Name;
    }
}
