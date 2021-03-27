package com.aait.oms.orders;


import com.aait.oms.util.BaseModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderMasterModel extends BaseModel {


          /*  "items":[{"ssCreator":"admin","ssCreatedOn":"2021-02-07T00:00:00.000+0000","ssModifier":null,"ssModifiedOn":"2021-02-07T00:00:00.000+0000",
            "orderId":704715796,"companyId":"101","branchId":1,"userName":"dipu","orderDate":"2021-02-07T00:00:00.000+0000",
            "shippingAddress":"d","activStatus":"Ordered","deliveryStatus":"2"},*/
/*    String ssCreator;
    String ssCreatedOn;
    String ssModifier;
    String ssModifiedOn;*/
    int orderId;
    String companyId;
    int branchId;
    String userName;
    String orderDate ;
    String shippingAddress;
    String activStatus;
    String deliveryStatus;
    List<OrderDetailsModel> orderDetailList;

    public OrderMasterModel(int orderId) {
        this.orderId = orderId;
    }

    public OrderMasterModel( String companyId, int branchId, String userName, String orderDate, String shippingAddress, String activStatus, String deliveryStatus, List<OrderDetailsModel> orderDetailList) {

        this.companyId = companyId;
        this.branchId = branchId;
        this.userName = userName;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.activStatus = activStatus;
        this.deliveryStatus = deliveryStatus;
        this.orderDetailList = orderDetailList;
    }

    public OrderMasterModel(int orderId, String companyId, int branchId, String userName , String orderDate, String shippingAddress, String activStatus, String deliveryStatus) {

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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public List<OrderDetailsModel> getOrderDetailList() {
        return orderDetailList;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderDetailList(List<OrderDetailsModel> orderDetailList) {
        this.orderDetailList = orderDetailList;
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