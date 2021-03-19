package com.aait.oms.commission;

import java.util.Date;

public class CommissionWithdrawModel {


    String userName ;
    String transDate ;
    float transAmount ;
    String withdrawType ;

    public CommissionWithdrawModel() {
    }

    public CommissionWithdrawModel(String userName, String transDate, float transAmount, String withdrawType) {
        this.userName = userName;
        this.transDate = transDate;
        this.transAmount = transAmount;
        this.withdrawType = withdrawType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public float getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(float transAmount) {
        this.transAmount = transAmount;
    }

    public String getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        this.withdrawType = withdrawType;
    }
}
