package com.aait.oms.supplier;

import com.aait.oms.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SupplierInterface {
    @GET("supplier/list")
    Call<BaseResponse> getallsupplier();

}
