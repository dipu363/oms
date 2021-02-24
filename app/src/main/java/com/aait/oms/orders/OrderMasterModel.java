package com.aait.oms.orders;

public class OrderMasterModel {


          /*  "items":[{"ssCreator":"admin","ssCreatedOn":"2021-02-07T00:00:00.000+0000","ssModifier":null,"ssModifiedOn":"2021-02-07T00:00:00.000+0000",
            "orderId":704715796,"companyId":"101","branchId":1,"userName":"dipu","orderDate":"2021-02-07T00:00:00.000+0000",
            "shippingAddress":"d","activStatus":"Ordered","deliveryStatus":"2"},*/
/*    String ssCreator;
    String ssCreatedOn;
    String ssModifier;
    String ssModifiedOn;*/
    String orderId;
    String companyId;
    String branchId;
    String userName;
    String orderDate;
    String shippingAddress;
    String activStatus;
    String deliveryStatus;



    public OrderMasterModel(String orderId, String companyId, String branchId, String userName ,String orderDate, String shippingAddress, String activStatus, String deliveryStatus) {

        this.orderId = orderId;
        this.companyId = companyId;
        this.branchId = branchId;
        this.userName = userName;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.activStatus = activStatus;
        this.deliveryStatus = deliveryStatus;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getActivStatus() {
        return activStatus;
    }

    public void setActivStatus(String activStatus) {
        this.activStatus = activStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}