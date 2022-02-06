package com.aait.oms.product;

import com.aait.oms.model.BaseResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductInterface {

    @GET("prodmaster/list")
    Call<BaseResponse> getallproduct();

    @GET("prodmaster/findById")
    Call<BaseResponse> getsingleproduct(@Query("id")String id);

    //http://localhost:8080/orderapi/orderapi/prodmaster/findByl1Code?l1Code=1
    // http://aborong.com/orderapi/orderapi/prodmaster/findByl1Code?l1Code=6
    @GET("prodmaster/findByl1Code")
    Call<BaseResponse> getproductbyl1id(@Query("l1Code") int id);

    // http://aborong.com/orderapi/orderapi/prodltwo/findById?id=1
    //http://aborong.com/orderapi/orderapi/prodmaster/findByl2Code?l2Code=1 // product list under catagori and subcatagori list
    @GET("prodmaster/findByl2Code")
    Call<BaseResponse> getproductbyl2id(@Query("l2Code") int id);

   // http://aborong.com/orderapi/orderapi/stock/viewlist


    @GET("stock/viewlist")
    Call<BaseResponse> getstockview();

   // http://aborong.com/orderapi/orderapi/stock/productFilter >> POST request with request body here we can send an empty body

    @POST("stock/productFilter")
    Call<BaseResponse> productFilter(@Body JsonObject json);

/*    @GET("prodl/list")
    Call<BaseResponse> getallrootcatagory();

    @GET("prodltwo/list")
    Call<BaseResponse> getallsubcatagory();

    @GET("uom/findById")
    Call<BaseResponse> getumlname(@Query("id")int id);*/



}
