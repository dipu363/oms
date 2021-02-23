package com.aait.oms.branch;

public class BranchModel {
/*    branchID:string;
    comId:string;
    bname:string;
    address:string;
    mobile1:string;
    mobile2:string;
    email:string;
    logo:string;
    status:string;*/

    int branchID;
    String comId;
    String bname;
    String address;
    String mobile1;
    String mobile2;
    String email;
    String logo;
    String status;

    public BranchModel(int branchID, String comId, String bname, String address, String mobile1, String mobile2, String email, String logo, String status) {
        this.branchID = branchID;
        this.comId = comId;
        this.bname = bname;
        this.address = address;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.email = email;
        this.logo = logo;
        this.status = status;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
