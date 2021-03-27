package com.aait.oms.users;

import java.util.Date;

public class UserRequest {

    private String userName;
    private String otpUID;
    private String roleId;
    private String password;
    private String  referencedBy;
    private String active;
    private String fname;
    private String  lname;
    private String mobiPassword;
    private boolean login_status;

    public UserRequest(String userName, String otpUID, String roleId, String password, String referencedBy, String active, String fname, String lname, String mobiPassword, boolean login_status) {
        this.userName = userName;
        this.otpUID = otpUID;
        this.roleId = roleId;
        this.password = password;
        this.referencedBy = referencedBy;
        this.active = active;
        this.fname = fname;
        this.lname = lname;
        this.mobiPassword = mobiPassword;
        this.login_status = login_status;
    }

    public UserRequest() {
    }

    public UserRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferencedBy() {
        return referencedBy;
    }

    public void setReferencedBy(String referencedBy) {
        this.referencedBy = referencedBy;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobiPassword() {
        return mobiPassword;
    }

    public void setMobiPassword(String mobiPassword) {
        this.mobiPassword = mobiPassword;
    }

    public String getOtpUID() {
        return otpUID;
    }

    public void setOtpUID(String otpUID) {
        this.otpUID = otpUID;
    }

    public boolean isLogin_status() {
        return login_status;
    }

    public void setLogin_status(boolean login_status) {
        this.login_status = login_status;
    }
}
