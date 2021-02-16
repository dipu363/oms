package com.aait.oms.product;

import com.aait.oms.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductInterface {

    @GET("prodmaster/list")
    Call<BaseResponse> getallproduct();
    @GET("stock/viewlist")
    Call<BaseResponse> getstockview();
    @GET("prodl/list")
    Call<BaseResponse> getallrootcatagory();
    @GET("prodltwo/list")
    Call<BaseResponse> getallsubcatagory();



}
