package com.aait.oms.interfaces;

import com.aait.oms.model.BaseResponse;
import com.aait.oms.model.SupplierModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SupplierInterface {
    @GET("supplier/list")
    Call<BaseResponse> getallsupplier();

}
