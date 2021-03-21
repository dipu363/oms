package com.aait.oms.product;

import com.aait.oms.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductInterface {

    @GET("prodmaster/list")
    Call<BaseResponse> getallproduct();
    @GET("prodmaster/findById")
    Call<BaseResponse> getsingleproduct(@Query("id")String id);
    @GET("stock/viewlist")
    Call<BaseResponse> getstockview();
    @GET("prodl/list")
    Call<BaseResponse> getallrootcatagory();
    @GET("prodltwo/list")
    Call<BaseResponse> getallsubcatagory();



}
