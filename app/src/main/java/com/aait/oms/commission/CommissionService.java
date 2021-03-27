package com.aait.oms.commission;

import com.aait.oms.model.BaseResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommissionService {

    //http://aborong.com/orderapi/orderapi/userCommission/findById?orderId=absfaruk  // indivedual user commission
    @GET("userCommission/findById")
    Call<BaseResponse> getuserCommission(@Query("orderId") String username);

    // http://aborong.com/orderapi/orderapi/commWithdraw/create
    // send a jsonObject with your model to joson body
    @POST("commWithdraw/create")
    Call<String> saveCommissionWithdrawRequest(@Body JsonObject json);

   // http://aborong.com/orderapi/orderapi/commWithdraw/findById?orderId=admin
    @GET("commWithdraw/findById")
    Call<BaseResponse> getuserWithdrawCommission(@Query("orderId") String username);




}
