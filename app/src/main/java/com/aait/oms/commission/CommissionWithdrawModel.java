package com.aait.oms.commission;

import java.sql.Timestamp;
import java.util.Date;

public class CommissionWithdrawModel {


    String userName ;
    String transDate ;
    float transAmount ;
    String withdrawType ;
    String status ;
    float afterTexBalance ;
    float texAmount ;
    String transectionId ;

    public CommissionWithdrawModel() {
    }

    public CommissionWithdrawModel(String userName, String transDate, float transAmount, String withdrawType, String status, float afterTexBalance, float texAmount, String transectionId) {
        this.userName = userName;
        this.transDate = transDate;
        this.transAmount = transAmount;
        this.withdrawType = withdrawType;
        this.status = status;
        this.afterTexBalance = afterTexBalance;
        this.texAmount = texAmount;
        this.transectionId = transectionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getAfterTexBalance() {
        return afterTexBalance;
    }

    public void setAfterTexBalance(float afterTexBalance) {
        this.afterTexBalance = afterTexBalance;
    }

    public float getTexAmount() {
        return texAmount;
    }

    public void setTexAmount(float texAmount) {
        this.texAmount = texAmount;
    }

    public String getTransectionId() {
        return transectionId;
    }

    public void setTransectionId(String transectionId) {
        this.transectionId = transectionId;
    }
}