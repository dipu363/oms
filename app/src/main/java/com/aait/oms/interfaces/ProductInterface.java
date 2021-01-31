package com.aait.oms.interfaces;

import com.aait.oms.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductInterface {

    @GET("prodmaster/list")
    Call<BaseResponse> getallproduct();
    @GET("prodl/list")
    Call<BaseResponse> getallrootcatagory();
    @GET("prodltwo/list")
    Call<BaseResponse> getallsubcatagory();



}
