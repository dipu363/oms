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

    @POST("commWithdraw/create")
    Call<String> saveCommissionWithdrawRequest(@Body JsonObject json);




}
