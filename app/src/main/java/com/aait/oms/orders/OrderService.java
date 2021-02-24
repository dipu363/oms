package com.aait.oms.orders;

import com.aait.oms.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {

   // http://aborong.com/orderapi/orderapi/branch/list // all branch list
   // http://localhost:8080/orderapi/orderapi/order/findByIdUserId?userName=dipu123 // individual user orders list
   // http://aborong.com/orderapi/orderapi/order/findByIdUserId?userName=dipu123 // individual user orders list
   // http://aborong.com/orderapi/orderapi/user/findByReferencedBy?referencedBy=admin // individual user reference list

    @GET("branch/list")
    Call<BaseResponse> get_branch_List();
    //http://aborong.com/orderapi/orderapi/prod1/list // cetegori list

    @GET("prodl/list")
    Call<BaseResponse> getCatList();
   // http://aborong.com/orderapi/orderapi/prodltwo/findById?id=1 // subcatagori list

   @GET("prodltwo/findById")
    Call<BaseResponse> getSubCatList(@Query("id") int id);

   // http://aborong.com/orderapi/orderapi/prodltwo/findById?id=1
    //http://aborong.com/orderapi/orderapi/prodmaster/findByl2Code?l2Code=1 // product list under catagori and subcatagori list
   @GET("prodmaster/findByl2Code")
    Call<BaseResponse> getproductbyl2id(@Query("l2Code") int id);
    // http://aborong.com/orderapi/orderapi/order/findByIdUserId?userName=dipu123 // individual user orders list

    @GET("order/findByIdUserId")
    Call<BaseResponse> getUserOrders(@Query("userName") String username);








}
