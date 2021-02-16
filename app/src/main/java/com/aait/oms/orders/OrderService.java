package com.aait.oms.orders;

import com.aait.oms.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {

    @GET("prodl/list")
    Call<BaseResponse> getCatList();

   // http://aborong.com/orderapi/orderapi/prodltwo/findById?id=1
   @GET("prodltwo/findById")
    Call<BaseResponse> getSubCatList(@Query("id") int id);

   // http://aborong.com/orderapi/orderapi/prodltwo/findById?id=1
    //http://aborong.com/orderapi/orderapi/prodmaster/findByl2Code?l2Code=1
   @GET("prodmaster/findByl2Code")
    Call<BaseResponse> getproductbyl2id(@Query("l2Code") int id);



}
