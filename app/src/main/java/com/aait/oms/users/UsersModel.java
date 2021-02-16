package com.aait.oms.users;

import java.util.Date;

public class UsersModel {


/*    userid: string;
    fname: string;
    lname: string;
    roleId: number;
    password: string;
    referenced: string;
    photo: string;
    status : string;
    phone1: string;
    phone2: string;
    email: string;
    address: string;
    dob: Date;
    gender: string;
    religion: string;
    maritalStatus: string;
    bloodGroup: string;
    referencedTo: string;
    commLayer: string;
    commPromotionDate: string;
    createBy: string;
    createDate: Date;*/


    private String userid;
    private String fname;
    private String lname;
    private int  roleId;
    private String password;
    private String referenced;
    private String photo;
    private String  status;
    private String  phone1;
    private String  phone2;
    private String  email;
    private String  address;
    private Date dob;
    private String  gender;
    private String  religion;
    private String  maritalStatus;
    private String  bloodGroup;
    private String  referencedTo;
    private String  commLayer;
    private String  commPromotionDate;
    private String  createBy;
    private Date  createDate;

    public UsersModel(String userid, String fname, String lname, int roleId, String password, String referenced, String photo, String status, String phone1, String phone2, String email, String address, Date dob, String gender, String religion, String maritalStatus, String bloodGroup, String referencedTo, String commLayer, String commPromotionDate, String createBy, Date createDate) {
        this.userid = userid;
        this.fname = fname;
        this.lname = lname;
        this.roleId = roleId;
        this.password = password;
        this.referenced = referenced;
        this.photo = photo;
        this.status = status;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.religion = religion;
        this.maritalStatus = maritalStatus;
        this.bloodGroup = bloodGroup;
        this.referencedTo = referencedTo;
        this.commLayer = commLayer;
        this.commPromotionDate = commPromotionDate;
        this.createBy = createBy;
        this.createDate = createDate;
    }

    public UsersModel(String userid, String fname, String lname, int roleId, String password,String gender, String referenced ) {
        this.userid = userid;
        this.fname = fname;
        this.lname = lname;
        this.roleId = roleId;
        this.password = password;
        this.gender = gender;
        this.referenced = referenced;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getReferenced() {
        return referenced;
    }

    public void setReferenced(String referenced) {
        this.referenced = referenced;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
