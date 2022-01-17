package com.aait.oms.users;

public class UsersViewModel {


   // http://aborong.com/orderapi/orderapi/user/findByReferencedBy?referencedBy=dipu
    //api return this fild and value

/*    {"success":true,"info":false,"warning":false,"message":"find data Successfully","valid":false,"id":null,"model":null,"data":null,
            "items":[{"username":"admin","fname":"dipu","roleId":"101","rolename":"ADMIN",
            "password":"$2a$10$O1ApF8uD/Fz3.GHZy.iN5u6kUkhP7CbMkFckixpK2YEYUZ64V/YEi",
            "referenced":"admin","photo":null,"status":"1","phone1":null,"phone2":null,
            "email":null,"address":null,"dob":null,"gender":null,"religion":null,
            "maritalStatus":null,"bloodGroup":null,"referencedTo":null,"commLayer":null,
            "commPromotionDate":null,"lname":"dipu"},
        {"username":"dipu123","fname":"d hossain",
            "roleId":"102","rolename":"Customer","password":"$2a$10$O1ApF8uD/Fz3.GHZy.iN5u6kUkhP7CbMkFckixpK2YEYUZ64V/YEi",
                "referenced":"admin","photo":null,"status":"1","phone1":"01933932630","phone2":null,"email":"dipu@gmail.com",
                "address":null,"dob":"2020-11-05","gender":"male","religion":null,"maritalStatus":null,"bloodGroup":null,
                "referencedTo":null,"commLayer":null,"commPromotionDate":null,"lname":"d hossain"}],"obj":null}*/


                String username;
                String fname;
                String roleId;
                String rolename;
                String password;
                String referenced;
                String photo;
                String status;
                String phone1;
                String phone2;
                String email;
                String address;
                String dob;
                String gender;
                String religion;
                String maritalStatus;
                String bloodGroup;
                String referencedTo;
                String commLayer;
                String commPromotionDate;
                String lname;


    public UsersViewModel() {
    }

    public UsersViewModel(String username, String fname, String roleId, String rolename, String password, String referenced, String photo, String status, String phone1, String phone2, String email, String address, String dob, String gender, String religion, String maritalStatus, String bloodGroup, String referencedTo, String commLayer, String commPromotionDate, String lname) {
        this.username = username;
        this.fname = fname;
        this.roleId = roleId;
        this.rolename = rolename;
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
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
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

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
