package com.aait.oms.users;

import com.aait.oms.util.BaseModel;

import java.sql.Date;

public class UserModel extends BaseModel {

   /* active: "1"
            ​​
    branchID: 4
            ​​
    fname: "dipu"
            ​​
    lname: "dipu"
            ​​
    mobiPassword: "123456"
            ​​
    password: "$2a$10$J1epF5ms.2VCXAYqYKmjiuL/hLdsTe36UK0vIqCUbNCY.E1bg74IO"
            ​​
    photo: null
            ​​
    referencedBy: "dipu"
            ​​
    roleId: 112
            ​​
    ssCreatedOn: "2021-03-23T17:53:26.061+0000"
            ​​
    ssCreator: "admin"
            ​​
    ssModifiedOn: "2021-03-23T17:53:26.061+0000"
            ​​
    ssModifier: null
            ​​
    userName: "monir
    */



    private String userid;
    private String fname;
    private String lname;
    private int  roleId;
    private String password;
    private  int branchID;
    private String referenced;
    private String photo;
    private String status ="1";
    private String phone1;
    private String phone2;
    private String email;
    private String address;
    private String dob;
    private String gender;
    private String religion;
    private String maritalStatus;
    private String bloodGroup;
    private String referencedTo;
    private String commLayer;
    private String commPromotionDate;
    private String  mobiPassword;
    private String username;

    public UserModel() {
    }

    public UserModel(String userid, String fname, String lname, int roleId, String password, int branchID, String referenced, String gender) {
        this.userid = userid;
        this.fname = fname;
        this.lname = lname;
        this.roleId = roleId;
        this.password = password;
        this.branchID = branchID;
        this.referenced = referenced;
        this.gender = gender;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReferenced() {
        return referenced;
    }

    public void setReferenced(String referenced) {
        this.referenced = referenced;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getReferencedTo() {
        return referencedTo;
    }

    public void setReferencedTo(String referencedTo) {
        this.referencedTo = referencedTo;
    }

    public String getCommLayer() {
        return commLayer;
    }

    public void setCommLayer(String commLayer) {
        this.commLayer = commLayer;
    }

    public String getCommPromotionDate() {
        return commPromotionDate;
    }

    public void setCommPromotionDate(String commPromotionDate) {
        this.commPromotionDate = commPromotionDate;
    }
    public String getMobiPassword() {
        return mobiPassword;
    }

    public void setMobiPassword(String mobiPassword) {
        this.mobiPassword = mobiPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
