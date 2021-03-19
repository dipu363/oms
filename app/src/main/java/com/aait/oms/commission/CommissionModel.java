package com.aait.oms.commission;

public class CommissionModel {



    String userName;
    String mnyr ;
    Float comBlance ;
    String commType ;

    public CommissionModel(String userName, String mnyr, Float comBlance, String commType) {
        this.userName = userName;
        this.mnyr = mnyr;
        this.comBlance = comBlance;
        this.commType = commType;
    }

    public CommissionModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMnyr() {
        return mnyr;
    }

    public void setMnyr(String mnyr) {
        this.mnyr = mnyr;
    }

    public Float getComBlance() {
        return comBlance;
    }

    public void setComBlance(Float comBlance) {
        this.comBlance = comBlance;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    @Override
    public String toString() {
        return "CommissionModel{" +
                "userName='" + userName + '\'' +
                ", mnyr='" + mnyr + '\'' +
                ", comBlance=" + comBlance +
                ", commType='" + commType + '\'' +
                '}';
    }
}
