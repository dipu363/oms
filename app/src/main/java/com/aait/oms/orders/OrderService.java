package com.aait.oms.orders;

import com.aait.oms.model.BaseResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    //http://localhost:8080/orderapi/orderapi/prodmaster/findByl1Code?l1Code=1
    // http://aborong.com/orderapi/orderapi/prodmaster/findByl1Code?l1Code=6
    @GET("prodmaster/findByl1Code")
    Call<BaseResponse> getproductbyl1id(@Query("l1Code") int id);

    // http://aborong.com/orderapi/orderapi/order/findByIdUserId?userName=dipu123 // individual user orders list

    @GET("order/findByIdUserId")
    Call<BaseResponse> getUserOrders(@Query("userName") String username);
   // http://aborong.com/orderapi/orderapi/order/findDetailsById?orderId=777299045 //order detail list

    @GET("order/findDetailsById")
    Call<BaseResponse> getOrderdetails(@Query("orderId") int orderid);


    @POST("order/create")
    Call<String> saveOrder(@Body JsonObject json);

    //http://aborong.com/orderapi/orderapi/userCommission/findById?orderId=absfaruk  // indivedual user commission
    @GET("userCommission/findById")
    Call<BaseResponse> getuserCommission(@Query("orderId") String username);

}
